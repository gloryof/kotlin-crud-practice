package jp.glory.kotlin.crud.externals.mongodb.common.collection

/**
 * sequenceコレクション.
 *
 * @param name シーケンス名
 * @param value 値
 */
data class SequenceCollection(val name: String, val value: Long)