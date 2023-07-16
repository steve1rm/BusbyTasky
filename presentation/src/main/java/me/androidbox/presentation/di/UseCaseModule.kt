package me.androidbox.presentation.di

import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import me.androidbox.domain.authentication.usecase.*
import me.androidbox.domain.authentication.usecase.imp.*
import me.androidbox.domain.login.usecase.ValidateEmailUseCase
import me.androidbox.domain.login.usecase.ValidatePasswordUseCase
import me.androidbox.domain.login.usecase.imp.ValidateEmailUseCaseImp
import me.androidbox.domain.login.usecase.imp.ValidatePasswordUseCaseImp

@InstallIn(ViewModelComponent::class)
@Module
interface UseCaseModule {

    @Reusable
    @Binds
    fun bindsRetrieveTokenUseCaseImp(retrieveTokenUseCaseImp: RetrieveCurrentLoginUserUseCaseImp): RetrieveCurrentLoginUserUseCase

    @Reusable
    @Binds
    fun bindsSaveCurrentUserUseCaseImp(saveCurrentUserUseCaseImp: SaveCurrentUserUseCaseImp): SaveCurrentUserUseCase

    @Reusable
    @Binds
    fun bindsDeleteCurrentUserUseCaseImp(deleteCurrentUserUseCaseImp: DeleteCurrentUserUseCaseImp): DeleteCurrentUserUseCase

    @Reusable
    @Binds
    fun bindsRegisterUseCaseImp(registerUseCaseImp: RegisterUseCaseImp): RegisterUseCase

    @Reusable
    @Binds
    fun bindsLoginUseCaseImp(loginUseCaseImp: LoginUseCaseImp): LoginUseCase

    @Reusable
    @Binds
    fun bindsAuthenticationUserUseCaseImp(authenticateUserUseCaseImp: AuthenticateUserUseCaseImp): AuthenticateUserUseCase

    @Reusable
    @Binds
    fun bindsLogoutUseCaseImp(logoutUseCaseImp: LogoutUseCaseImp): LogoutUseCase

    @Reusable
    @Binds
    fun bindsValidateEmailUseCaseImp(validateEmailUseCaseImp: ValidateEmailUseCaseImp): ValidateEmailUseCase

    @Reusable
    @Binds
    fun bindsValidatePasswordUseCaseImp(validatePasswordUseCaseImp: ValidatePasswordUseCaseImp): ValidatePasswordUseCase
}
