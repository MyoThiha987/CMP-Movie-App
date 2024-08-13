import com.myothiha.compose.movie.domain.coroutine.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

/**
 * Created by Vincent on 11/27/19
 * Modified by ZMT
 */
class DefaultDispatcherProvider : DispatcherProvider {
    override fun main(): CoroutineDispatcher = Dispatchers.Main
    override fun io(): CoroutineDispatcher = Dispatchers.IO
    override fun default(): CoroutineDispatcher = Dispatchers.Default
    override fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}