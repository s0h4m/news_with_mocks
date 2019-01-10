package cc.soham.news.di

import cc.soham.news.NewsApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class, AppModule::class, ActivityModule::class, MainViewModelModule::class, ViewModelFactoryModule::class))
interface AppComponent {
    fun inject(newsApplication: NewsApplication)
}