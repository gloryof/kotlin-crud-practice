package jp.glory.kotlin.crud.context.user.web.response

import jp.glory.kotlin.crud.context.user.usecase.SearchUser

/**
 * ユーザのリストレスポンス.
 *
 * @param results ユーザの検索結果リスト
 */
class UsersResponse(private val results: List<SearchUser.UserResult>) {

    /**
     * ユーザリスト.
     */
    val users: List<UserResponse> = results.map { UserResponse(it) }
}