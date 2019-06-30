package jp.glory.kotlin.crud.externals.mongodb.user.dao

import jp.glory.kotlin.crud.externals.mongodb.common.dao.SequenceDao
import jp.glory.kotlin.crud.externals.mongodb.user.collection.UsersCollection
import org.springframework.data.repository.CrudRepository
import java.util.*

/**
 * usersテーブルDao.
 */
interface UsersDao : CrudRepository<UsersCollection, Long>, SequenceDao {
    /**
     * ユーザIDをキーに検索する.
     *
     * @param userId ユーザID
     * @return ユーザコレクション
     */
    fun findByUserId(userId: Long): Optional<UsersCollection>
}