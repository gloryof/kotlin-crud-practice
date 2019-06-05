package jp.glory.kotlin.crud.context.user.usecase

import io.mockk.every
import io.mockk.mockk
import jp.glory.kotlin.crud.context.base.usecase.error.FailedResult
import jp.glory.kotlin.crud.context.base.usecase.error.SuccessResult
import jp.glory.kotlin.crud.context.base.usecase.error.UsecaseResult
import jp.glory.kotlin.crud.context.user.domain.entity.User
import jp.glory.kotlin.crud.context.user.domain.repository.UserRepository
import jp.glory.kotlin.crud.context.user.domain.repository.UserSaveEventRepository
import jp.glory.kotlin.crud.context.user.domain.value.BirthDay
import jp.glory.kotlin.crud.context.user.domain.value.RegisteredUserId
import jp.glory.kotlin.crud.context.user.domain.value.UserName
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import java.time.LocalDate

internal class SaveUserTest {

    private var sut: SaveUser? = null

    private var mockSearchRepository: UserRepository? = null
    private var mockSaveRepository: UserSaveEventRepository? = null

    private val existUser = User(
            userId = RegisteredUserId(1000),
            userName = UserName("テスト姓", "テスト名"),
            birthDay = BirthDay(LocalDate.of(1986, 12, 16)))

    @BeforeEach
    fun setUp() {

        mockSearchRepository = mockk()
        mockSaveRepository = mockk()
    }

    @DisplayName("registerのテスト")
    @Nested
    inner class TestRegister {

        @BeforeEach
        fun setUp() {

            sut = SaveUser(mockSearchRepository!!, mockSaveRepository!!)


            every {

                mockSearchRepository!!.findByUserId(any())
            } returns null

            every {
                mockSaveRepository!!.save(any())
            } returns RegisteredUserId(1000)
        }

        @DisplayName("入力値がすべて正常な場合")
        @Test
        fun whenInputIsValid() {

            val param = SaveUser.RegisterParam(
                    lastName = "テスト姓",
                    firstName = "テスト名",
                    birthDay = "1986-12-16"
            )

            val actual: Long = when (val result: UsecaseResult<Long> = sut!!.register(param)) {
                is SuccessResult<Long> -> result.value
                else -> fail("実行中にエラーが発生しました。")
            }

            assertEquals(1000, actual)
        }

        @DisplayName("入力値に不正がある場合")
        @Test
        fun whenInputIsInvalid() {

            val param = SaveUser.RegisterParam(
                    lastName = "",
                    firstName = "テスト名",
                    birthDay = "1986-12-16"
            )

            val actual: List<String> = when (val result: UsecaseResult<Long> = sut!!.register(param)) {
                is FailedResult<Long> -> result.messages
                else -> fail("実行中にエラーが発生しました。")
            }

            assertTrue(actual.isNotEmpty())
        }
    }

    @DisplayName("updateのテスト")
    @Nested
    inner class TestUpdate {

        @BeforeEach
        fun setUp() {

            sut = SaveUser(mockSearchRepository!!, mockSaveRepository!!)


            every {

                mockSearchRepository!!.findByUserId(any())
            } returns existUser

            every {
                mockSaveRepository!!.save(any())
            } returns RegisteredUserId(1000)
        }

        @DisplayName("入力値がすべて正常な場合")
        @Test
        fun whenInputIsValid() {

            val param = SaveUser.UpdateParam(
                    userId = 1000,
                    lastName = "テスト姓",
                    firstName = "テスト名",
                    birthDay = "1986-12-16"
            )

            val actual: Long = when (val result: UsecaseResult<Long> = sut!!.update(param)) {
                is SuccessResult<Long> -> result.value
                else -> fail("実行中にエラーが発生しました。")
            }

            assertEquals(1000, actual)
        }

        @DisplayName("入力値に不正がある場合")
        @Test
        fun whenInputIsInvalid() {

            val param = SaveUser.UpdateParam(
                    userId = 1000,
                    lastName = "",
                    firstName = "テスト名",
                    birthDay = "1986-12-16"
            )

            val actual: List<String> = when (val result: UsecaseResult<Long> = sut!!.update(param)) {
                is FailedResult<Long> -> result.messages
                else -> fail("実行中にエラーが発生しました。")
            }

            assertTrue(actual.isNotEmpty())
        }
    }
}