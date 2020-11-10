package com.ob.domain

import org.junit.Before
import org.junit.Test

class HeroEntityTest {

    private lateinit var hero: Hero

    @Before
    fun setUp() {
        hero = Hero(
            id = 1234,
            name = "Thor",
            description = "Son of odin",
            thumbnail = "http://simplethumbnailurl.jpg",
            comics = listOf(),
            stories = listOf()
        )
    }

    @Test
    fun testDefaultValues() {
        assert(hero.id == 1234)
        assert(hero.name == "Thor")
        assert(hero.description == "Son of odin")
        assert(hero.thumbnail == "http://simplethumbnailurl.jpg")
        assert(hero.comics.count() == 0)
        assert(hero.stories.count() == 0)
    }

}