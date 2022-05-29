package io.tjohander.marvelouscharacterservice.repository

import io.tjohander.marvelouscharacterservice.model.api.Character
import org.springframework.data.domain.Example
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface CharacterRepository : MongoRepository<Character, Int> {

    fun findCharacterByNameIs(name: String): Character?
}