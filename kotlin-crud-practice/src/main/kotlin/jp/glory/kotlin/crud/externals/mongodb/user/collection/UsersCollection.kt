package jp.glory.kotlin.crud.externals.mongodb.user.collection

import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

/**
 * usersコレクション.
 *
 * @param userId ユーザID
 * @param lastName 姓
 * @param firstName 名
 * @param birthDay 誕生日
 */
@Document("users")
data class UsersCollection(
    val id: String? = null,
    val userId: Long,
    val lastName: String,
    val firstName: String,
    val birthDay: LocalDate
)