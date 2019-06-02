package jp.glory.kotlin.crud.context.user.usecase

import io.mockk.every
import io.mockk.mockk
import jp.glory.kotlin.crud.context.user.domain.entity.User
import jp.glory.kotlin.crud.context.user.domain.repository.UserRepository
import jp.glory.kotlin.crud.context.user.domain.value.BirthDay
import jp.glory.kotlin.crud.context.user.domain.value.RegisteredUserId
import jp.glory.kotlin.crud.context.user.domain.value.UserName
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class SearchUserTest {

    var sut : SearchUser? = null
    var mockReository : UserRepository? = null

    val user1 = User(
            userId = RegisteredUserId(1001),
            userName = UserName("テスト姓1", "テスト名1"),
            birthDay = BirthDay(LocalDate.of(1986, 12,16)))

    val user2 = User(
            userId = RegisteredUserId(1002),
            userName = UserName("テスト姓2", "テスト名2"),
            birthDay = BirthDay(LocalDate.of(1987, 1,17)))

    val user3 = User(
            userId = RegisteredUserId(1003),
            userName = UserName("テスト姓3", "テスト名3"),
            birthDay = BirthDay(LocalDate.of(1988, 2,18)))

    @DisplayName("findByUserIdのテスト")
    @Nested
    inner class TestFindByUserId {

        @BeforeEach
        fun setUp() {

            mockReository = mockk(relaxed = true)
            sut = SearchUser(mockReository!!)
        }

        @DisplayName("ユーザが存在している場合")
        @Test
        fun whenExistUser() {

            every {
                mockReository!!.findByUserId(any())
            } returns user1

            val result : SearchUser.UserResult? = sut!!.findByUserId(1001)
            val actual: SearchUser.UserResult = result!!

            assertEquals(1001, actual.userId)
            assertEquals("テスト姓1", actual.lastName)
            assertEquals("テスト名1", actual.firstName)
            assertEquals(LocalDate.of(1986,12,16), actual.birthDay)
        }

        @DisplayName("ユーザが存在していない場合")
        @Test
        fun whenNotExistUser() {

            every {
                mockReository!!.findByUserId(any())
            } returns null

            val result : SearchUser.UserResult? = sut!!.findByUserId(1001)
            assertNull(result)
        }
    }

    @DisplayName("findAllのテスト")
    @Nested
    inner class TestFindAll {

        @BeforeEach
        fun setUp() {

            mockReository = mockk(relaxed = true)
            sut = SearchUser(mockReository!!)
        }


        @DisplayName("ユーザが存在している場合")
        @Test
        fun whenExistUser() {

            every {
                mockReository!!.findAll()
            } returns listOf(user1,  user2, user3)

            val result : List<SearchUser.UserResult> = sut!!.findAll()
            assertEquals(3, result.size)

            val actualUser1: SearchUser.UserResult = result[0]
            assertEquals(1001, actualUser1.userId)
            assertEquals("テスト姓1", actualUser1.lastName)
            assertEquals("テスト名1", actualUser1.firstName)
            assertEquals(LocalDate.of(1986,12,16), actualUser1.birthDay)

            val actualUser2: SearchUser.UserResult = result[1]
            assertEquals(1002, actualUser2.userId)
            assertEquals("テスト姓2", actualUser2.lastName)
            assertEquals("テスト名2", actualUser2.firstName)
            assertEquals(LocalDate.of(1987,1,17), actualUser2.birthDay)

            val actualUser3: SearchUser.UserResult = result[2]
            assertEquals(1003, actualUser3.userId)
            assertEquals("テスト姓3", actualUser3.lastName)
            assertEquals("テスト名3", actualUser3.firstName)
            assertEquals(LocalDate.of(1988,2,18), actualUser3.birthDay)
        }


        @DisplayName("ユーザが存在していない場合")
        @Test
        fun whenNotExistUser() {

            every {
                mockReository!!.findAll()
            } returns listOf()

            val result : List<SearchUser.UserResult> = sut!!.findAll()
            assertEquals(0, result.size)
        }
    }
}