package jp.glory.kotlin.crud.context.user.infra.repository

import jp.glory.kotlin.crud.context.user.domain.event.UserSaveEvent
import jp.glory.kotlin.crud.context.user.domain.repository.UserSaveEventRepository
import jp.glory.kotlin.crud.context.user.domain.value.NotRegisteredUserId
import jp.glory.kotlin.crud.context.user.domain.value.RegisteredUserId
import jp.glory.kotlin.crud.context.user.domain.value.UserId
import jp.glory.kotlin.crud.externals.doma.user.dao.UsersDao
import jp.glory.kotlin.crud.externals.doma.user.holder.UsersTable
import org.springframework.stereotype.Repository

/**
 * ユーザ保存イベントリポジトリのDB実装.
 *
 * @param usersDao usersのDAO
 */
@Repository
class UserSaveEventRepositoryDbImpl(private val usersDao: UsersDao) : UserSaveEventRepository {

    override fun save(userSaveEvent: UserSaveEvent): RegisteredUserId {

        return when (val userId: UserId = userSaveEvent.userId) {
            is RegisteredUserId -> update(userId, userSaveEvent)
            is NotRegisteredUserId -> register(userSaveEvent)
        }
    }

    /**
     * usersテーブルにレコードを登録する.
     *
     * @param userSaveEvent ユーザ保存イベント
     * @return usersテーブルレコード
     */
    private fun register(userSaveEvent: UserSaveEvent): RegisteredUserId {

        val newUserId: Long = usersDao.getNextUserId()

        val record = UsersTable(
            userId = newUserId,
            lastName = userSaveEvent.userName.lastName,
            firstName = userSaveEvent.userName.firstName,
            birthDay = userSaveEvent.birthDay.value
        )

        usersDao.insert(record)

        return RegisteredUserId(newUserId)
    }

    /**
     * usersテーブルのレコードを更新する.
     *
     * @param userId ユーザID
     * @param userSaveEvent ユーザ保存イベント
     * @return usersテーブルレコード
     */
    private fun update(userId: RegisteredUserId, userSaveEvent: UserSaveEvent): RegisteredUserId {

        val record = UsersTable(
            userId = userId.value,
            lastName = userSaveEvent.userName.lastName,
            firstName = userSaveEvent.userName.firstName,
            birthDay = userSaveEvent.birthDay.value
        )

        usersDao.update(record)

        return userId
    }
}