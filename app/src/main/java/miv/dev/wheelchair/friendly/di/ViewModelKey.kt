package miv.dev.wheelchair.friendly.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@Retention
annotation class ViewModelKey(val value: KClass<out ViewModel>)
