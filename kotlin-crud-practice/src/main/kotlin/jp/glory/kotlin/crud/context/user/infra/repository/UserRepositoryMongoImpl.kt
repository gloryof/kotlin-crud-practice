package jp.glory.kotlin.crud.context.user.infra.repository

import jp.glory.kotlin.crud.context.user.domain.entity.User
import jp.glory.kotlin.crud.context.user.domain.repository.UserRepository
import jp.glory.kotlin.crud.context.user.domain.value.BirthDay
import jp.glory.kotlin.crud.context.user.domain.value.RegisteredUserId
import jp.glory.kotlin.crud.context.user.domain.value.UserName
import jp.glory.kotlin.crud.externals.mongodb.user.collection.UsersCollection
import jp.glory.kotlin.crud.externals.mongodb.user.dao.UsersDao
import org.springframework.stereotype.Repository
import java.util.*

/**
 * ユーザリポジトリのDB実装.
 *
 * @param usersDao usersテーブルDAO.
 */
@Repository
class UserRepositoryMongoImpl(private val usersDao: UsersDao) : UserRepository {

    override fun findAll(): List<User> {

        return usersDao.findAll().map { createUser(it) }
    }

    override fun findByUserId(id: RegisteredUserId): User? {

        val opt: Optional<UsersCollection> = usersDao.findById(id.value)

        return opt.map { createUser(it) }.orElse(null)
    }


    /**
     * usersテーブルのレコードからユーザエンティティを作成する.
     *
     * @return ユーザエンティティ
     */
    private fun createUser(record: UsersCollection): User {

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