package jp.glory.kotlin.crud.context.base.usecase.error

import jp.glory.kotlin.crud.context.base.domain.error.ValidationErrors


/**
 * ユースケースの実行結果.
 *
 * ユースケースを実行した際に失敗する可能性がある場合に使用する。
 *
 * @param T 返す型
 */
sealed class UsecaseResult<T>


/**
 * ユースケースの実行に成功した場合.
 *
 * @param value ユースケースで実行した結果
 * @param T ユースケースで実行した結果の型
 */
class SuccessResult<T>(val value: T) : UsecaseResult<T>()

/**
 * ユースケースの実行に失敗した場合.
 *
 * @param errors エラーリスト
 */
class FailedResult<T>(private val errors: ValidationErrors) : UsecaseResult<T>() {

    /**
     * メッセージリスト.
     */
    val messages: List<String> = errors.toList().map { it.createMessage() }
}