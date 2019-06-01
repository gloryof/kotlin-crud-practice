package jp.glory.kotlin.crud.context.user.domain.value

/**
 * ユーザID.
 */
sealed class UserId

/**
 * 登録済みのユーザID.
 * @param value ユーザIDの値
 */
class RegisteredUserId(val value: Long) : UserId()

/**
 * 未登録を表すユーザID.
 */
object NotRegisteredUserId : UserId()

