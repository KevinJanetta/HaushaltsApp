package com.example.lebensmittelverwaltung.ui.einkaufsliste

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lebensmittelverwaltung.data.reporsitories.Repository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewModel(repository) as T //Das Viewmodel wird zurückgegeben
    }
}