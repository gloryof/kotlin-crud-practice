package jp.glory.kotlin.crud.context.user.domain.entity

import jp.glory.kotlin.crud.context.user.domain.value.BirthDay
import jp.glory.kotlin.crud.context.user.domain.value.RegisteredUserId
import jp.glory.kotlin.crud.context.user.domain.value.UserName

/**
 * ユーザ.
 * @param userId ユーザID
 * @param userName ユーザ名
 * @param birthDay 誕生日
 */
class User(val userId: RegisteredUserId, val userName: UserName, val birthDay: BirthDay) {
}