package io.tjohander.marvelouscharacterservice.extension

import io.tjohander.marvelouscharacterservice.model.CharacterResponse
import io.tjohander.marvelouscharacterservice.model.api.Character

fun MutableList<Character>.toResponse() =
    CharacterResponse(
        this.size,
        results = this
    )