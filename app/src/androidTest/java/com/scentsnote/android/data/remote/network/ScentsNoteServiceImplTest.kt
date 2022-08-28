package com.scentsnote.android.data.remote.network

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.scentsnote.android.data.vo.request.RequestLogin
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.HttpException

@RunWith(AndroidJUnit4::class)
class ScentsNoteServiceImplTest {

    private var api: ScentsNoteService = ScentsNoteServiceImpl.service
    private var token: String? = null

    @Before
    fun setToken() {
        if (token != null) return
        runBlocking {
            val loginInfo = RequestLogin("anyone@afume.com", "1234")
            token = "Bearer " + try {
                val response = api.postLoginInfo(loginInfo)
                response.data.token
            } catch (e: HttpException) {
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjoxNTIsImVtYWlsIjoiYW55b25lQGFmdW1lLmNvbSIsIm5pY2tuYW1lIjoi7J207Iug7J28IiwiZ2VuZGVyIjoxLCJiaXJ0aCI6MTk5NSwiZ3JhZGUiOjAsImFjY2Vzc1RpbWUiOiIyMDIxLTA2LTE2VDEzOjE4OjU5LjAwMFoiLCJjcmVhdGVkQXQiOiIyMDIxLTA2LTE2VDEyOjM5OjQyLjAwMFoiLCJ1cGRhdGVkQXQiOiIyMDIxLTA2LTE2VDEzOjE4OjU5LjAwMFoiLCJpYXQiOjE2MjM4NTQwODEsImV4cCI6MTcxMDI1NDA4MSwiaXNzIjoiYWZ1bWUtamFja3BvdCJ9.3__RsAKANn5sV-bx7iAntY9wflmJr4IcSw-45FkQTmM"
            }
        }
    }

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.scents.note", appContext.packageName)
    }

    @Test
    fun getValidateEmailTestSuccess() {
        val testEmail = "nevernevernoneUseEmail@afume.com"
        runBlocking {
            try {
                val response = api.getValidateEmail(testEmail)
                Assert.assertEquals(true, response.data)
            } catch (e: HttpException) {
                val response = e.response()!!
                val code = response.code()
                Assert.assertEquals(200, code)
            }
        }
    }

    @Test
    fun getValidateEmailTestConflicted() {
        val testEmail = "anyone@afume.com"
        runBlocking {
            try {
                val response = api.getValidateEmail(testEmail)
                Assert.assertEquals(false, response.data)
            } catch (e: HttpException) {
                val response = e.response()!!
                val code = response.code()
                Assert.assertEquals(409, code)
            }
        }
    }

    @Test
    fun getValidateNicknameTestSuccess() {
        val testNickname = "nevernevernoneUse"
        runBlocking {
            try {
                val response = api.getValidateNickname(testNickname)
                Assert.assertEquals(true, response.data)
            } catch (e: HttpException) {
                val response = e.response()!!
                val code = response.code()
                Assert.assertEquals(200, code)
            }
        }
    }

    @Test
    fun getValidateNicknameTestConflicted() {
        val testNickname = "쿼카맨"
        runBlocking {
            try {
                val response = api.getValidateNickname(testNickname)
                Assert.assertEquals(false, response.data)
            } catch (e: HttpException) {
                val response = e.response()!!
                val code = response.code()
                Assert.assertEquals(409, code)
            }
        }
    }

    @Test
    fun getSeriesTest() {
        runBlocking {
            try {
                val response = api.getSeries()
                val rows = response.data.rows
                Assert.assertEquals(true, rows.size > 0)
            } catch (e: HttpException) {
                val response = e.response()!!
                val code = response.code()
                Assert.assertEquals(200, code)
            }
        }
    }

    @Test
    fun getSurveyPerfumeTest() {
        runBlocking {
            try {
                val response = api.getSurveyPerfume(token!!)
                val rows = response.data.rows
                Assert.assertEquals(true, rows.size > 0)
            } catch (e: HttpException) {
                val response = e.response()!!
                val code = response.code()
                Assert.assertEquals(200, code)
            }
        }
    }

    @Test
    fun getKeywordTest() {
        runBlocking {
            try {
                val response = api.getKeyword()
                val rows = response.data.rows
                Assert.assertEquals(true, rows.size > 0)
            } catch (e: HttpException) {
                val response = e.response()!!
                val code = response.code()
                Assert.assertEquals(200, code)
            }
        }
    }

    @Test
    fun getLikedPerfumeTest() {
        val userIdx = 152
        runBlocking {
            try {
                val response = api.getLikedPerfume(token!!, userIdx)
                val rows = response.data.rows
                Assert.assertEquals(true, rows.size > 0)
            } catch (e: HttpException) {
                val response = e.response()!!
                val code = response.code()
                Assert.assertEquals(200, code)
            }
        }
    }

    @Test
    fun getMyPerfume() {
        runBlocking {
            try {
                val response = api.getMyPerfume(token!!)
                val rows = response.data
                Assert.assertEquals(true, rows.size > 0)
            } catch (e: HttpException) {
                val response = e.response()!!
                val code = response.code()
                Assert.assertEquals(200, code)
            }
        }
    }

    @Test
    fun getFillterSeriesTest() {
        runBlocking {
            try {
                val response = api.getFilterSeries()
                val rows = response.data.rows
                Assert.assertEquals(true, rows.size > 0)
            } catch (e: HttpException) {
                val response = e.response()!!
                val code = response.code()
                Assert.assertEquals(200, code)
            }
        }
    }

    @Test
    fun getFillterBrandTest() {
        runBlocking {
            try {
                val response = api.getFilterBrand()
                val rows = response.data
                Assert.assertEquals(true, rows.size > 0)
            } catch (e: HttpException) {
                val response = e.response()!!
                val code = response.code()
                Assert.assertEquals(200, code)
            }
        }
    }

    @Test
    fun getRecommendPerfumeListTest() {
        runBlocking {
            try {
                val response = api.getRecommendPerfumeList(token!!)
                val rows = response.data.rows
                Assert.assertEquals(true, rows.size > 0)
            } catch (e: HttpException) {
                val response = e.response()!!
                val code = response.code()
                Assert.assertEquals(200, code)
            }
        }
    }

    @Test
    fun getCommonPerfumeListTest() {
        runBlocking {
            try {
                val response = api.getCommonPerfumeList(token!!)
                val rows = response.data.rows
                Assert.assertEquals(true, rows.size > 0)
            } catch (e: HttpException) {
                val response = e.response()!!
                val code = response.code()
                Assert.assertEquals(200, code)
            }
        }
    }

    @Test
    fun getRecentPerfumeListTest() {
        runBlocking {
            try {
                val response = api.getRecentList(token!!)
                val rows = response.data.rows
                Assert.assertEquals(true, rows.size > 0)
            } catch (e: HttpException) {
                val response = e.response()!!
                val code = response.code()
                Assert.assertEquals(200, code)
            }
        }
    }

    @Test
    fun getNewPerfumeListTest() {
        runBlocking {
            try {
                val response = api.getNewPerfumeList()
                val rows = response.data.rows
                Assert.assertEquals(true, rows.size > 0)
            } catch (e: HttpException) {
                val response = e.response()!!
                val code = response.code()
                Assert.assertEquals(200, code)
            }
        }
    }

    @Test
    fun getReviewTest() {
        val reviewIdx = 73
        runBlocking {
            try {
                val response = api.getReview(reviewIdx)
                Assert.assertEquals(true, response.message.contains("성공"))
                Assert.assertEquals(reviewIdx, response.data.reviewIdx)
            } catch (e: HttpException) {
                val response = e.response()!!
                val code = response.code()
                Assert.assertEquals(200, code)
            }
        }
    }
}
