package jp.glory.kotlin.crud.context.base.domain.error.error

import jp.glory.kotlin.crud.context.base.domain.error.ValidationError
import jp.glory.kotlin.crud.context.base.domain.error.ValidationErrors
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class ValidationErrorsTest {
    var sut: ValidationErrors? = null

    @DisplayName("要素が空の場合")
    @Nested
    inner class WhenEmpty {

        @BeforeEach
        fun setUp() {

            sut = ValidationErrors()
        }

        @DisplayName("hasErrorはfalse")
        @Test
        fun testHasError() {

            assertFalse(sut!!.hasError())
        }

        @DisplayName("toListで空リストが返る")
        @Test
        fun testToList() {

            val actualList: List<ValidationError> = sut!!.toList()

            assertTrue(actualList.isEmpty())
        }

        @DisplayName("addRequiredが実行された場合")
        @Nested
        inner class WhenAddRequired {


            @BeforeEach
            fun setUp() {

                sut!!.addRequiredError("テスト項目")
            }

            @DisplayName("hasErrorはtrue")
            @Test
            fun testHasError() {

                assertTrue(sut!!.hasError())
            }

            @DisplayName("toListで1つの要素が返る")
            @Test
            fun testToList() {

                val actualList: List<ValidationError> = sut!!.toList()

                assertFalse(actualList.isEmpty())
                assertEquals(1, actualList.size)

                val actualError: ValidationError = actualList[0]
                assertEquals("テスト項目は必須です。", actualError.createMessage())
            }
        }

        @DisplayName("addMaxLengthが実行された場合")
        @Nested
        inner class WhenAddMaxLength {


            @BeforeEach
            fun setUp() {

                sut!!.addMaxLength("テスト項目", 1000)
            }

            @DisplayName("hasErrorはtrue")
            @Test
            fun testHasError() {

                assertTrue(sut!!.hasError())
            }

            @DisplayName("toListで1つの要素が返る")
            @Test
            fun testToList() {

                val actualList: List<ValidationError> = sut!!.toList()

                assertFalse(actualList.isEmpty())
                assertEquals(1, actualList.size)

                val actualError: ValidationError = actualList[0]
                assertEquals(
                        "テスト項目は1,000文字以内で入力してください。",
                        actualError.createMessage())
            }
        }

        @DisplayName("addInvalidFormatDateが実行された場合")
        @Nested
        inner class WhenInvalidFormatDate {


            @BeforeEach
            fun setUp() {

                sut!!.addInvalidFormatDate("テスト項目")
            }

            @DisplayName("hasErrorはtrue")
            @Test
            fun testHasError() {

                assertTrue(sut!!.hasError())
            }

            @DisplayName("toListで1つの要素が返る")
            @Test
            fun testToList() {

                val actualList: List<ValidationError> = sut!!.toList()

                assertFalse(actualList.isEmpty())
                assertEquals(1, actualList.size)

                val actualError: ValidationError = actualList[0]
                assertEquals(
                        "日付形式が不正です。YYYY-MM-DD形式かつ存在する日付で入力してください。",
                        actualError.createMessage())
            }
        }

        @DisplayName("addNotExistUserが実行された場合")
        @Nested
        inner class WhenNotExistUser {


            @BeforeEach
            fun setUp() {

                sut!!.addNotExistUser()
            }

            @DisplayName("hasErrorはtrue")
            @Test
            fun testHasError() {

                assertTrue(sut!!.hasError())
            }

            @DisplayName("toListで1つの要素が返る")
            @Test
            fun testToList() {

                val actualList: List<ValidationError> = sut!!.toList()

                assertFalse(actualList.isEmpty())
                assertEquals(1, actualList.size)

                val actualError: ValidationError = actualList[0]
                assertEquals(
                        "ユーザが存在しません。",
                        actualError.createMessage())
            }
        }
    }
}