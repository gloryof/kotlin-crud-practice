package jp.glory.kotlin.crud.context.user.domain.event

import jp.glory.kotlin.crud.context.user.domain.value.BirthDay
import jp.glory.kotlin.crud.context.user.domain.value.NotRegisteredUserId
import jp.glory.kotlin.crud.context.user.domain.value.RegisteredUserId
import jp.glory.kotlin.crud.context.user.domain.value.UserName
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class UserSaveEventTest {

    var sut : UserSaveEvent? = null

    val name : UserName = UserName("テスト姓", "テスト名")
    val birthDay : BirthDay = BirthDay(LocalDate.of(1986, 12, 16))

    @DisplayName("登録済みユーザの場合")
    @Nested
    inner class WhenRegisteredUser {

        @BeforeEach
        fun setUp() {

            sut = UserSaveEvent(
                    userId = RegisteredUserId(1000),
                    userName = name,
                    birthDay = birthDay
            )
        }

        @DisplayName("getTypeでタイプ：更新が返ってくる")
        @Test
        fun testGetType() {

            assertEquals(UserSaveEvent.Type.Update, sut!!.getType())
        }
    }

    @DisplayName("未登録ユーザの場合")
    @Nested
    inner class WhenNotRegisteredUser {

        @BeforeEach
        fun setUp() {

            sut = UserSaveEvent(
                    userId = NotRegisteredUserId,
                    userName = name,
                    birthDay = birthDay
            )
        }

        @DisplayName("getTypeでタイプ：登録が返ってくる")
        @Test
        fun testGetType() {

            assertEquals(UserSaveEvent.Type.Register, sut!!.getType())
        }
    }
}