package ir.sajjad.bleconnector.di

import AndroidBluetoothController
import android.app.Application
import android.content.Context
import ir.sajjad.bleconnector.domain.BluetoothController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideBluetoothController(@ApplicationContext context: Context): BluetoothController {
        return AndroidBluetoothController(context)
    }
    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext
}