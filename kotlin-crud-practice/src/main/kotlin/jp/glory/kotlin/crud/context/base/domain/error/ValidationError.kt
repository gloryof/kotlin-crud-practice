package jp.glory.kotlin.crud.context.base.domain.error

import java.text.MessageFormat

/**
 * 入力チェックのエラー.
 * @param errorInfo エラー情報
 * @param messageParam メッセージパラメータ
 */
class ValidationError(private val errorInfo: ErrorInfo, private val messageParam: Array<Any>) {

    /**
     * エラーメッセージを作成する.
     *
     * @return エラーメッセージ
     */
    fun createMessage(): String = MessageFormat.format(errorInfo.message, *messageParam)
}