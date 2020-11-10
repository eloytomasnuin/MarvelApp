package com.ob.marvelapp.ui

import com.ob.domain.Hero
import com.ob.marvelapp.ui.model.UIHero

class UIMapper {

    fun convertUIHeroToDomain(uiHero: UIHero) {
        return with(uiHero) {
            Hero(
                id = id,
                name = name,
                description = description,
                thumbnail = thumbnail,
                comics = comics,
                stories = stories
            )
        }
    }

    fun convertHeroesToUIHeroes(heroes: List<Hero>): List<UIHero> {
        return heroes.map { domain ->
            convertHeroToUIHero(domain)
        }
    }

    fun convertHeroToUIHero(hero: Hero): UIHero {
        return with(hero) {
            UIHero(
                id = id,
                name = name,
                description = description,
                thumbnail = thumbnail,
                comics = comics,
                stories = stories
            )
        }
    }
}