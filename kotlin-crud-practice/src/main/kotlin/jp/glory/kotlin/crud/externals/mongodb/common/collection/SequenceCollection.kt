package jp.glory.kotlin.crud.externals.mongodb.common.collection

import org.springframework.data.mongodb.core.mapping.Document

/**
 * sequenceコレクション.
 *
 * @param name シーケンス名
 * @param value 値
 */
@Document("sequence")
data class SequenceCollection(val name: String, val value: Long)