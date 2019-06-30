package jp.glory.kotlin.crud.externals.mongodb.common.dao

import jp.glory.kotlin.crud.externals.mongodb.common.collection.SequenceCollection
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update

/**
 * [SequenceDao]の実装クラス
 *
 * @param mongoTemplate MongoDBテンプレート
 */
class SequenceDaoImpl(private val mongoTemplate: MongoTemplate) : SequenceDao {

    /**
     * 次のユーザIDを返す.
     *
     * @return ユーザID
     */
    override fun getNextUserId(): Long {
        val seq: SequenceCollection? = mongoTemplate.findAndModify(
            createQuery(),
            createUpdate(),
            SequenceCollection::class.java
        )

        return seq!!.value
    }

    /**
     * クエリを作成する.
     *
     * @return クエリ
     */
    private fun createQuery(): Query {

        return Query.query(Criteria.where("name").`is`("user_sequence"))
    }

    /**
     * UPDATE句を作成する.
     *
     * @return UPDATE句
     */
    private fun createUpdate(): Update {

        return Update().inc("value", 1)
    }
}