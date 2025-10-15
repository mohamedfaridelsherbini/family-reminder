package com.mfe.family.infra.config

import com.mfe.family.domain.port.NoteQueryService
import com.mfe.family.domain.port.NoteRepository
import com.mfe.family.domain.port.PasswordHasher
import com.mfe.family.domain.port.UserRepository
import com.mfe.family.domain.service.NoteApplicationService
import com.mfe.family.domain.usecase.CreateNoteUseCase
import com.mfe.family.domain.usecase.SignupUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DomainConfig {

    @Bean
    fun createNoteUseCase(noteRepository: NoteRepository): CreateNoteUseCase =
        CreateNoteUseCase(noteRepository)

    @Bean
    fun signupUseCase(
        userRepository: UserRepository,
        passwordHasher: PasswordHasher
    ): SignupUseCase = SignupUseCase(userRepository, passwordHasher)

    @Bean
    fun noteQueryService(noteRepository: NoteRepository): NoteQueryService =
        NoteApplicationService(noteRepository)
}
