package jp.glory.kotlin.crud.externals.doma.user.holder

import org.seasar.doma.Entity
import org.seasar.doma.Id
import org.seasar.doma.Table
import java.time.LocalDate

/**
 * usersテーブル.
 *
 * @param userId ユーザID
 * @param lastName 姓
 * @param firstName 名
 * @param birthDay 誕生日
 */
@Entity(immutable = true)
@Table(name = "users")
class UsersTable(
    @Id val userId: Long,
    val lastName: String,
    val firstName: String,
    val birthDay: LocalDate
)