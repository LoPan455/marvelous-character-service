package io.tjohander.marvelouscharacterservice.service

import io.tjohander.marvelouscharacterservice.model.api.Character
import org.springframework.stereotype.Component

@Component
interface ICharacterService {
    fun getCharacterStartsWith(startsWithString: String): Character?

    fun getCharactersFromMarvelPaged(limit: Int, offset: Int): List<Character>?
}