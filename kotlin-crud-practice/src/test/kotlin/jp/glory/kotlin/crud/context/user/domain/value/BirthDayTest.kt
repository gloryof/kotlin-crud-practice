package jp.glory.kotlin.crud.context.user.domain.value

import org.junit.jupiter.api.*
import java.time.LocalDate


internal class BirthDayTest {
    var sut: BirthDay? = null


    @DisplayName("月日が今日と同じ場合")
    @Nested
    inner class WhenSameDateToday {

        @BeforeEach

        fun setUp()  {

            sut = BirthDay(LocalDate.now().minusYears(20))
        }

        @DisplayName("calculateAgeで20が返ってくる")
        @Test
        fun testCalculateAge(){

            Assertions.assertEquals(20, sut!!.calculateAge().value)
        }
    }


    @DisplayName("月日が昨日の場合")
    @Nested
    inner class WhenSameDateYesterday {

        @BeforeEach

        fun setUp()  {

            sut = BirthDay(LocalDate.now().minusYears(20).minusDays(1))
        }

        @DisplayName("calculateAgeで20が返ってくる")
        @Test
        fun testCalculateAge(){

            Assertions.assertEquals(20, sut!!.calculateAge().value)
        }
    }


    @DisplayName("月日が明日の場合")
    @Nested
    inner class WhenSameDateTomorrow {

        @BeforeEach

        fun setUp()  {

            sut = BirthDay(LocalDate.now().minusYears(20).plusDays(1))
        }

        @DisplayName("calculateAgeで19が返ってくる")
        @Test
        fun testCalculateAge(){

            Assertions.assertEquals(19, sut!!.calculateAge().value)
        }
    }


    @DisplayName("年月日が今日の場合")
    @Nested
    inner class WhenToday {

        @BeforeEach

        fun setUp()  {

            sut = BirthDay(LocalDate.now())
        }

        @DisplayName("calculateAgeで0が返ってくる")
        @Test
        fun testCalculateAge(){

            Assertions.assertEquals(0, sut!!.calculateAge().value)
        }
    }


    @DisplayName("年月日が昨日の場合")
    @Nested
    inner class WhenYesterday {

        @BeforeEach

        fun setUp()  {

            sut = BirthDay(LocalDate.now().minusDays(1))
        }

        @DisplayName("calculateAgeで0が返ってくる")
        @Test
        fun testCalculateAge(){

            Assertions.assertEquals(0, sut!!.calculateAge().value)
        }
    }


    @DisplayName("年月日が明日の場合")
    @Nested
    inner class WhenTomorrow {

        @BeforeEach

        fun setUp()  {

            sut = BirthDay(LocalDate.now())
        }

        @DisplayName("calculateAgeで0が返ってくる")
        @Test
        fun testCalculateAge(){

            Assertions.assertEquals(0, sut!!.calculateAge().value)
        }
    }

    @DisplayName("年月日が1年以上未来の場合")
    @Nested
    inner class WhenFutureOver1Year {

        @BeforeEach

        fun setUp()  {

            sut = BirthDay(LocalDate.now().plusYears(1))
        }

        @DisplayName("calculateAgeで0が返ってくる")
        @Test
        fun testCalculateAge(){

            Assertions.assertEquals(0, sut!!.calculateAge().value)
        }
    }
}