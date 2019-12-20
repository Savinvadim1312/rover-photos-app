package com.example.martian.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class NasaAPI


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CoroutineScropeIO
