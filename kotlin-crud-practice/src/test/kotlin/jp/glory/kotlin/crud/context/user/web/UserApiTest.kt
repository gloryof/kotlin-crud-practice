package jp.glory.kotlin.crud.context.user.web

import io.mockk.every
import io.mockk.mockk
import jp.glory.kotlin.crud.context.base.domain.error.ValidationErrors
import jp.glory.kotlin.crud.context.base.usecase.error.FailedResult
import jp.glory.kotlin.crud.context.base.usecase.error.SuccessResult
import jp.glory.kotlin.crud.context.user.domain.entity.User
import jp.glory.kotlin.crud.context.user.domain.value.BirthDay
import jp.glory.kotlin.crud.context.user.domain.value.RegisteredUserId
import jp.glory.kotlin.crud.context.user.domain.value.UserName
import jp.glory.kotlin.crud.context.user.usecase.SaveUser
import jp.glory.kotlin.crud.context.user.usecase.SearchUser
import jp.glory.kotlin.crud.context.user.web.request.SaveUserRequest
import jp.glory.kotlin.crud.context.user.web.response.UserResponse
import jp.glory.kotlin.crud.context.user.web.response.UsersResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.LocalDate

internal class UserApiTest {

    private var sut: UserApi? = null
    private var mockSearchUser: SearchUser? = null
    private var mockSaveUser: SaveUser? = null

    private val user1: SearchUser.UserResult = SearchUser.UserResult(
        User(
            userId = RegisteredUserId(1000),
            userName = UserName(
                lastName = "テスト姓1",
                firstName = "テスト名1"
            ),
            birthDay = BirthDay(LocalDate.of(1986, 12, 16))
        )
    )

    private val user2: SearchUser.UserResult = SearchUser.UserResult(
        User(
            userId = RegisteredUserId(1001),
            userName = UserName(
                lastName = "テスト姓2",
                firstName = "テスト名2"
            ),
            birthDay = BirthDay(LocalDate.of(1987, 1, 17))
        )
    )

    private val user3: SearchUser.UserResult = SearchUser.UserResult(
        User(
            userId = RegisteredUserId(1002),
            userName = UserName(
                lastName = "テスト姓3",
                firstName = "テスト名3"
            ),
            birthDay = BirthDay(LocalDate.of(1988, 2, 18))
        )
    )


    @BeforeEach
    fun setUp() {

        mockSearchUser = mockk()
        mockSaveUser = mockk()

        sut = UserApi(mockSearchUser!!, mockSaveUser!!)
    }

    @DisplayName("findAllのテスト")
    @Nested
    inner class TestFindAll {

        @BeforeEach
        fun setUp() {

            every { mockSearchUser!!.findAll() } returns listOf(user1, user2, user3)
        }

        @DisplayName("全てのユーザが返ってくる")
        @Test
        fun testResponse() {

            val response: UsersResponse = sut!!.findAll()
            val actual: List<UserResponse> = response.users

            assertEquals(3, actual.size)

            val actual1: UserResponse = actual[0]
            assertEquals(user1.userId, actual1.userId)
            assertEquals(user1.lastName, actual1.lastName)
            assertEquals(user1.firstName, actual1.firstName)
            assertEquals(user1.birthDay, actual1.birthDay)

            val actual2: UserResponse = actual[1]
            assertEquals(user2.userId, actual2.userId)
            assertEquals(user2.lastName, actual2.lastName)
            assertEquals(user2.firstName, actual2.firstName)
            assertEquals(user2.birthDay, actual2.birthDay)

            val actual3: UserResponse = actual[2]
            assertEquals(user3.userId, actual3.userId)
            assertEquals(user3.lastName, actual3.lastName)
            assertEquals(user3.firstName, actual3.firstName)
            assertEquals(user3.birthDay, actual3.birthDay)
        }
    }

    @DisplayName("registerのテスト")
    @Nested
    inner class TestRegister {

        @DisplayName("保存に成功した場合")
        @Test
        fun whenSaveIsSuccess() {

            every { mockSaveUser!!.register(any()) } returns SuccessResult(1000)

            val request = SaveUserRequest(
                lastName = "テスト姓",
                firstName = "テスト名",
                birthDay = "1986-12-16"
            )

            val actual: ResponseEntity<Any> = sut!!.register(request)

            assertEquals(HttpStatus.CREATED, actual.statusCode)
            assertEquals(1000L, actual.body)
        }

        @DisplayName("保存に失敗した場合")
        @Test
        fun whenSaveIsFailed() {

            val errors = ValidationErrors()
            errors.addRequiredError("テスト項目")

            every { mockSaveUser!!.register(any()) } returns FailedResult(errors)

            val request = SaveUserRequest(
                lastName = "",
                firstName = "テスト名",
                birthDay = "1986-12-16"
            )

            val actual: ResponseEntity<Any> = sut!!.register(request)

            assertEquals(HttpStatus.BAD_REQUEST, actual.statusCode)
            assertEquals(listOf("テスト項目は必須です。"), actual.body)
        }

    }

    @DisplayName("findByIdのテスト")
    @Nested
    inner class TestFindById {

        @BeforeEach
        fun setUp() {

            every { mockSearchUser!!.findByUserId(1001) } returns user2
            every { mockSearchUser!!.findByUserId(2000) } returns null
        }

        @DisplayName("全てのユーザ存在している場合")
        @Test
        fun whenUserExist() {

            val actual: ResponseEntity<UserResponse> = sut!!.findById(1001)

            assertEquals(HttpStatus.OK, actual.statusCode)

            val actualUser: UserResponse? = actual.body

            if (actualUser != null) {

                assertEquals(user2.userId, actualUser.userId)
                assertEquals(user2.lastName, actualUser.lastName)
                assertEquals(user2.firstName, actualUser.firstName)
                assertEquals(user2.birthDay, actualUser.birthDay)
            } else {

                fail("ユーザがnullです。")
            }
        }

        @DisplayName("全てのユーザ存在していない場合")
        @Test
        fun whenUserIsNotExist() {


            val actual: ResponseEntity<UserResponse> = sut!!.findById(2000)

            assertEquals(HttpStatus.NOT_FOUND, actual.statusCode)
        }
    }

    @DisplayName("updateのテスト")
    @Nested
    inner class TestUpdate {

        @DisplayName("保存に成功した場合")
        @Test
        fun whenSaveIsSuccess() {

            every { mockSaveUser!!.update(any()) } returns SuccessResult(1000)

            val request = SaveUserRequest(
                lastName = "テスト姓",
                firstName = "テスト名",
                birthDay = "1986-12-16"
            )

            val actual: ResponseEntity<Any> = sut!!.update(1000, request)

            assertEquals(HttpStatus.CREATED, actual.statusCode)
            assertEquals(1000L, actual.body)
        }

        @DisplayName("保存に失敗した場合")
        @Test
        fun whenSaveIsFailed() {

            val errors = ValidationErrors()
            errors.addRequiredError("テスト項目")

            every { mockSaveUser!!.register(any()) } returns FailedResult(errors)

            val request = SaveUserRequest(
                lastName = "",
                firstName = "テスト名",
                birthDay = "1986-12-16"
            )

            val actual: ResponseEntity<Any> = sut!!.register(request)

            assertEquals(HttpStatus.BAD_REQUEST, actual.statusCode)
            assertEquals(listOf("テスト項目は必須です。"), actual.body)
        }

    }
}