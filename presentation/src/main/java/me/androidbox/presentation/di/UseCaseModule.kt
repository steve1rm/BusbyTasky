package me.androidbox.presentation.di


import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import me.androidbox.domain.authentication.usecase.LoginUseCase
import me.androidbox.domain.authentication.usecase.RegisterUseCase
import me.androidbox.domain.authentication.usecase.imp.LoginUseCaseImp
import me.androidbox.domain.authentication.usecase.imp.RegisterUseCaseImp

@InstallIn(ViewModelComponent::class)
@Module
interface UseCaseModule {
    @Reusable
    @Binds
    fun bindsRegisterUseCaseImp(registerUseCaseImp: RegisterUseCaseImp): RegisterUseCase

    @Reusable
    @Binds
    fun bindsLoginUseCaseImp(loginUseCaseImp: LoginUseCaseImp): LoginUseCase
}
