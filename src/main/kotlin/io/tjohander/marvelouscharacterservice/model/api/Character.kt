package io.tjohander.marvelouscharacterservice.model.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.squareup.moshi.JsonClass
import org.springframework.data.annotation.Id
import java.util.*

@JsonClass(generateAdapter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Character(
    @Id
    val id: String,
    val name: String,
    val description: String,
    val resourceURI: String,
    val urls: List<URL>,
    val thumbnail: Image,
    val comics: ResourceList<Comic>,
    val stories: ResourceList<Story>,
    val events: ResourceList<Event>,
    val series: ResourceList<Series>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Event(
    val id: Int
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Series(
    val id: Int
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Story(
    val id: Int,
    val title: String,
    val description: String,
    val resourceURI: String,
    val type: String,
    val modified: Date,
    val thumbnail: Image,
    val comics: List<Comic>,
    val series: List<Series>,
    val characters: List<Character>,
    val creators: List<Creator>,
    val originalIssue: ComicSummary
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Creator(
    val id: Int
)
