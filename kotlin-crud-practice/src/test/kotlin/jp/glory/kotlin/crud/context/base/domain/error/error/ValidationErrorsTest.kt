package jp.glory.kotlin.crud.context.base.domain.error.error

import jp.glory.kotlin.crud.context.base.domain.error.ErrorInfo
import jp.glory.kotlin.crud.context.base.domain.error.ValidationError
import jp.glory.kotlin.crud.context.base.domain.error.ValidationErrors
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class ValidationErrorsTest {
    var sut : ValidationErrors? = null

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

            val actualList:List<ValidationError> = sut!!.toList()

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

                val actualList:List<ValidationError> = sut!!.toList()

                assertFalse(actualList.isEmpty())
                assertEquals(1, actualList.size)

                val actualError: ValidationError = actualList.get(0)
                assertEquals(ErrorInfo.Required, actualError.errorInfo)
                assertArrayEquals(arrayOf<Any>("テスト項目"), actualError.messageParam)
            }
        }

        @DisplayName("addMaxLengthが実行された場合")
        @Nested
        inner class WhenAddMaxLength {


            @BeforeEach
            fun setUp() {

                sut!!.addMaxLength("テスト項目", 100)
            }

            @DisplayName("hasErrorはtrue")
            @Test
            fun testHasError() {

                assertTrue(sut!!.hasError())
            }

            @DisplayName("toListで1つの要素が返る")
            @Test
            fun testToList() {

                val actualList:List<ValidationError> = sut!!.toList()

                assertFalse(actualList.isEmpty())
                assertEquals(1, actualList.size)

                val actualError: ValidationError = actualList.get(0)
                assertEquals(ErrorInfo.MaxLengthOver, actualError.errorInfo)
                assertArrayEquals(arrayOf<Any>("テスト項目", 100), actualError.messageParam)
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

                val actualList:List<ValidationError> = sut!!.toList()

                assertFalse(actualList.isEmpty())
                assertEquals(1, actualList.size)

                val actualError: ValidationError = actualList.get(0)
                assertEquals(ErrorInfo.InvalidFormatDate, actualError.errorInfo)
                assertArrayEquals(arrayOf<Any>("テスト項目"), actualError.messageParam)
            }
        }
    }
}