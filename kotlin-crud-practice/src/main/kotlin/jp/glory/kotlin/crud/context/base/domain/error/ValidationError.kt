package jp.glory.kotlin.crud.context.base.domain.error

/**
 * 入力チェックのエラー.
 * @param errorInfo エラー情報
 * @param messageParam メッセージパラメータ
 */
data class ValidationError(val errorInfo: ErrorInfo, var messageParam:Array<Any>)