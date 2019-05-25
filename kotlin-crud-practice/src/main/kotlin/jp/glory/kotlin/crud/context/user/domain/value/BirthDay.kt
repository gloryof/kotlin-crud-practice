package jp.glory.kotlin.crud.context.user.domain.value

import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * 誕生日.
 */
class BirthDay(val value: LocalDate) {

    /**
     * 年齢を計算する.
     *
     * @return 年齢
     */
    fun calculateAge(): Age {
        val result = ChronoUnit.YEARS.between(value, LocalDate.now()).toInt()

        if (result < 0) {

            return getZeroAge()
        }

        return Age(result)
    }
}