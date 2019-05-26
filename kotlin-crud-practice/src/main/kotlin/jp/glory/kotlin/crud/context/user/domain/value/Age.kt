package jp.glory.kotlin.crud.context.user.domain.value

/**
 * 0才オブジェクト.
 */
private val zero = Age(0)

/**
 * 0才のオブジェクトを取得する.
 * @return 0才オブジェクト
 */
fun getZeroAge(): Age {

    return zero
}

/**
 * 年齢.
 */
class Age(val value: Int) {
    companion object
}