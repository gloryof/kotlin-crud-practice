package jp.glory.kotlin.crud.context.user.infra

import jp.glory.kotlin.crud.context.user.domain.entity.User
import jp.glory.kotlin.crud.context.user.domain.repository.UserRepository
import jp.glory.kotlin.crud.context.user.domain.value.BirthDay
import jp.glory.kotlin.crud.context.user.domain.value.RegisteredUserId
import jp.glory.kotlin.crud.context.user.domain.value.UserName
import jp.glory.kotlin.crud.externals.doma.user.dao.UsersDao
import jp.glory.kotlin.crud.externals.doma.user.holder.UsersTable
import org.springframework.stereotype.Repository

/**
 * ユーザリポジトリのDB実装.
 *
 * @param usersDao usersテーブルDAO.
 */
@Repository
class UserRepositoryDbImpl(private val usersDao: UsersDao) : UserRepository {

    override fun findAll(): List<User> {

        return usersDao.selectAll().map { createUser(it) }
    }

    override fun findByUserId(id: RegisteredUserId): User? {

        val result: UsersTable = usersDao.selectById(id.value) ?: return null

        return createUser(result)
    }

    /**
     * usersテーブルのレコードからユーザエンティティを作成する.
     *
     * @return ユーザエンティティ
     */
    private fun createUser(record: UsersTable): User {

        return User(
            userId = RegisteredUserId(record.userId),
            userName = UserName(
                lastName = record.lastName,
                firstName = record.firstName
            ),
            birthDay = BirthDay(record.birthDay)
        )
    }
}