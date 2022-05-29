package io.tjohander.marvelouscharacterservice

import io.tjohander.marvelouscharacterservice.repository.CharacterRepository
import io.tjohander.marvelouscharacterservice.service.ICharacterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class ApplicationCommandLineRunner(
    val characterService: ICharacterService,
    @Autowired private val characterRepository: CharacterRepository
) : CommandLineRunner {


    override fun run(vararg args: String?): Unit {
        val characterResult = characterService.getCharacterStartsWith("Thor")
        characterResult?.let {
            println(characterResult)
        }
    }
}