package com.terdev.colosseum

import com.terdev.colosseum.jpa.dao.HeroRepository
import com.terdev.colosseum.jpa.entity.Hero
import com.terdev.colosseum.jpa.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class HeroCreator {

    @Autowired
    lateinit var userCreator: UserCreator

    @Autowired
    lateinit var heroRepository: HeroRepository

    fun createHero(nickname: String) : String {
        val output = StringBuilder()

        // Проверяем существование пользователя
        if (userCreator.checkUserExists(nickname)) {
            val user = userCreator.getUser(nickname)

            // Проверяем существование живого героя
            if (user != null && !checkAliveHeroExists(user)) {
                val hero = Hero()
                val heroGen = HeroBuilder.getHero(1, HeroClassType.values().random())

                hero.userLink = user
                hero.isAlive = heroGen.isAlive
                hero.name = heroGen.name
                hero.classType = heroGen.classType.toString()
                hero.level = heroGen.level
                hero.experience = heroGen.experience
                hero.strength = heroGen.strength
                hero.dexterity = heroGen.dexterity
                hero.intelligence = heroGen.intelligence
                hero.luck = heroGen.luck
                hero.healthMax = heroGen.healthMax
                hero.health = heroGen.health
                hero.defence = heroGen.defence
                hero.damageMin = heroGen.damageMin
                hero.damageMax = heroGen.damageMax
                hero.dodge = heroGen.dodge
                hero.criticalChance = heroGen.criticalChance
                hero.battlesWon = heroGen.battlesWon
                heroRepository.save(hero)

                output.appendLine("Герой создан")
            }
            else {
                output.appendLine("Герой уже существует")
            }
        }
        else {
            output.appendLine("HeroCreator: Ошибка! Герой не создан, т.к. пользователь не был найден")
        }

        return output.toString()
    }

    fun getHero(userLink: User) : Hero? {
        if (checkAliveHeroExists(userLink)) {
            return heroRepository.getByUserLinkAndIsAlive(userLink)
        }
        else {
            return null
        }
    }

    fun checkAliveHeroExists(userLink: User) : Boolean {
        return userLink.heroes?.stream()?.filter { it.isAlive?:false }?.count()!! > 0
        //return heroRepository.countByUserLinkAndIsAlive(userLink) > 0
    }
}