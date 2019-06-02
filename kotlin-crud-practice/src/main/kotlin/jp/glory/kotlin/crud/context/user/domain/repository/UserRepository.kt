package jp.glory.kotlin.crud.context.user.domain.repository

import jp.glory.kotlin.crud.context.user.domain.entity.User
import jp.glory.kotlin.crud.context.user.domain.value.RegisteredUserId

/**
 * ユーザリポジトリ.
 */
interface UserRepository {

    /**
     * 全てのユーザを取得する.
     *
     * @return ユーザリスト
     */
    fun findAll() : List<User>

    /**
     * ユーザIDで検索する.
     *
     * @param id ユーザID
     * @return ユーザ
     */
    fun findByUserId(id : RegisteredUserId) : User?
}