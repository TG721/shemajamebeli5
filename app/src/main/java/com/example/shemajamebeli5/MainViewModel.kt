package com.example.shemajamebeli5

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shemajamebeli5.model.Items
import com.example.shemajamebeli5.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response


class MainViewModel(private val repository: Repository): ViewModel() {
    private val _myState= MutableStateFlow<MyResponseState<Items>>(MyResponseState.Empty()) //mutable state flow
    val myState: StateFlow<MyResponseState<Items>> = _myState //immutable state flow

    fun getInfo(){
        viewModelScope.launch {
            _myState.emit(MyResponseState.Loading())
            val response: Response<Items> = repository.getPost()
            val body: Items? = response.body()
            if (response.isSuccessful && response.body() != null) {
                _myState.emit(MyResponseState.Success(body!!))
            }
            else {
//            _myState.value = MyResponseState.Error(response.errorBody().toString())
                _myState.emit(MyResponseState.Error(response.errorBody().toString()))
            }
        }
    }

    sealed class MyResponseState<T>{
        data class Success<T>(val items: T) : MyResponseState<T>()
        data class Error<T>(val message: String?) : MyResponseState<T>()
        class Loading<T>: MyResponseState<T>()
        class Empty<T> : MyResponseState<T>()

    }
}
