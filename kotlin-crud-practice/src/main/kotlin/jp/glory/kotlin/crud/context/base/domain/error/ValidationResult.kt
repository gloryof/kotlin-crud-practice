package jp.glory.kotlin.crud.context.base.domain.error

/**
 * 入力チェックの結果クラス.
 */
sealed class ValidationResult<T>

/**
 * 入力チェックで正常と扱われた場合の結果.
 *
 * 入力チェックを行い、変換されたモデルが返る。
 * @param T モデルの型
 */
class ValidResult<T>(val value: T) : ValidationResult<T>()

/**
 * 入力チェックでエラーがあった場合の結果.
 */
data class InvalidResult<T>(val errors: ValidationErrors) : ValidationResult<T>()