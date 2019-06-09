package jp.glory.kotlin.crud.context.user.web.response

import jp.glory.kotlin.crud.context.user.usecase.SearchUser
import java.time.LocalDate

/**
 * ユーザのレスポンス.
 *
 * @param userResult ユーザの検索結果
 */
class UserResponse(private val userResult: SearchUser.UserResult) {


    /**
     * ユーザID.
     */
    val userId: Long = userResult.userId

    /**
     * 姓.
     */
    val lastName: String = userResult.lastName

    /**
     * 名.
     */
    val firstName: String = userResult.firstName

    /**
     * 誕生日.
     */
    val birthDay: LocalDate = userResult.birthDay

    /**
     * 年齢.
     */
    val age: Int = userResult.age
}