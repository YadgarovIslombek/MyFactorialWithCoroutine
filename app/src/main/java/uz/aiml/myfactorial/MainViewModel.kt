package uz.aiml.myfactorial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger
import kotlin.concurrent.thread
import kotlin.coroutines.suspendCoroutine

class MainViewModel : ViewModel() {
    val scope = CoroutineScope(Dispatchers.Main + CoroutineName("myScope"))

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state


    fun calculate(value: String) {
        _state.value = Progress()


        if (value.isBlank()) {

            _state.value = Error()
            return
        }

        scope.launch {
            val number = value.toLong()
            val result = withContext(Dispatchers.Default) {
               calculateFactorial(number)
            }
            _state.value = Factorial(value = result)

        }

    }

    private fun calculateFactorial(value: Long): String {


        var result = BigInteger.ONE
        for (i in 1..value) {
            result = result.multiply(BigInteger.valueOf(i))
        }
        return result.toString()


    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

}