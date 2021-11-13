'use strict';
const crypto = require('crypto')
let uniID = require('uni-id')
const uniCaptcha = require('uni-captcha')
const db = uniCloud.database()
const dbCmd = db.command

exports.main = async (event, context) => {
    uniID = uniID.createInstance({
        context
    })

    let params = event.params || {}
    let body = {}

    if (context.SOURCE == 'http') {
        // uniCloud.logger.info('body')
        // uniCloud.logger.info(event.body)
        body = JSON.parse(event.body)
        context.OS = body.clientInfo.OS
        context.PLATFORM = body.clientInfo.PLATFORM
        context.APPID = body.appid
        context.DEVICEID = body.clientInfo.DEVICEID
        context.CLIENTIP = body.clientInfo.CLIENTIP
        context.LOCALE = 'zh-Hans'

        let uniIdToken = body.uniIdToken
        if (uniIdToken) {
            event.uniIdToken = uniIdToken
        }

        params = body.params
    }

    let action = event.action || body.action

    //event为客户端上传的参数
    console.log('params : ')
    // console.log(event)
    uniCloud.logger.info(params)
    const sign = params.sign
    delete params.sign
    let signStr = getSignStr(params)
    uniCloud.logger.info(action + signStr)
    // uniCloud.logger.info(getSignStr(event.clientInfo))

    const secret = 'test'
    const hmac = crypto.createHmac('sha256', secret);
    hmac.update(signStr);
    // uniCloud.logger.info(hmac.digest('hex'))
    uniCloud.logger.info(hmac.digest('base64'))

    console.log('context : ')
    let source = context.SOURCE // 当前云函数被何种方式调用
    uniCloud.logger.info('source')
    uniCloud.logger.info(source)
    // client   客户端callFunction方式调用
    // http     云函数url化方式调用
    // timing   定时触发器调用
    // server   由管理端调用，HBuilderX里上传并运行，仅阿里云支持，腾讯云这种方式调用也是client
    // function 由其他云函数callFunction调用，仅阿里云支持，腾讯云这种方式调用也是client

    // console.log(context)
    // uniCloud.logger.info(context)
    //context中可获取客户端调用的上下文
    let clientIP = context.CLIENTIP // 客户端ip信息
    uniCloud.logger.info('clientIP')
    uniCloud.logger.info(clientIP)
    let clientUA = context.CLIENTUA // 客户端user-agent
    uniCloud.logger.info('clientUA')
    uniCloud.logger.info(clientUA)
    let spaceInfo = context.SPACEINFO // 当前环境信息 {spaceId:'xxx',provider:'tencent'}
    uniCloud.logger.info('spaceInfo')
    uniCloud.logger.info(spaceInfo)
    let locale = context.LOCALE
    uniCloud.logger.info('locale')
    uniCloud.logger.info(locale)

    // 以下四个属性只有使用uni-app以callFunction方式调用才能获取
    let os = context.OS //客户端操作系统，返回值：android、ios    等
    uniCloud.logger.info('os')
    uniCloud.logger.info(os)
    let platform = context.PLATFORM //运行平台，返回值为 mp-weixin、app-plus等
    uniCloud.logger.info('platform')
    uniCloud.logger.info(platform)
    let appid = context.APPID // manifest.json中配置的appid
    uniCloud.logger.info('appid')
    uniCloud.logger.info(appid)
    let deviceId = context.DEVICEID // 客户端标识，新增于HBuilderX 3.1.0，同uni-app客户端getSystemInfo接口获取的deviceId
    uniCloud.logger.info('deviceId')
    uniCloud.logger.info(deviceId)

    if (!params.deviceId) {
        params.deviceId = deviceId
    }

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
        // console.log('param')
        // console.log(param)
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

    let contextRes = {}
    contextRes.CLIENTIP = clientIP
    // contextRes.CLIENTUA = clientUA
    // contextRes.SPACEINFO = spaceInfo
    contextRes.OS = os
    contextRes.PLATFORM = platform
    // contextRes.APPID = appid
    contextRes.DEVICEID = deviceId

    let payload = {}
    let noCheckAction = [
        'register', 'loginByWeixin', 'checkToken',
        'login', 'logout', 'sendSmsCode',
        'loginBySms', 'inviteLogin', 'loginByUniverify',
        'loginByApple', 'createCaptcha', 'verifyCaptcha',
        'refreshCaptcha', 'getPhoneNumberByAccessToken', 'getClientInfo'
    ]

    let res = {}
    // if (queryStringParameters) {
    // 	if (queryStringParameters.action == 'getPhoneNumberByAccessToken') {
    // 		res = await getPhoneNumberByAccessToken(queryStringParameters)
    // 		return res
    // 	} else if (queryStringParameters.action == 'login') {
    // 		let passed = false;
    // 		let needCaptcha = await getNeedCaptcha();
    // 		if (needCaptcha) {
    // 			res = await uniCaptcha.verify(queryStringParameters)
    // 			if (res.code === 0) passed = true;
    // 		}
    // 		if (!needCaptcha || passed) {
    // 			res = await uniID.login({
    // 				username: queryStringParameters.username,
    // 				password: queryStringParameters.password,
    // 				queryField: ['username', 'email', 'mobile']
    // 			});
    // 			await loginLog(res);
    // 			needCaptcha = await getNeedCaptcha();
    // 		}
    // 		res.needCaptcha = needCaptcha;
    // 		return res
    // 	}
    // } else {
    // 	if (noCheckAction.indexOf(event.action) === -1) {
    // 		if (!event.uniIdToken) {
    // 			return {
    // 				code: 403,
    // 				msg: '缺少token'
    // 			}
    // 		}
    // 		payload = await uniID.checkToken(event.uniIdToken)
    // 		if (payload.code && payload.code > 0) {
    // 			return payload
    // 		}
    // 		params.uid = payload.uid
    // 	}
    // }

    switch (action) {
        case 'bindMobile': {
            const {
                uid,
                mobile,
                code
            } = params
            res = await uniID.bindMobile({
                uid,
                mobile,
                code
            });
            break;
        }
        case 'getClientInfo':
            res.clientInfo = contextRes;
            uniCloud.logger.info('res')
            uniCloud.logger.info(res)
            break;
        case 'register':
            res = await uniID.register(params);
            break;
        case 'login':
            let passed = false;
            let needCaptcha = await getNeedCaptcha();

            if (needCaptcha) {
                // params.deviceId = deviceId
                res = await uniCaptcha.verify(params)
                if (res.code === 0) passed = true;
            }

            if (!needCaptcha || passed) {
                res = await uniID.login(params);
                await loginLog(res);
                needCaptcha = await getNeedCaptcha();
            }

            res.needCaptcha = needCaptcha;
            if (event.apiUrl) {
                const apiRes = await uniCloud.httpclient.request(event.apiUrl, {
                    method: 'POST',
                    rejectUnauthorized: false,
                    strictSSL: false,
                    data: {
                        userName: params.username,
                        userPwd: params.password
                    },
                    contentType: 'json', // 指定以application/json发送data内的数据
                    dataType: 'json' // 指定返回值为json格式，自动进行parse
                })
                // console.log('apiRes')
                // console.log(apiRes)
                res.apiRes = apiRes
            }
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


function getSignStr(obj) {
    // uniCloud.logger.info(obj)
    const signStr = Object.keys(obj).sort().map(key => {
        // uniCloud.logger.info(Object.prototype.toString.call(obj[key]))
        if (Object.prototype.toString.call(obj[key]) != '[object Object]') {
            let res = `${key}=${obj[key]}`
            // uniCloud.logger.info(res)
            return res.toString()
        } else {
            let objSign = getSignStr(obj[key])
            // uniCloud.logger.info('objSign')
            // uniCloud.logger.info(objSign.toString())
            let res = `${key}=${objSign}`
            // uniCloud.logger.info((JSON.stringify(objSign)))
            return res.toString()
        }
    }).join('&')
    return signStr.toString()
}
