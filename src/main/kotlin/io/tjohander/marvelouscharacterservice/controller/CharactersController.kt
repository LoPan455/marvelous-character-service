package io.tjohander.marvelouscharacterservice.controller

import io.tjohander.marvelouscharacterservice.extension.toResponse
import io.tjohander.marvelouscharacterservice.model.CharacterResponse
import io.tjohander.marvelouscharacterservice.model.api.Character
import io.tjohander.marvelouscharacterservice.service.ICharacterService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("characters/v1")
class CharactersController(
    private val characterService: ICharacterService
) {

    @GetMapping
    @ResponseBody
    fun getCharacterByName(@RequestParam characterName: String): CharacterResponse {
        val results = mutableListOf<Character>()
        characterService.getCharacterStartsWith(characterName)?.let(results::add)
        return results.toResponse()
    }

}