package jp.glory.kotlin.crud.context.user.usecase

import jp.glory.kotlin.crud.context.base.domain.error.InvalidResult
import jp.glory.kotlin.crud.context.base.domain.error.ValidResult
import jp.glory.kotlin.crud.context.base.domain.error.ValidationResult
import jp.glory.kotlin.crud.context.base.usecase.Usecase
import jp.glory.kotlin.crud.context.base.usecase.error.FailedResult
import jp.glory.kotlin.crud.context.base.usecase.error.SuccessResult
import jp.glory.kotlin.crud.context.base.usecase.error.UsecaseResult
import jp.glory.kotlin.crud.context.user.domain.event.UserSaveEvent
import jp.glory.kotlin.crud.context.user.domain.repository.UserRepository
import jp.glory.kotlin.crud.context.user.domain.repository.UserSaveEventRepository
import jp.glory.kotlin.crud.context.user.domain.specification.UserSaveEventValidator
import jp.glory.kotlin.crud.context.user.domain.value.NotRegisteredUserId
import jp.glory.kotlin.crud.context.user.domain.value.RegisteredUserId

/**
 * ユーザを保存する.
 *
 * @param searchRepository ユーザ検索リポジトリ
 * @param saveRepository ユーザ保存イベントリポジトリ
 */
@Usecase
class SaveUser(
        private val searchRepository: UserRepository,
        private val saveRepository: UserSaveEventRepository) {

    /**
     * ユーザの登録を行う.
     *
     * @param param 登録パラメータ
     * @return 成功した場合は登録されたユーザID
     */
    fun register(param: RegisterParam): UsecaseResult<Long> {

        val validator = UserSaveEventValidator(
                userRepository = searchRepository,
                lastName = param.lastName,
                firstName = param.firstName,
                birthDay = param.birthDay
        )

        val event: UserSaveEvent = when (val result: ValidationResult<UserSaveEvent> = validator.validate(NotRegisteredUserId)) {
            is ValidResult<UserSaveEvent> -> result.value
            is InvalidResult<UserSaveEvent> -> return FailedResult(result.errors)
        }

        val userId: RegisteredUserId = saveRepository.save(event)

        return SuccessResult(userId.value)
    }

    /**
     * ユーザの更新を行う.
     *
     * @param param 更新パラメータ
     * @return 成功した場合は更新されたユーザID
     */
    fun update(param: UpdateParam): UsecaseResult<Long> {


        val validator = UserSaveEventValidator(
                userRepository = searchRepository,
                lastName = param.lastName,
                firstName = param.firstName,
                birthDay = param.birthDay
        )

        val registeredUserId = RegisteredUserId(param.userId)
        val event: UserSaveEvent = when (val result: ValidationResult<UserSaveEvent> = validator.validate(registeredUserId)) {
            is ValidResult<UserSaveEvent> -> result.value
            is InvalidResult<UserSaveEvent> -> return FailedResult(result.errors)
        }

        val userId: RegisteredUserId = saveRepository.save(event)

        return SuccessResult(userId.value)
    }


    /**
     * 登録パラメータ.
     *
     * @param lastName 姓
     * @param firstName 名
     * @param birthDay 誕生日
     */
    data class RegisterParam(
            val lastName: String = "",
            val firstName: String = "",
            val birthDay: String = "")

    /**
     * 更新パラメータ.
     *
     * @param userId ユーザID
     * @param lastName 姓
     * @param firstName 名
     * @param birthDay 誕生日
     */
    data class UpdateParam(
            val userId: Long,
            val lastName: String = "",
            val firstName: String = "",
            val birthDay: String = "")
}

