package jp.glory.kotlin.crud.base.domain.error

/**
 * 入力チェックのエラー.
 * @param errorInfo エラー情報
 * @param messageParam メッセージパラメータ
 */
class ValidationError(val errorInfo: ErrorInfo, var messageParam:Array<Any>)