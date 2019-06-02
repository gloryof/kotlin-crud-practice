package jp.glory.kotlin.crud.context.user.domain.repository

import jp.glory.kotlin.crud.context.user.domain.event.UserSaveEvent

/**
 * [UserSaveEvent]のリポジトリ.
 */
interface UserSaveEventRepository {
    /**
     * ユーザ保存イベントを保存する.
     *
     * @param userSaveEvent ユーザ保存イベント
     */
    fun save(userSaveEvent: UserSaveEvent)
}