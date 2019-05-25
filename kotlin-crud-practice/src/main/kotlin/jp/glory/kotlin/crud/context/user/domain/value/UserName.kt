package jp.glory.kotlin.crud.context.user.domain.value

/**
 * ユーザ名.
 */
class UserName(val lastName: String, val firstName: String) {

    init {
        assert(this.lastName.isNotEmpty())
        assert(this.firstName.isNotEmpty())
    }

    /**
     * フルネームを返す.
     * @return フルネーム
     */
    fun getFullName(): String = """$lastName $firstName"""
}