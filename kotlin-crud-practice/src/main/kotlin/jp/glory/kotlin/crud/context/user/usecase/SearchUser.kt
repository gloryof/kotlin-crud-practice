package jp.glory.kotlin.crud.context.user.usecase

import jp.glory.kotlin.crud.context.base.usecase.Usecase
import jp.glory.kotlin.crud.context.user.domain.entity.User
import jp.glory.kotlin.crud.context.user.domain.repository.UserRepository
import jp.glory.kotlin.crud.context.user.domain.value.RegisteredUserId
import java.time.LocalDate

/**
 * ユーザの検索ユースケース.
 *
 * @param repository ユーザリポジトリ
 */
@Usecase
class SearchUser(private val repository: UserRepository) {

    /**
     * ユーザIDで検索する.
     *
     * @return ユーザの検索結果
     */
    fun findByUserId(userId: Long): UserResult? {

        val user: User? = repository.findByUserId(RegisteredUserId(userId))

        return if (user != null) {
            UserResult(user)
        } else {
            null
        }
    }

    /**
     * 全てのユーザを検索する.
     *
     * @return ユーザリスト
     */
    fun findAll(): List<UserResult> {

        return repository.findAll().map { UserResult(it) }
    }

    /**
     * ユーザの検索結果.
     *
     * @param user ユーザ
     */
    inner class UserResult(private val user: User) {

        /**
         * ユーザID.
         */
        val userId: Long = user.userId.value

        /**
         * 姓.
         */
        val lastName: String = user.userName.lastName

        /**
         * 名.
         */
        val firstName: String = user.userName.firstName

        /**
         * 誕生日.
         */
        val birthDay: LocalDate = user.birthDay.value
    }

}