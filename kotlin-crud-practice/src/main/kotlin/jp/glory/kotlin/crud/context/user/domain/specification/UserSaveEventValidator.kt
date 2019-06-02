package jp.glory.kotlin.crud.context.user.domain.specification

import jp.glory.kotlin.crud.context.base.domain.error.*
import jp.glory.kotlin.crud.context.user.domain.entity.User
import jp.glory.kotlin.crud.context.user.domain.event.UserSaveEvent
import jp.glory.kotlin.crud.context.user.domain.value.*
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle

/**
 * ユーザ保存イベントの入力チェックを行う.
 */
class UserSaveEventValidator {

    /**
     * 姓.
     */
    var lastName: String? = null

    /**
     * 名.
     */
    var firstName: String? = null

    /**
     * 誕生日.
     */
    var birthDay: String? = null

    /**
     * 未登録のユーザの入力チェックを行う.
     *
     * @param id ユーザID
     * @return 入力チェック結果
     */
    fun validate(id: UserId): ValidationResult<UserSaveEvent> {

        val result = ValidationErrors()

        validateLastName(result)
        validateFirstName(result)
        validateBirthDay(result)

        if (result.hasError()) {

            return InvalidResult(result)
        }

        return ValidResult(convert(id))
    }

    /**
     * 姓の入力チェックを行う.
     *
     * 下記のチェックを行う。
     * - 値が設定されていること
     * - 値が30文字を超えていないこと
     *
     * @param errors エラーリスト
     */
    private fun validateLastName(errors: ValidationErrors) {

        val itemName = "姓"
        val maxLength = 30
        val value = lastName ?: ""

        if (value.trim().isEmpty()) {

            errors.addRequiredError(itemName)
            return
        }

        if (value.length > maxLength) {

            errors.addMaxLength(itemName = itemName, length = maxLength)
        }
    }

    /**
     * 名の入力チェックを行う.
     *
     * 下記のチェックを行う。
     * - 値が設定されていること
     * - 値が30文字を超えていないこと
     *
     * @param errors エラーリスト
     */
    private fun validateFirstName(errors: ValidationErrors) {

        val itemName = "名"
        val maxLength = 30
        val value = firstName ?: ""

        if (value.trim().isEmpty()) {

            errors.addRequiredError(itemName)
            return
        }

        if (value.length > maxLength) {

            errors.addMaxLength(itemName = itemName, length = maxLength)
        }
    }

    /**
     * 誕生日の入力チェックを行う.
     *
     * 下記のチェックを行う。
     * - 値が設定されていること
     * - 値が30文字を超えていないこと
     *
     * @param errors エラーリスト
     */
    private fun validateBirthDay(errors: ValidationErrors) {

        val itemName = "誕生日"
        val value = birthDay ?: ""


        if (value.trim().isEmpty()) {

            errors.addRequiredError(itemName)
            return
        }

        if (!isValidBirthDayFormat()) {

            errors.addInvalidFormatDate(itemName)
        }

    }

    /**
     * モデルに変換する.
     *
     * @param id ユーザID
     * @return ユーザモデル
     */
    private fun convert(id: UserId): UserSaveEvent {

        return  UserSaveEvent(
                userId = id,
                userName = UserName(lastName ?: "", firstName ?: ""),
                birthDay = BirthDay(converFromToBirthDay())
        )
    }

    /**
     * 誕生日の日付フォーマット通りかを判定する.
     *
     */
    private fun isValidBirthDayFormat(): Boolean {

        return try {

            converFromToBirthDay()
            true
        } catch (e :Exception) {

            false
        }
    }

    private fun converFromToBirthDay(): LocalDate {

        return DateTimeFormatter
                    .ofPattern("uuuu-MM-dd")
                    .withResolverStyle(ResolverStyle.STRICT)
                    .parse(birthDay, LocalDate::from)
    }
}