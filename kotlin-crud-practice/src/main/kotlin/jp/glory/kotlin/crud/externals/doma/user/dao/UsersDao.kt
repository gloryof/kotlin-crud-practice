package jp.glory.kotlin.crud.externals.doma.user.dao

import jp.glory.kotlin.crud.externals.doma.user.holder.UsersTable
import org.seasar.doma.Dao
import org.seasar.doma.Insert
import org.seasar.doma.Select
import org.seasar.doma.Update
import org.seasar.doma.experimental.Sql

/**
 * usersテーブルDao.
 */
@Dao
interface UsersDao {

    /**
     * 全レコードを取得する.
     *
     * @return usersレコードリスト
     */
    @Select
    @Sql("SELECT /*%expand*/* FROM users")
    fun selectAll(): List<UsersTable>

    /**
     * IDをキーにレコードを取得する.
     *
     * @param id ユーザID
     * @return usersレコード
     */
    @Select
    @Sql("SELECT /*%expand*/* FROM users WHERE user_id = /*id*/1")
    fun selectById(id: Long): UsersTable?

    /**
     * 次のユーザID値を取得する.
     */
    @Select
    @Sql("SELECT nextval('user_id_seq')")
    fun getNextUserId(): Long

    /**
     * 登録を行う.
     */
    @Insert
    fun insert(usersTable: UsersTable)

    /**
     * 更新を行う.
     */
    @Update
    fun update(usersTable: UsersTable)
}