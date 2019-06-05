package jp.glory.kotlin.crud.context.base.domain.error

/**
 * 入力チェックエラーのリスト.
 */
class ValidationErrors {

    /**
     * エラーリスト.
     */
    private val errors: MutableList<ValidationError> = mutableListOf()

    /**
     * 必須エラーを追加する.
     * @param itemName 項目名
     */
    fun addRequiredError(itemName: String) {

        errors.add(ValidationError(ErrorInfo.Required, arrayOf(itemName)))
    }

    /**
     * 文字数エラーを追加する.
     * @param itemName 項目名
     * @param length 文字数
     */
    fun addMaxLength(itemName: String, length: Int) {

        errors.add(ValidationError(ErrorInfo.MaxLengthOver, arrayOf(itemName, length)))
    }

    /**
     * 日付形式エラーを追加する.
     * @param itemName 項目名
     */
    fun addInvalidFormatDate(itemName: String) {

        errors.add(ValidationError(ErrorInfo.InvalidFormatDate, arrayOf(itemName)))
    }

    /**
     * ユーザが存在しないエラーを追加する.
     */
    fun addNotExistUser() {

        errors.add(ValidationError(ErrorInfo.NotExistUser, arrayOf()))
    }

    /**
     * エラーがあるかを判定する.
     * @return エラーがある場合：true、エラーがない場合：false
     */
    fun hasError(): Boolean = !errors.isEmpty()

    /**
     * エラー情報をリストで返す.
     * @return エラーリスト
     */
    fun toList(): List<ValidationError> {

        return errors.toList()
    }

    override fun toString(): String {
        return "ValidationErrors(errors=$errors)"
    }

}