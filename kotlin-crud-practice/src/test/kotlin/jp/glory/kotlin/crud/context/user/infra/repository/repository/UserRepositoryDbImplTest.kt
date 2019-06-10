package jp.glory.kotlin.crud.context.user.infra.repository.repository

import io.mockk.every
import io.mockk.mockk
import jp.glory.kotlin.crud.context.user.domain.entity.User
import jp.glory.kotlin.crud.context.user.domain.value.RegisteredUserId
import jp.glory.kotlin.crud.context.user.infra.repository.UserRepositoryDbImpl
import jp.glory.kotlin.crud.externals.doma.user.dao.UsersDao
import jp.glory.kotlin.crud.externals.doma.user.holder.UsersTable
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class UserRepositoryDbImplTest {

    private var sut: UserRepositoryDbImpl? = null
    private var mockRepository: UsersDao? = null

    private val expectedUser1 = UsersTable(
        userId = 1000,
        lastName = "テスト姓1",
        firstName = "テスト名1",
        birthDay = LocalDate.of(1986, 12, 16)
    )

    private val expectedUser2 = UsersTable(
        userId = 1001,
        lastName = "テスト姓2",
        firstName = "テスト名2",
        birthDay = LocalDate.of(1987, 1, 17)
    )

    private val expectedUser3 = UsersTable(
        userId = 1002,
        lastName = "テスト姓3",
        firstName = "テスト名3",
        birthDay = LocalDate.of(1988, 2, 18)
    )

    @BeforeEach
    fun setUp() {

        mockRepository = mockk()
        sut = UserRepositoryDbImpl(mockRepository!!)
    }

    @DisplayName("findAllのテスト")
    @Nested
    inner class TestFindAll {

        @BeforeEach
        fun setUp() {

            every { mockRepository!!.selectAll() } returns listOf(expectedUser1, expectedUser2, expectedUser3)
        }

        @DisplayName("全てのユーザが返る")
        @Test
        fun testReturnVal() {

            val actual: List<User> = sut!!.findAll()

            assertEquals(3, actual.size)

            val actualUser1: User = actual[0]
            assertEquals(expectedUser1.userId, actualUser1.userId.value)
            assertEquals(expectedUser1.lastName, actualUser1.userName.lastName)
            assertEquals(expectedUser1.firstName, actualUser1.userName.firstName)
            assertEquals(expectedUser1.birthDay, actualUser1.birthDay.value)

            val actualUser2: User = actual[1]
            assertEquals(expectedUser2.userId, actualUser2.userId.value)
            assertEquals(expectedUser2.lastName, actualUser2.userName.lastName)
            assertEquals(expectedUser2.firstName, actualUser2.userName.firstName)
            assertEquals(expectedUser2.birthDay, actualUser2.birthDay.value)

            val actualUser3: User = actual[2]
            assertEquals(expectedUser3.userId, actualUser3.userId.value)
            assertEquals(expectedUser3.lastName, actualUser3.userName.lastName)
            assertEquals(expectedUser3.firstName, actualUser3.userName.firstName)
            assertEquals(expectedUser3.birthDay, actualUser3.birthDay.value)
        }
    }

    @DisplayName("findByUserIdのテスト")
    @Nested
    inner class TestFindByUserId {

        @BeforeEach
        fun setUp() {

            every { mockRepository!!.selectById(1001) } returns expectedUser2
            every { mockRepository!!.selectById(9999) } returns null
        }

        @DisplayName("レコードが存在する場合")
        @Test
        fun whenRecordExist() {

            val actual: User? = sut!!.findByUserId(RegisteredUserId(1001))

            if (actual != null) {
                assertEquals(expectedUser2.userId, actual.userId.value)
                assertEquals(expectedUser2.lastName, actual.userName.lastName)
                assertEquals(expectedUser2.firstName, actual.userName.firstName)
                assertEquals(expectedUser2.birthDay, actual.birthDay.value)
            } else {

                fail("実測値がNullです")
            }
        }

        @DisplayName("レコードが存在しない場合")
        @Test
        fun whenRecordIsNotExist() {

            val actual: User? = sut!!.findByUserId(RegisteredUserId(9999))

            assertNull(actual)
        }
    }
}