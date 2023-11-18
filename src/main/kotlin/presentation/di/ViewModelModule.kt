package presentation.di

import org.koin.dsl.module
import presentation.screens.main.MainScreenModel
import presentation.screens.splash.SplashScreenModel
import presentation.screens.thread.ThreadScreenModel

val viewModelModule = module {
    factory { MainScreenModel(get()) }
    factory { SplashScreenModel(get()) }
    factory { ThreadScreenModel(get(), get()) }
}