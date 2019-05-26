package jp.glory.kotlin.crud.context.base.domain.error

/**
 * エラー情報.
 * @param message メッセージ
 */
enum class ErrorInfo(val message: String) {


    /** 必須入力チェックエラー. */
    Required("{0}は必須です。"),

    /** 文字数オーバーエラー. */
    MaxLengthOver("{0}は{1}文字以内で入力してください。"),

    /** 日付形式エラー. */
    InvalidFormatDate("日付形式が不正です。YYYY-MM-DD形式で入力してください。")
}