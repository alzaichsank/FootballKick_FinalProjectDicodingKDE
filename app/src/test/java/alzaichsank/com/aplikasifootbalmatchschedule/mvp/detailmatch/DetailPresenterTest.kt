package alzaichsank.com.aplikasifootbalmatchschedule.mvp.detailmatch

import alzaichsank.com.aplikasifootbalmatchschedule.system.retrofit.BaseApiServices
import alzaichsank.com.aplikasifootbalmatchschedule.utils.ServerCallback
import alzaichsank.com.aplikasifootbalmatchschedule.view.detailmatch.presenter.DetailPresenter
import com.nhaarman.mockito_kotlin.mock
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailPresenterTest {

    @Mock
    lateinit var presenter: DetailPresenter

    @Mock
    lateinit var serviceLink: BaseApiServices

    @Mock
    lateinit var serverCallbackTest : ServerCallback

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailPresenter()
    }

    @Test
    fun geDetailTeam()  {
        val id ="133604"
        val mockedCall: Call<ResponseBody> = mock()
        val mockedResponseBody: ResponseBody = mock()
        val moeckedResponse = Response.success(mockedResponseBody)
        GlobalScope.launch {
            `when`(serviceLink.getLookupteam(id)).thenReturn(mockedCall)
            doAnswer {
                val callBack: Callback<ResponseBody> = it.getArgument(0)
                callBack.onResponse(mockedCall, moeckedResponse)
            }.`when`(mockedCall).enqueue(any())

            presenter.geDetailTeam(id, serverCallbackTest)

            verify(presenter).geDetailTeam(id, serverCallbackTest)
        verify(serverCallbackTest).onSuccess(moeckedResponse.body()!!.string())
        }
    }

}