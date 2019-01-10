package cc.soham.news.di

import android.app.Application
import cc.soham.news.NewsApplication
import cc.soham.news.networking.NewsRepository
import cc.soham.news.networking.RealNewsRepository
import cc.soham.news.storage.StorageProvider
import cc.soham.news.storage.TemporaryStorageProvider
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


    @Provides
    @Singleton
    fun providesNewsRepsitory(): NewsRepository = RealNewsRepository()

    @Provides
    @Singleton
    fun providesStorageProvider(): StorageProvider = TemporaryStorageProvider()
}