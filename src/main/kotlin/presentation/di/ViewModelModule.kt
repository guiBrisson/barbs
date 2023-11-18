package presentation.di

import org.koin.dsl.module
import presentation.screens.main.MainScreenModel
import presentation.screens.splash.SplashScreenModel

val viewModelModule = module {
    factory { MainScreenModel(get()) }
    factory { SplashScreenModel(get()) }
}