package jp.glory.kotlin.crud.context.user.web.request

/**
 * ユーザ保存リクエスト.
 *
 * @param lastName 姓
 * @param firstName 名
 * @param birthDay 誕生日
 */
class SaveUserRequest(
    var lastName: String = "",
    var firstName: String = "",
    var birthDay: String = ""
)