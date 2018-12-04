package alzaichsank.com.aplikasifootbalmatchschedule.mvp.match

import alzaichsank.com.aplikasifootbalmatchschedule.system.retrofit.BaseApiServices
import alzaichsank.com.aplikasifootbalmatchschedule.utils.ServerCallback
import alzaichsank.com.aplikasifootbalmatchschedule.view.match.`interface`.MatchView
import alzaichsank.com.aplikasifootbalmatchschedule.view.match.presenter.MatchPresenter
import com.nhaarman.mockito_kotlin.mock
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchPresenterTest {

    @Mock
    lateinit var presenter: MatchPresenter

    @Mock
    lateinit var serviceLink: BaseApiServices

    @Mock
    lateinit var serverCallbackTest : ServerCallback

    @Mock
    lateinit var matchViewTest : MatchView


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(matchViewTest)
    }

    @Test
    fun getPrevMatch() {
        val id = "4328"
        val mockedCall: Call<ResponseBody> = mock()
        val mockedResponseBody: ResponseBody = mock()
        val moeckedResponse = Response.success(mockedResponseBody)

        GlobalScope.launch {
            `when`(serviceLink.geteventsPasteague(id)).thenReturn(mockedCall)
            doAnswer {
                val callBack: Callback<ResponseBody> = it.getArgument(0)
                callBack.onResponse(mockedCall, moeckedResponse)
            }.`when`(mockedCall).enqueue(Mockito.any())

            presenter.getPrevMatch(id, serverCallbackTest)

            verify(presenter).getPrevMatch(id, serverCallbackTest)
            verify(matchViewTest).showLoading()
            verify(matchViewTest).hideLoading()
        }
    }

    @Test
    fun getNextMatch() {
        val id ="4328"
        val mockedCall: Call<ResponseBody> = mock()
        val mockedResponseBody: ResponseBody = mock()
        val moeckedResponse = Response.success(mockedResponseBody)
        GlobalScope.launch {
            `when`(serviceLink.geteeventsNextleague(id)).thenReturn(mockedCall)
            doAnswer {
                val callBack: Callback<ResponseBody> = it.getArgument(0)
                callBack.onResponse(mockedCall, moeckedResponse)
            }.`when`(mockedCall).enqueue(Mockito.any())

            presenter.getNextMatch(id, serverCallbackTest)

            verify(presenter).getNextMatch(id, serverCallbackTest)
        }
    }

    @Test
    fun getSpinnerData() {
        val mockedCall: Call<ResponseBody> = mock()
        val mockedResponseBody: ResponseBody = mock()
        val moeckedResponse = Response.success(mockedResponseBody)
        GlobalScope.launch {
            `when`(serviceLink.getallLeagues()).thenReturn(mockedCall)
            doAnswer {
                val callBack: Callback<ResponseBody> = it.getArgument(0)
                callBack.onResponse(mockedCall, moeckedResponse)
            }.`when`(mockedCall).enqueue(Mockito.any())

            presenter.getSpinnerData(serverCallbackTest)

            verify(presenter).getSpinnerData(serverCallbackTest)
        }
    }
}