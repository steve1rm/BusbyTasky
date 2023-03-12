package me.androidbox.presentation.di


import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import me.androidbox.domain.authentication.usecase.LoginUseCase
import me.androidbox.domain.authentication.usecase.RegisterUseCase
import me.androidbox.domain.authentication.usecase.RetrieveTokenUseCase
import me.androidbox.domain.authentication.usecase.SaveTokenUseCase
import me.androidbox.domain.authentication.usecase.imp.LoginUseCaseImp
import me.androidbox.domain.authentication.usecase.imp.RegisterUseCaseImp
import me.androidbox.domain.authentication.usecase.imp.RetrieveTokenUseCaseImp
import me.androidbox.domain.authentication.usecase.imp.SaveTokenUseCaseImp

@InstallIn(ViewModelComponent::class)
@Module
interface UseCaseModule {

    @Reusable
    @Binds
    fun bindsRetrieveTokenUseCaseImp(retrieveTokenUseCaseImp: RetrieveTokenUseCaseImp): RetrieveTokenUseCase

    @Reusable
    @Binds
    fun bindsSaveTokenUseCaseImp(saveTokenUseCaseImp: SaveTokenUseCaseImp): SaveTokenUseCase

    @Reusable
    @Binds
    fun bindsRegisterUseCaseImp(registerUseCaseImp: RegisterUseCaseImp): RegisterUseCase

    @Reusable
    @Binds
    fun bindsLoginUseCaseImp(loginUseCaseImp: LoginUseCaseImp): LoginUseCase
}
