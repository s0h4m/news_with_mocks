package cc.soham.news

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.appcompat.app.AppCompatActivity

interface ViewModelHelper<T : AndroidViewModel> {
    fun provideViewModel(appCompatActivity: AppCompatActivity, modelClass: Class<T>): T
}

class ViewModelHelperImpl<T : AndroidViewModel> : ViewModelHelper<T> {
    override fun provideViewModel(appCompatActivity: AppCompatActivity, modelClass: Class<T>): T = ViewModelProviders.of(appCompatActivity).get(modelClass)
}

