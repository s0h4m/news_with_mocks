package cc.soham.news.di

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun providesContext(application: Application) = application.applicationContext
}