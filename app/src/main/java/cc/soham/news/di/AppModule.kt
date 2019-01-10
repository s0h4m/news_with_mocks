package cc.soham.news.di

import android.app.Application
import cc.soham.news.NewsApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val application: NewsApplication) {
    @Provides
    @Singleton
    fun providesContext(application: Application) = application.applicationContext

    @Provides
    @Singleton
    fun providesApplication(): Application = application

    @Provides
    @Singleton
    fun providesNewsApplication(): NewsApplication = application
}