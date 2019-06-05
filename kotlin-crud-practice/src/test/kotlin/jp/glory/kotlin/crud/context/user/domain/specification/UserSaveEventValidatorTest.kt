package jp.glory.kotlin.crud.context.user.domain.specification

import io.mockk.every
import io.mockk.mockk
import jp.glory.kotlin.crud.context.base.domain.error.InvalidResult
import jp.glory.kotlin.crud.context.base.domain.error.ValidResult
import jp.glory.kotlin.crud.context.base.domain.error.ValidationErrors
import jp.glory.kotlin.crud.context.base.domain.error.ValidationResult
import jp.glory.kotlin.crud.context.user.domain.entity.User
import jp.glory.kotlin.crud.context.user.domain.event.UserSaveEvent
import jp.glory.kotlin.crud.context.user.domain.repository.UserRepository
import jp.glory.kotlin.crud.context.user.domain.value.BirthDay
import jp.glory.kotlin.crud.context.user.domain.value.NotRegisteredUserId
import jp.glory.kotlin.crud.context.user.domain.value.RegisteredUserId
import jp.glory.kotlin.crud.context.user.domain.value.UserName
import jp.glory.kotlin.crud.test.tool.ValidationAssert
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class UserSaveEventValidatorTest {

    private var sut: UserSaveEventValidator? = null
    private var mockUserRepository: UserRepository? = null
    private val existUser = User(
            userId = RegisteredUserId(1000),
            userName = UserName("テスト姓", "テスト名"),
            birthDay = BirthDay(LocalDate.of(1986, 12, 16))
    )

    @BeforeEach
    fun setUp() {

        mockUserRepository = mockk()

        sut = createSut()
    }

    @DisplayName("全てが正常に入力されている場合")
    @Nested
    inner class WhenAllValid {

        @DisplayName("ユーザのモデルが返る")
        @Test
        fun executeValidate() {

            every {

                mockUserRepository!!.findByUserId(any())
            } returns existUser

            val user = when (val actual: ValidationResult<UserSaveEvent> = sut!!.validate(RegisteredUserId(1000))) {
                is ValidResult -> actual.value
                is InvalidResult -> fail(actual.toString())
            }

            assertEquals(1000, (user.userId as RegisteredUserId).value)
            assertEquals("テスト姓", user.userName.lastName)
            assertEquals("テスト名", user.userName.firstName)
            assertEquals(LocalDate.of(1986, 12, 16), user.birthDay.value)
        }
    }

    @DisplayName("全てが最大文字数以内の場合")
    @Nested
    inner class WhenAllMaxLength {

        private val expectedLastName = "あ".repeat(30)
        private val expectedFirstName = "い".repeat(30)

        @DisplayName("ユーザのモデルが返る")
        @Test
        fun executeValidate() {

            every {

                mockUserRepository!!.findByUserId(any())
            } returns existUser

            sut = createSut(lastName = expectedLastName, firstName = expectedFirstName)

            val user = when (val actual: ValidationResult<UserSaveEvent> = sut!!.validate(RegisteredUserId(1000))) {
                is ValidResult -> actual.value
                is InvalidResult -> fail(actual.toString())
            }

            assertEquals(1000, (user.userId as RegisteredUserId).value)
            assertEquals(expectedLastName, user.userName.lastName)
            assertEquals(expectedFirstName, user.userName.firstName)
            assertEquals(LocalDate.of(1986, 12, 16), user.birthDay.value)
        }
    }

    @DisplayName("姓の不正入力")
    @Nested
    inner class InvalidLastName {

        private val itemName = "姓"

        @BeforeEach
        fun setUp() {
            every {

                mockUserRepository!!.findByUserId(any())
            } returns existUser
        }

        @DisplayName("未入力の場合")
        @Test
        fun whenEmpty() {

            sut = createSut(lastName = "")

            val errors = when (val actual: ValidationResult<UserSaveEvent> = sut!!.validate(RegisteredUserId(1000))) {
                is InvalidResult -> actual.errors
                is ValidResult -> fail("入力チェックエラー扱いになりませんでした。")
            }

            val expected = ValidationErrors()
            expected.addRequiredError(itemName)

            val assert = ValidationAssert(expected, errors)
            assert.assert()
        }

        @DisplayName("スペースのみの場合")
        @Test
        fun whenWhiteSpaceOnly() {

            sut = createSut(lastName = " ")

            val errors = when (val actual: ValidationResult<UserSaveEvent> = sut!!.validate(RegisteredUserId(1000))) {
                is InvalidResult -> actual.errors
                is ValidResult -> fail("入力チェックエラー扱いになりませんでした。")
            }

            val expected = ValidationErrors()
            expected.addRequiredError(itemName)

            val assert = ValidationAssert(expected, errors)
            assert.assert()
        }

        @DisplayName("最大文字数を超える場合")
        @Test
        fun whenMaxLengthOver() {

            sut = createSut(lastName = "あ".repeat(31))

            val errors = when (val actual: ValidationResult<UserSaveEvent> = sut!!.validate(RegisteredUserId(1000))) {
                is InvalidResult -> actual.errors
                is ValidResult -> fail("入力チェックエラー扱いになりませんでした。")
            }

            val expected = ValidationErrors()
            expected.addMaxLength(itemName, 30)

            val assert = ValidationAssert(expected, errors)
            assert.assert()
        }
    }

    @DisplayName("名の不正入力")
    @Nested
    inner class InvalidFirstName {

        private val itemName = "名"

        @BeforeEach
        fun setUp() {
            every {

                mockUserRepository!!.findByUserId(any())
            } returns existUser
        }

        @DisplayName("未入力の場合")
        @Test
        fun whenEmpty() {

            sut = createSut(firstName = "")

            val errors = when (val actual: ValidationResult<UserSaveEvent> = sut!!.validate(RegisteredUserId(1000))) {
                is InvalidResult -> actual.errors
                is ValidResult -> fail("入力チェックエラー扱いになりませんでした。")
            }

            val expected = ValidationErrors()
            expected.addRequiredError(itemName)

            val assert = ValidationAssert(expected, errors)
            assert.assert()
        }

        @DisplayName("スペースのみの場合")
        @Test
        fun whenWhiteSpaceOnly() {

            sut = createSut(firstName = " ")

            val errors = when (val actual: ValidationResult<UserSaveEvent> = sut!!.validate(RegisteredUserId(1000))) {
                is InvalidResult -> actual.errors
                is ValidResult -> fail("入力チェックエラー扱いになりませんでした。")
            }

            val expected = ValidationErrors()
            expected.addRequiredError(itemName)

            val assert = ValidationAssert(expected, errors)
            assert.assert()
        }

        @DisplayName("最大文字数を超える場合")
        @Test
        fun whenMaxLengthOver() {

            sut = createSut(firstName = "あ".repeat(31))

            val errors = when (val actual: ValidationResult<UserSaveEvent> = sut!!.validate(RegisteredUserId(1000))) {
                is InvalidResult -> actual.errors
                is ValidResult -> fail("入力チェックエラー扱いになりませんでした。")
            }

            val expected = ValidationErrors()
            expected.addMaxLength(itemName, 30)

            val assert = ValidationAssert(expected, errors)
            assert.assert()
        }
    }

    @DisplayName("誕生日の不正入力")
    @Nested
    inner class InvalidBirthDay {

        private val itemName = "誕生日"

        @BeforeEach
        fun setUp() {
            every {

                mockUserRepository!!.findByUserId(any())
            } returns existUser
        }

        @DisplayName("未入力の場合")
        @Test
        fun whenEmpty() {

            sut = createSut(birthDay = "")

            val errors = when (val actual: ValidationResult<UserSaveEvent> = sut!!.validate(RegisteredUserId(1000))) {
                is InvalidResult -> actual.errors
                is ValidResult -> fail("入力チェックエラー扱いになりませんでした。")
            }

            val expected = ValidationErrors()
            expected.addRequiredError(itemName)

            val assert = ValidationAssert(expected, errors)
            assert.assert()
        }

        @DisplayName("スペースのみの場合")
        @Test
        fun whenWhiteSpaceOnly() {

            sut = createSut(birthDay = " ")

            val errors = when (val actual: ValidationResult<UserSaveEvent> = sut!!.validate(RegisteredUserId(1000))) {
                is InvalidResult -> actual.errors
                is ValidResult -> fail("入力チェックエラー扱いになりませんでした。")
            }

            val expected = ValidationErrors()
            expected.addRequiredError(itemName)

            val assert = ValidationAssert(expected, errors)
            assert.assert()
        }

        @DisplayName("不正なフォーマット")
        @Nested
        inner class WhenInvalidFormat {

            @DisplayName("yyyy/mm/ddの場合")
            @Test
            fun whenInvalidFormat() {

                sut = createSut(birthDay = "1986/12/16")

                val errors = when (val actual: ValidationResult<UserSaveEvent> = sut!!.validate(RegisteredUserId(1000))) {
                    is InvalidResult -> actual.errors
                    is ValidResult -> fail("入力チェックエラー扱いになりませんでした。")
                }

                val expected = ValidationErrors()
                expected.addInvalidFormatDate(itemName)

                val assert = ValidationAssert(expected, errors)
                assert.assert()
            }

            @DisplayName("存在しない日付の場合")
            @Test
            fun whenInvalidValue() {

                sut = createSut(birthDay = "1987-02-29")

                val errors = when (val actual: ValidationResult<UserSaveEvent> = sut!!.validate(RegisteredUserId(1000))) {
                    is InvalidResult -> actual.errors
                    is ValidResult -> fail("入力チェックエラー扱いになりませんでした。")
                }

                val expected = ValidationErrors()
                expected.addInvalidFormatDate(itemName)

                val assert = ValidationAssert(expected, errors)
                assert.assert()
            }

            @DisplayName("0埋めされていない日付の場合")
            @Test
            fun whenNotZeroPadding() {

                sut = createSut(birthDay = "1987-2-1")

                val errors = when (val actual: ValidationResult<UserSaveEvent> = sut!!.validate(RegisteredUserId(1000))) {
                    is InvalidResult -> actual.errors
                    is ValidResult -> fail("入力チェックエラー扱いになりませんでした。")
                }

                val expected = ValidationErrors()
                expected.addInvalidFormatDate(itemName)

                val assert = ValidationAssert(expected, errors)
                assert.assert()
            }
        }
    }


    @DisplayName("ユーザが存在しない場合")
    @Nested
    inner class NotExistUser {

        @BeforeEach
        fun setUp() {
            every {

                mockUserRepository!!.findByUserId(any())
            } returns null
        }

        @DisplayName("登録済みユーザIDが渡された場合")
        @Test
        fun whenPassedRegisteredUserId() {

            val errors = when (val actual: ValidationResult<UserSaveEvent> = sut!!.validate(RegisteredUserId(1000))) {
                is InvalidResult -> actual.errors
                is ValidResult -> fail("入力チェックエラー扱いになりませんでした。")
            }

            val expected = ValidationErrors()
            expected.addNotExistUser()

            val assert = ValidationAssert(expected, errors)
            assert.assert()
        }

        @DisplayName("未登録ユーザIDが渡された場合")
        @Test
        fun whenPassedNotRegisteredUserId() {

            val user = when (val actual: ValidationResult<UserSaveEvent> = sut!!.validate(NotRegisteredUserId)) {
                is ValidResult -> actual.value
                is InvalidResult -> fail(actual.toString())
            }

            assertEquals(NotRegisteredUserId, user.userId)
            assertEquals("テスト姓", user.userName.lastName)
            assertEquals("テスト名", user.userName.firstName)
            assertEquals(LocalDate.of(1986, 12, 16), user.birthDay.value)
        }
    }

    private fun createSut(
            lastName: String = "テスト姓",
            firstName: String = "テスト名",
            birthDay: String = "1986-12-16"): UserSaveEventValidator {

        return UserSaveEventValidator(mockUserRepository!!, lastName, firstName, birthDay)
    }
}