package jp.glory.kotlin.crud.externals.mongodb.common.dao

/**
 * シーケンスコレクションのDAO.
 */
interface SequenceDao {


    /**
     * 次のユーザID値を取得する.
     */
    fun getNextUserId(): Long
}