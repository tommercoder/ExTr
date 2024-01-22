package app.extr

import android.app.Application
import app.extr.data.AppContainer
import app.extr.data.AppContainerImpl

class ExTrApplication : Application() {
    companion object {
        lateinit var container: AppContainer
    }

    override fun onCreate() {
        super.onCreate()

        container = AppContainerImpl(this)
    }
}