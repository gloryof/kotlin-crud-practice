package jp.glory.kotlin.crud.context.user.domain.value

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.lang.AssertionError

internal class UserNameTest {
    var sut: UserName? = null

    @DisplayName("姓名が設定されている場合")
    @Nested
    inner class WhenBothIsSet {

        @BeforeEach
        fun setUp() {

            sut = UserName("テスト", "太郎")
        }

        @DisplayName("getFullNameはスペース区切りで返ってくる")
        @Test
        fun testGetFullName() {

            assertEquals("テスト 太郎", sut!!.getFullName())
        }
    }

    @DisplayName("例外ケース")
    @Nested
    inner class ExceptionCase {

        @DisplayName("姓が空文字の場合はエラー")
        @Test
        fun WhenLastNameIsEmpty() {

            org.junit.jupiter.api.assertThrows<AssertionError> { UserName("", "テスト")}
        }

        @DisplayName("名が空文字の場合はエラー")
        @Test
        fun WhenFirstsNameIsEmpty() {

            org.junit.jupiter.api.assertThrows<AssertionError> { UserName("テスト", "")}
        }
    }
}