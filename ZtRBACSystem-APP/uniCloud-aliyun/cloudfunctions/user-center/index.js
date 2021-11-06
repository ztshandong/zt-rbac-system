'use strict';
const crypto = require('crypto')
const uniID = require('uni-id')
const uniCaptcha = require('uni-captcha')
const db = uniCloud.database()
const dbCmd = db.command

exports.main = async (event, context) => {
    let params = event.params || {}

    // 登录记录
    const loginLog = async (res = {}, type = 'login') => {
        const now = Date.now()
        const uniIdLogCollection = db.collection('uni-id-log')
        let logData = {
            deviceId: params.deviceId || context.DEVICEID,
            ip: params.ip || context.CLIENTIP,
            type,
            ua: context.CLIENTUA,
            create_date: now
        };

        Object.assign(logData,
            res.code === 0 ? {
                user_id: res.uid,
                state: 1
            } : {
                state: 0
            })

        return uniIdLogCollection.add(logData)
    }

    const getNeedCaptcha = async () => {
        const now = Date.now()
        // 查询是否在 {2小时} 内 {前2条} 有 {登录失败} 数据，来确定是否需要验证码
        const recordSize = 2;
        const recordDate = 120 * 60 * 1000;

        const uniIdLogCollection = db.collection('uni-id-log')
        let recentRecord = await uniIdLogCollection.where({
            deviceId: params.deviceId || context.DEVICEID,
            create_date: dbCmd.gt(now - recordDate),
            type: 'login'
        })
            .orderBy('create_date', 'desc')
            .limit(recordSize)
            .get();

        return recentRecord.data.filter(item => item.state === 0).length === recordSize;
    }

    let queryStringParameters = event.queryStringParameters
    const getPhoneNumberByAccessToken = async (param) => {
        console.log('param')
        console.log(param)
        const res = await uniCloud.getPhoneNumber({
            provider: 'univerify',
            appid: param.appid, // DCloud appid，不同于callFunction方式调用，使用云函数Url化需要传递DCloud appid参数
            apiKey: 'aaa', // 在开发者中心开通服务并获取apiKey
            apiSecret: 'bbb', // 在开发者中心开通服务并获取apiSecret
            access_token: param.access_token,
            openid: param.openid
        })
        // 返回手机号给自己服务器
        return res
    }

    //event为客户端上传的参数
    console.log('event : ')
    console.log(event)

    let payload = {}
    let noCheckAction = [
        'register', 'loginByWeixin', 'checkToken',
        'login', 'logout', 'sendSmsCode',
        'loginBySms', 'inviteLogin', 'loginByUniverify',
        'loginByApple', 'createCaptcha', 'verifyCaptcha',
        'refreshCaptcha', 'getPhoneNumberByAccessToken'
    ]

    let res = {}
    if (queryStringParameters) {
        if (queryStringParameters.action == 'getPhoneNumberByAccessToken') {
            res = await getPhoneNumberByAccessToken(queryStringParameters)
            return res
        }
    } else {
        if (noCheckAction.indexOf(event.action) === -1) {
            if (!event.uniIdToken) {
                return {
                    code: 403,
                    msg: '缺少token'
                }
            }
            payload = await uniID.checkToken(event.uniIdToken)
            if (payload.code && payload.code > 0) {
                return payload
            }
            params.uid = payload.uid
        }
    }

    switch (event.action) {
        case 'register':
            res = await uniID.register(params);
            break;
        case 'login':
            let passed = false;
            let needCaptcha = await getNeedCaptcha();

            if (needCaptcha) {
                res = await uniCaptcha.verify(params)
                if (res.code === 0) passed = true;
            }

            if (!needCaptcha || passed) {
                res = await uniID.login(params);
                await loginLog(res);
                needCaptcha = await getNeedCaptcha();
            }

            res.needCaptcha = needCaptcha;
            break;
        case 'loginByWeixin':
            res = await uniID.loginByWeixin(params);
            loginLog(res)
            break;
        case 'checkToken':
            res = await uniID.checkToken(event.uniIdToken);
            break;
        case 'logout':
            res = await uniID.logout(event.uniIdToken)
            break;
        case 'sendSmsCode':
            // 简单限制一下客户端调用频率
            const ipLimit = await db.collection('uni-verify').where({
                ip: context.CLIENTIP,
                created_at: dbCmd.gt(Date.now() - 60000)
            }).get()
            if (ipLimit.data.length > 0) {
                return {
                    code: 429,
                    msg: '请求过于频繁'
                }
            }
            const templateId = '' // 替换为自己申请的模板id
            if (!templateId) {
                return {
                    code: 500,
                    msg: 'sendSmsCode需要传入自己的templateId，参考https://uniapp.dcloud.net.cn/uniCloud/uni-id?id=sendsmscode'
                }
            }
            const randomStr = '00000' + Math.floor(Math.random() * 1000000)
            const code = randomStr.substring(randomStr.length - 6)
            res = await uniID.sendSmsCode({
                mobile: params.mobile,
                code,
                type: params.type,
                templateId
            })
            break;
        case 'loginBySms':
            if (!params.code) {
                return {
                    code: 500,
                    msg: '请填写验证码'
                }
            }
            if (!/^1\d{10}$/.test(params.mobile)) {
                return {
                    code: 500,
                    msg: '手机号码填写错误'
                }
            }
            res = await uniID.loginBySms(params)
            loginLog(res)
            break;
        case 'inviteLogin':
            if (!params.code) {
                return {
                    code: 500,
                    msg: '请填写验证码'
                }
            }
            res = await uniID.loginBySms({
                ...params,
                type: 'register'
            })
            break;
        case 'getInviteCode':
            res = await uniID.getUserInfo({
                uid: params.uid,
                field: ['my_invite_code']
            })
            if (res.code === 0) {
                res.myInviteCode = res.userInfo.my_invite_code
                delete res.userInfo
            }
            break;
        case 'getInvitedUser':
            res = await uniID.getInvitedUser(params)
            break;
        case 'loginByUniverify':
            res = await uniID.loginByUniverify(params)
            break;
        case 'getPhoneNumberByAccessToken':
            console.log('getPhoneNumberByAccessToken')
            console.log(params)
            res = await getPhoneNumberByAccessToken(params)
            break;
        case 'loginByApple':
            res = await uniID.loginByApple(params)
            loginLog(res)
            break;
        case 'updatePwd':
            res = await uniID.updatePwd({
                uid: params.uid,
                ...params
            })
            break;
        case 'createCaptcha':
            res = await uniCaptcha.create(params)
            break;
        case 'refreshCaptcha':
            res = await uniCaptcha.refresh(params)
            break;
        default:
            res = {
                code: 403,
                msg: '非法访问'
            }
            break;
    }

    //返回数据给客户端
    return res
};