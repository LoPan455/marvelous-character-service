package io.tjohander.marvelouscharacterservice.model

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import io.tjohander.marvelouscharacterservice.model.api.Character

data class CharacterResponse(
    val resultsCount: Int,
    val results: List<Character> = listOf()
)
