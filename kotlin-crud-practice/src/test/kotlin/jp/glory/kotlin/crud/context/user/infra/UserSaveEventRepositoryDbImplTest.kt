package jp.glory.kotlin.crud.context.user.infra

import io.mockk.clearMocks
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jp.glory.kotlin.crud.context.user.domain.event.UserSaveEvent
import jp.glory.kotlin.crud.context.user.domain.value.BirthDay
import jp.glory.kotlin.crud.context.user.domain.value.NotRegisteredUserId
import jp.glory.kotlin.crud.context.user.domain.value.RegisteredUserId
import jp.glory.kotlin.crud.context.user.domain.value.UserName
import jp.glory.kotlin.crud.externals.doma.user.dao.UsersDao
import jp.glory.kotlin.crud.externals.doma.user.holder.UsersTable
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.seasar.doma.jdbc.Result
import java.time.LocalDate

internal class UserSaveEventRepositoryDbImplTest {

    private var sut: UserSaveEventRepositoryDbImpl? = null
    private var daoMock: UsersDao? = null

    @BeforeEach
    fun setUp() {

        if (daoMock != null) {
            clearMocks(daoMock!!)
        }

        daoMock = mockk()
        sut = UserSaveEventRepositoryDbImpl(daoMock!!)
    }

    @DisplayName("saveのテスト")
    @Nested
    inner class TestSave {

        @DisplayName("新規登録の場合")
        @Test
        fun whenRegister() {

            every { daoMock!!.getNextUserId() } returns 1000
            every { daoMock!!.insert(any()) } returns Result<UsersTable>(1, null)

            val event = UserSaveEvent(
                userId = NotRegisteredUserId,
                userName = UserName(
                    lastName = "テスト姓",
                    firstName = "テスト名"
                ),
                birthDay = BirthDay(LocalDate.of(1986, 12, 16))
            )

            val result: RegisteredUserId = sut!!.save(event)

            assertEquals(1000, result.value)

            verify {
                daoMock!!.getNextUserId()
                daoMock!!.insert(any())
            }

            confirmVerified(daoMock!!)
        }

        @DisplayName("更新の場合")
        @Test
        fun whenUpdate() {

            every { daoMock!!.update(any()) } returns Result<UsersTable>(1, null)

            val event = UserSaveEvent(
                userId = RegisteredUserId(1000),
                userName = UserName(
                    lastName = "テスト姓",
                    firstName = "テスト名"
                ),
                birthDay = BirthDay(LocalDate.of(1986, 12, 16))
            )

            val result: RegisteredUserId = sut!!.save(event)

            assertEquals(1000, result.value)

            verify {
                daoMock!!.update(any())
            }

            confirmVerified(daoMock!!)
        }
    }
}

