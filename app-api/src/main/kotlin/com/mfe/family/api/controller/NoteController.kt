package com.mfe.family.api.controller

import com.mfe.family.api.dto.NoteDto
import com.mfe.family.api.mapper.toCommand
import com.mfe.family.api.mapper.toDto
import com.mfe.family.domain.entity.FamilyId
import com.mfe.family.domain.port.NoteQueryService
import com.mfe.family.domain.usecase.CreateNoteUseCase
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/notes")
class NoteController(
    private val createNoteUseCase: CreateNoteUseCase,
    private val noteQueryService: NoteQueryService
) {

    @PostMapping
    fun create(@Valid @RequestBody noteDto: NoteDto): ResponseEntity<NoteDto> {
        val note = createNoteUseCase.execute(noteDto.toCommand())
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(note.toDto())
    }

    @GetMapping
    fun list(@RequestParam familyId: String): List<NoteDto> =
        noteQueryService.list(FamilyId(familyId)).map { it.toDto() }
}
