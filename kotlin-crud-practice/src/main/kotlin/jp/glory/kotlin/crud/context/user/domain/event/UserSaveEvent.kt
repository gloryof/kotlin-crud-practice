package jp.glory.kotlin.crud.context.user.domain.event

import jp.glory.kotlin.crud.context.user.domain.value.*

/**
 * ユーザ保存イベント.
 * @param userId ユーザID
 * @param userName ユーザ名
 * @param birthDay 誕生日
 */
class UserSaveEvent (val userId: UserId, val userName: UserName, val birthDay: BirthDay) {

    /**
     * タイプ.
     */
    enum class Type {

        /**
         * 更新.
         */
        Update,

        /**
         * 登録.
         */
        Register
    }

    /**
     * イベントのタイプを返す.
     *
     * @return タイプ
     */
    fun getType() : Type {
        return when(userId) {
            is RegisteredUserId ->  Type.Update
            is NotRegisteredUserId -> Type.Register
        }
    }
}