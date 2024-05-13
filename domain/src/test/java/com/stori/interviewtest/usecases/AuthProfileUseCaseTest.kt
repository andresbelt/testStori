package com.stori.interviewtest.usecases

import com.stori.interviewtest.commons.Either
import com.stori.interviewtest.commons.Either.Left
import com.stori.interviewtest.commons.Either.Right
import com.stori.interviewtest.data.Response.Success
import com.stori.interviewtest.data.UserStori
import com.stori.interviewtest.data.error.GenericUserErrorContainer
import com.stori.interviewtest.data.error.UserError.Type.EXCEPTION
import com.stori.interviewtest.data.repository.StoriRepository
import com.stori.interviewtest.domain.usecase.AuthProfileUseCase
import com.stori.interviewtest.domain.usecase.AuthProfileUseCase.ParamsSignIn
import com.stori.interviewtest.domain.usecase.AuthProfileUseCase.ParamsSignUp
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AuthProfileUseCaseTest {
    @MockK
    private lateinit var repository: StoriRepository

    // Use Case
    private lateinit var getListUseCase: AuthProfileUseCase

    @After
    fun tearDown() = clearAllMocks()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        getListUseCase = AuthProfileUseCase(repository)
    }

    @After
    fun verifyAll() {
        confirmVerified(repository)
    }

    @Test
    fun testSignIn() = runTest {
        val params = ParamsSignIn("", "")
        val expected = true

        coEvery { repository.signInWithEmailAndPassword(params.email,params.password) } returns Either.Right(expected)

        // When
        val result = getListUseCase.signIn(params = params)

        // Then
        Assert.assertTrue(result is Either.Right)
        coVerifySequence {
            repository.signInWithEmailAndPassword(params.email,params.password)
        }
    }

    @Test
    fun testSignInWithError() = runTest {
        val params = ParamsSignIn("", "")
        val expectedUserErrorContainer = GenericUserErrorContainer(EXCEPTION, "Error")

        coEvery { repository.signInWithEmailAndPassword(params.email,params.password) } returns  Either.Left(expectedUserErrorContainer)

        // When
        val result = getListUseCase.signIn(params = params)

        // Then
        coVerifySequence {
            repository.signInWithEmailAndPassword(params.email,params.password)
        }
        Assert.assertTrue(result is Either.Left)
        Assert.assertEquals(Either.Left(expectedUserErrorContainer).error, (result as Either.Left).error)
    }

    @Test
    fun testSignUp() = runTest {
        val params = ParamsSignUp(UserStori())

        coEvery { repository.saveInfoUser(params.user)  } returns flow {
            emit(Right((true)))
        }

        // When
        val result = getListUseCase.signUp(params = params).take(1)

        // Then
        Assert.assertTrue(result.last() is Right)
        Assert.assertEquals((result.last() as Right<*>).value, true)

        coVerifySequence {
            repository.saveInfoUser(params.user)
        }
    }

    @Test
    fun testSignUpWithError() = runTest {
        val params = ParamsSignUp(UserStori())
        val expected = GenericUserErrorContainer(EXCEPTION, "")

        coEvery { repository.saveInfoUser(params.user)  } returns flow {
            emit(Left(expected))
        }

        // When
        val result = getListUseCase.signUp(params = params).take(1)

        // Then
        Assert.assertTrue(result.last() is Left)
        Assert.assertEquals((result.last() as Left<*>).error, expected)

        coVerifySequence {
            repository.saveInfoUser(params.user)
        }
    }

}
