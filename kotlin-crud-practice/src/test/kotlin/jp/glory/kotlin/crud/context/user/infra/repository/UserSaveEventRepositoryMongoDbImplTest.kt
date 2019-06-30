package jp.glory.kotlin.crud.context.user.infra.repository

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
import jp.glory.kotlin.crud.externals.mongodb.user.collection.UsersCollection
import jp.glory.kotlin.crud.externals.mongodb.user.dao.UsersDao
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.*

internal class UserSaveEventRepositoryMongoDbImplTest {

    private var sut: UserSaveEventRepositoryMongoImpl? = null
    private var daoMock: UsersDao? = null

    @BeforeEach
    fun setUp() {

        if (daoMock != null) {
            clearMocks(daoMock!!)
        }

        daoMock = mockk()
        sut = UserSaveEventRepositoryMongoImpl(daoMock!!)
    }

    @DisplayName("saveのテスト")
    @Nested
    inner class TestSave {


        @DisplayName("新規登録の場合")
        @Test
        fun whenRegister() {

            every { daoMock!!.getNextUserId() } returns 1000

            val event = UserSaveEvent(
                userId = NotRegisteredUserId,
                userName = UserName(
                    lastName = "テスト姓",
                    firstName = "テスト名"
                ),
                birthDay = BirthDay(LocalDate.of(1986, 12, 16))
            )
            every { daoMock!!.save<UsersCollection>(any()) } returns UsersCollection(
                userId = 1,
                firstName = event.userName.firstName,
                lastName = event.userName.lastName,
                birthDay = event.birthDay.value
            )

            val result: RegisteredUserId = sut!!.save(event)

            assertEquals(1000, result.value)

            verify {
                daoMock!!.getNextUserId()
                daoMock!!.save<UsersCollection>(any())
            }

            confirmVerified(daoMock!!)
        }

        @DisplayName("更新の場合")
        @Test
        fun whenUpdate() {

            val event = UserSaveEvent(
                userId = RegisteredUserId(1000),
                userName = UserName(
                    lastName = "テスト姓",
                    firstName = "テスト名"
                ),
                birthDay = BirthDay(LocalDate.of(1986, 12, 16))
            )
            every { daoMock!!.findByUserId(1000) } returns Optional.of(
                UsersCollection(
                    id = "test123",
                    userId = 1000,
                    firstName = "before",
                    lastName = "before",
                    birthDay = LocalDate.now()
                )
            )
            every { daoMock!!.save<UsersCollection>(any()) } returns UsersCollection(
                id = "test123",
                userId = 1000,
                firstName = event.userName.firstName,
                lastName = event.userName.lastName,
                birthDay = event.birthDay.value
            )


            val result: RegisteredUserId = sut!!.save(event)

            assertEquals(1000, result.value)

            verify {
                daoMock!!.findByUserId(1000)
                daoMock!!.save<UsersCollection>(any())
            }

            confirmVerified(daoMock!!)
        }
    }
}

