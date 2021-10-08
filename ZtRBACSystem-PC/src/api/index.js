import request from '@/utils/request'

export default {
  get: function (url, params) {
    return request({
      url: url,
      method: 'get',
      data: params
    })
  },
  post: function (url, params) {
    console.log('url:' + JSON.stringify(url))
    console.log('params:' + JSON.stringify(params))
    return request({
      url: url,
      method: 'post',
      data: params
    })
  }
}
