package io.tjohander.marvelouscharacterservice.repository

import io.tjohander.marvelouscharacterservice.model.api.Character
import org.springframework.data.mongodb.repository.MongoRepository

interface CharacterRepository : MongoRepository<Character, Int> {
}