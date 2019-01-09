package cc.soham.news.di

import android.app.Application
import cc.soham.news.NewsApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton
import dagger.BindsInstance

@Component(modules = arrayOf(AndroidInjectionModule::class, AppModule::class, ActivityModule::class, MainViewModelModule::class, ViewModelFactoryModule::class))
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(newsApplication: NewsApplication)
}