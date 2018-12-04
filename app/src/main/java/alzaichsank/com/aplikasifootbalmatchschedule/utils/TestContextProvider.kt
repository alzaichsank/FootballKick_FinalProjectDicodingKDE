package alzaichsank.com.aplikasifootbalmatchschedule.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlin.coroutines.CoroutineContext


open class CoroutineContextProvider {
    open val main: CoroutineContext by lazy { Dispatchers.Main }
}

class TestContextProvider : CoroutineContextProvider() {
    override val main: CoroutineContext = Unconfined
}
