package jp.glory.kotlin.crud.context.user.domain.repository

import jp.glory.kotlin.crud.context.user.domain.event.UserSaveEvent
import jp.glory.kotlin.crud.context.user.domain.value.RegisteredUserId

/**
 * [UserSaveEvent]のリポジトリ.
 */
interface UserSaveEventRepository {
    /**
     * ユーザ保存イベントを保存する.
     *
     * @param userSaveEvent ユーザ保存イベント
     * @return 登録されたユーザのID
     */
    fun save(userSaveEvent: UserSaveEvent): RegisteredUserId
}