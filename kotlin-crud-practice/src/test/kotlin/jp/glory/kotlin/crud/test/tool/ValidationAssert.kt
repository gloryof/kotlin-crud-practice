package jp.glory.kotlin.crud.test.tool

import jp.glory.kotlin.crud.context.base.domain.error.ValidationError
import jp.glory.kotlin.crud.context.base.domain.error.ValidationErrors
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals

/**
 * 入力チェックエラーの検証を行う.
 *
 * @param expected 入力チェックエラーの期待値
 * @param actual 入力チェックの実測値
 */
class ValidationAssert(private val expected: ValidationErrors, private val actual: ValidationErrors) {

    private val expectedList: List<ValidationError> = expected.toList()

    private val actualList: List<ValidationError> = actual.toList()

    fun assert() {

        assertSize()

        for (i in expectedList.indices) {

            assertDetail(i, expectedList[i], actualList[i])
        }
    }

    private fun assertSize() {

        assertEquals(expectedList.size, actualList.size)
    }

    private fun assertDetail(index:Int, expectedDetail: ValidationError, actualDetail: ValidationError) {

        val message = """インデックス${index}の要素"""

        assertEquals(expectedDetail.errorInfo, actualDetail.errorInfo, message)
        assertArrayEquals(expectedDetail.messageParam, actualDetail.messageParam, message)
    }
}