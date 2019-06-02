package jp.glory.kotlin.crud.context.user.domain.specification

import jp.glory.kotlin.crud.context.base.domain.error.InvalidResult
import jp.glory.kotlin.crud.context.base.domain.error.ValidResult
import jp.glory.kotlin.crud.context.base.domain.error.ValidationErrors
import jp.glory.kotlin.crud.context.base.domain.error.ValidationResult
import jp.glory.kotlin.crud.context.user.domain.entity.User
import jp.glory.kotlin.crud.context.user.domain.event.UserSaveEvent
import jp.glory.kotlin.crud.context.user.domain.value.RegisteredUserId
import jp.glory.kotlin.crud.test.tool.ValidationAssert
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class UserSaveEventValidatorTest {

    var sut: UserSaveEventValidator? = null

    @BeforeEach
    fun setUp() {

        val temp = UserSaveEventValidator()

        temp.lastName = "テスト姓"
        temp.firstName = "テスト名"
        temp.birthDay = "1986-12-16"

        sut = temp
    }

    @DisplayName("全てが正常に入力されている場合")
    @Nested
    inner class WhenAllValid {

        @DisplayName("ユーザのモデルが返る")
        @Test
        fun executeValidate() {

            val actual: ValidationResult<UserSaveEvent> = sut!!.validate(RegisteredUserId(1000))

            val user = when(actual) {
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

            sut!!.lastName = expectedLastName
            sut!!.firstName = expectedFirstName

            val actual: ValidationResult<UserSaveEvent> = sut!!.validate(RegisteredUserId(1000))

            val user = when(actual) {
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

        @DisplayName("未入力の場合")
        @Test
        fun whenEmpty() {

            sut!!.lastName = ""

            val actual: ValidationResult<UserSaveEvent> = sut!!.validate(RegisteredUserId(1000))

            val errors = when(actual) {
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

            sut!!.lastName = " "

            val actual: ValidationResult<UserSaveEvent> = sut!!.validate(RegisteredUserId(1000))

            val errors = when(actual) {
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

            sut!!.lastName = "あ".repeat(31)

            val actual: ValidationResult<UserSaveEvent> = sut!!.validate(RegisteredUserId(1000))

            val errors = when(actual) {
                is InvalidResult -> actual.errors
                is ValidResult -> fail("入力チェックエラー扱いになりませんでした。")
            }

            val expected = ValidationErrors()
            expected. addMaxLength(itemName, 30)

            val assert = ValidationAssert(expected, errors)
            assert.assert()
        }
    }

    @DisplayName("名の不正入力")
    @Nested
    inner class InvalidFirstName {

        private val itemName = "名"

        @DisplayName("未入力の場合")
        @Test
        fun whenEmpty() {

            sut!!.firstName = ""

            val actual: ValidationResult<UserSaveEvent> = sut!!.validate(RegisteredUserId(1000))

            val errors = when(actual) {
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

            sut!!.firstName = " "

            val actual: ValidationResult<UserSaveEvent> = sut!!.validate(RegisteredUserId(1000))

            val errors = when(actual) {
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

            sut!!.firstName = "あ".repeat(31)

            val actual: ValidationResult<UserSaveEvent> = sut!!.validate(RegisteredUserId(1000))

            val errors = when(actual) {
                is InvalidResult -> actual.errors
                is ValidResult -> fail("入力チェックエラー扱いになりませんでした。")
            }

            val expected = ValidationErrors()
            expected. addMaxLength(itemName, 30)

            val assert = ValidationAssert(expected, errors)
            assert.assert()
        }
    }

    @DisplayName("誕生日の不正入力")
    @Nested
    inner class InvalidBirthDay {

        private val itemName = "誕生日"

        @DisplayName("未入力の場合")
        @Test
        fun whenEmpty() {

            sut!!.birthDay = ""

            val actual: ValidationResult<UserSaveEvent> = sut!!.validate(RegisteredUserId(1000))

            val errors = when(actual) {
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

            sut!!.birthDay = " "

            val actual: ValidationResult<UserSaveEvent> = sut!!.validate(RegisteredUserId(1000))

            val errors = when(actual) {
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

                sut!!.birthDay = "1986/12/16"

                val actual: ValidationResult<UserSaveEvent> = sut!!.validate(RegisteredUserId(1000))

                val errors = when(actual) {
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

                sut!!.birthDay = "1987-02-29"

                val actual: ValidationResult<UserSaveEvent> = sut!!.validate(RegisteredUserId(1000))

                val errors = when(actual) {
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

                sut!!.birthDay = "1987-2-1"

                val actual: ValidationResult<UserSaveEvent> = sut!!.validate(RegisteredUserId(1000))

                val errors = when(actual) {
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
}