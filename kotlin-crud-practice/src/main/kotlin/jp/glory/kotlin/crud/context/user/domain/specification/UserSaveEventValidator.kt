package jp.glory.kotlin.crud.context.user.domain.specification

import jp.glory.kotlin.crud.context.base.domain.error.InvalidResult
import jp.glory.kotlin.crud.context.base.domain.error.ValidResult
import jp.glory.kotlin.crud.context.base.domain.error.ValidationErrors
import jp.glory.kotlin.crud.context.base.domain.error.ValidationResult
import jp.glory.kotlin.crud.context.user.domain.event.UserSaveEvent
import jp.glory.kotlin.crud.context.user.domain.repository.UserRepository
import jp.glory.kotlin.crud.context.user.domain.value.BirthDay
import jp.glory.kotlin.crud.context.user.domain.value.RegisteredUserId
import jp.glory.kotlin.crud.context.user.domain.value.UserId
import jp.glory.kotlin.crud.context.user.domain.value.UserName
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle

/**
 * ユーザ保存イベントの入力チェックを行う.
 *
 * @param userRepository ユーザリポジトリ
 * @param lastName 姓
 * @param firstName 名
 * @param birthDay 誕生日
 */
class UserSaveEventValidator(
        private val userRepository: UserRepository,
        private val lastName: String = "",
        private val firstName: String = "",
        private val birthDay: String = "") {

    /**
     * 未登録のユーザの入力チェックを行う.
     *
     * - 姓のチェックを行う([validateLastName])
     * - 名のチェックを行う([validateFirstName])
     * - 誕生日のチェックを行う([validateBirthDay])
     * - 登録済みユーザIDの場合ユーザの存在チェックを行う([validateExistUser])
     *
     * @param id ユーザID
     * @return 入力チェック結果
     */
    fun validate(id: UserId): ValidationResult<UserSaveEvent> {

        val result = ValidationErrors()

        validateLastName(result)
        validateFirstName(result)
        validateBirthDay(result)

        when (id) {
            is RegisteredUserId -> validateExistUser(result, id)
        }


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

        if (lastName.trim().isEmpty()) {

            errors.addRequiredError(itemName)
            return
        }

        if (lastName.length > maxLength) {

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

        if (firstName.trim().isEmpty()) {

            errors.addRequiredError(itemName)
            return
        }

        if (firstName.length > maxLength) {

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


        if (birthDay.trim().isEmpty()) {

            errors.addRequiredError(itemName)
            return
        }

        if (!isValidBirthDayFormat()) {

            errors.addInvalidFormatDate(itemName)
        }

    }

    /**
     * ユーザの存在チェックを行う.
     *
     * @param errors エラーリスト
     * @param registeredUserId 登録済みユーザID
     */
    private fun validateExistUser(errors: ValidationErrors, registeredUserId: RegisteredUserId) {


        if (userRepository.findByUserId(registeredUserId) == null) {

            errors.addNotExistUser()
        }
    }

    /**
     * モデルに変換する.
     *
     * @param id ユーザID
     * @return ユーザモデル
     */
    private fun convert(id: UserId): UserSaveEvent {

        return UserSaveEvent(
                userId = id,
                userName = UserName(lastName, firstName),
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
        } catch (e: Exception) {

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