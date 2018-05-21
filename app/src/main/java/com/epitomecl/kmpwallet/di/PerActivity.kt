package com.epitomecl.kmpwallet.di

import java.lang.annotation.RetentionPolicy
import javax.inject.Qualifier
import javax.inject.Scope

@Scope
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
annotation class PerActivity
