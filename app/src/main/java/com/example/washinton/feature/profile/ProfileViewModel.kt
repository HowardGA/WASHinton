package com.example.washinton.feature.profile

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.washinton.feature.api.ProductRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor( private val repository: ProductRepository) : ViewModel() {
    val _profile = MutableStateFlow<Profile?>(null)
    val profile: StateFlow<Profile?> = _profile

    val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

   suspend fun getProfile(userID: String): Result<Profile> {
        return repository.getProfile(userID)
            .onSuccess { details ->
                _profile.value = details
            }
            .onFailure { error ->
                _errorMessage.value = error.message
            }
    }

}
