package app.extr

import android.app.Application
import app.extr.data.AppContainer
import app.extr.data.AppContainerImpl
import app.extr.ui.theme.helpers.ResourceProvider
import app.extr.ui.theme.helpers.ResourceProviderImpl

class ExTrApplication : Application() {
    companion object {
        lateinit var container: AppContainer
        lateinit var resourceProvider: ResourceProvider
    }

    override fun onCreate() {
        super.onCreate()

        container = AppContainerImpl(this)
        resourceProvider = ResourceProviderImpl(this)
    }
}