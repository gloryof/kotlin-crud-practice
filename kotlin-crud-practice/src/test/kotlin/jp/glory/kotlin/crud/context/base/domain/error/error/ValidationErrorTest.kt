package jp.glory.kotlin.crud.context.base.domain.error.error

import jp.glory.kotlin.crud.context.base.domain.error.ErrorInfo
import jp.glory.kotlin.crud.context.base.domain.error.ValidationError
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class ValidationErrorTest {

    private var sut: ValidationError? = null
    private val itemName = "テスト項目名"

    @Nested
    @DisplayName("パラメータが0の場合")
    inner class ParameterIsNone {

        @BeforeEach
        fun setUp() {

            sut = ValidationError(ErrorInfo.InvalidFormatDate, arrayOf())
        }

        @Test
        @DisplayName("createMessageのテスト")
        fun testCreateMessage() {

            assertEquals(
                    "日付形式が不正です。YYYY-MM-DD形式かつ存在する日付で入力してください。",
                    sut!!.createMessage())
        }
    }

    @Nested
    @DisplayName("パラメータが一つの場合")
    inner class ParameterIsSingle {

        @BeforeEach
        fun setUp() {

            sut = ValidationError(ErrorInfo.Required, arrayOf(itemName))
        }

        @Test
        @DisplayName("createMessageのテスト")
        fun testCreateMessage() {

            assertEquals("テスト項目名は必須です。", sut!!.createMessage())
        }
    }

    @Nested
    @DisplayName("パラメータが複数の場合")
    inner class ParameterIsMultiple {

        @BeforeEach
        fun setUp() {

            sut = ValidationError(ErrorInfo.MaxLengthOver, arrayOf(itemName, 20))
        }

        @Test
        @DisplayName("createMessageのテスト")
        fun testCreateMessage() {

            assertEquals("テスト項目名は20文字以内で入力してください。", sut!!.createMessage())
        }
    }
}