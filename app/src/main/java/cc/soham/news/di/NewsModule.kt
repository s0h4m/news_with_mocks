package cc.soham.news.di

import cc.soham.news.MainActivity
import cc.soham.news.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector()
    abstract fun bindMainActivity(): MainActivity
}