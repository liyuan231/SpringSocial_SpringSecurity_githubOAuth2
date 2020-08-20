package com.liyuan.wechat;

public interface UrlConstants {
    /*第1步：请求CODE
     第三方使用网站应用授权登录前请注意已获取相应网页授权作用域
     （scope=snsapi_login），
     则可以通过在PC端打开以下链接：
     https://open.weixin.qq.com/connect/qrconnect?
     appid=APPID&redirect_uri=REDIRECT_URI&
     response_type=code&scope=SCOPE&state=STATE#wechat_redirect
     若提示“该链接无法访问”，请检查参数是否填写错误，
     如redirect_uri的域名与审核时填写的授权域名不一致或scope
     不为snsapi_login。
    **/
    /**
     * 用户允许授权后，将会重定向到redirect_uri的网址上，
     * 并且带上code和state参数
     * redirect_uri?code=CODE&state=STATE
     * 请求示例
     *
     * 登录一号店网站应用 https://passport.yhd.com/wechat/login.do
     * 打开后，一号店会生成state参数，跳转到 https://open.weixin.qq.com
     * /connect/qrconnect?appid=wxbdc5610cc59c1631&
     * redirect_uri=https%3A%2F%2Fpassport.yhd.com%2Fwechat%2Fcallback.do&
     * response_type=code&scope=snsapi_login&state=3d6be0a4035d839573b04816624a415e#wechat_redirect
     * 微信用户使用微信扫描二维码并且确认登录后，PC端会跳转到
     * https://passport.yhd.com/wechat/callback.do?code=CODE&state=3d6be0a4035d839573b04816624a415e
     * 为了满足网站更定制化的需求，我们还提供了第二种获取code的方式，支持网站将微信登录二维码内嵌到自己页面中，
     * 用户使用微信扫码授权后通过JS将code返回给网站。 JS微信登录主要用途：网站希望用户在网站内就能完成登录，
     * 无需跳转到微信域下登录后再返回，提升微信登录的流畅性与成功率。 网站内嵌二维码微信登录JS实现办法：
     */
    String QRCONNECT_API_URL = "https://open.weixin.qq.com/connect/qrconnect";


    /**
     * 第2步通过code获取access_token
     * https://api.weixin.qq.com/sns/oauth2/access_token?
     * appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
     *
     * 正确的返回：
     * {
     * "access_token":"ACCESS_TOKEN",
     * "expires_in":7200,
     * "refresh_token":"REFRESH_TOKEN",
     * "openid":"OPENID",
     * "scope":"SCOPE",
     * "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
     * }
     * 错误返回样例：
     *
     * {"errcode":40029,"errmsg":"invalid code"}
     *
     */
    String ACCESS_TOKEN_API_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    /**
     * 刷新access_token有效期
     * access_token是调用授权关系接口的调用凭证，由于access_token有效期
     * （目前为2个小时）较短，当access_token超时后，
     * 可以使用refresh_token进行刷新，access_token刷新结果有两种：
     * 1. 若access_token已超时，那么进行refresh_token会获取一个新的access_token，新的超时时间；
     * 2. 若access_token未超时，那么进行refresh_token不会改变access_token，
     * 但超时时间会刷新，相当于续期access_token。
     * https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN
     * 正确的返回：
     *
     * {
     * "access_token":"ACCESS_TOKEN",
     * "expires_in":7200,
     * "refresh_token":"REFRESH_TOKEN",
     * "openid":"OPENID",
     * "scope":"SCOPE"
     * }
     * 错误返回样例：
     *
     * {"errcode":40030,"errmsg":"invalid refresh_token"}
     */
    String REFRESH_TOKEN_API_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";

    /**
     * 第三步：通过access_token调用接口
     *
     * 获取access_token后，进行接口调用，有以下前提：
     *
     * 1. access_token有效且未超时；
     * 2. 微信用户已授权给第三方应用帐号相应接口作用域（scope）。
     * snsapi_base	/sns/auth	检查access_token有效性
     */
    String AUTH_API_URL = "https://api.weixin.qq.com/sns/auth";
    /**
     * 续：snsapi_userinfo	/sns/userinfo	获取用户个人信息
     */
    String USERINFO_API_URL = "https://api.weixin.qq.com/sns/userinfo";

    /**
     * 这是微信公众平台的，反正不是扫码登录的那个
     */
    String AUTHORIZE_API_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";

}
