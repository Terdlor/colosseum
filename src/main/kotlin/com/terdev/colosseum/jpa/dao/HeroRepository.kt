package com.terdev.colosseum.jpa.dao

import com.terdev.colosseum.jpa.entity.Hero
import com.terdev.colosseum.jpa.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface HeroRepository : JpaRepository<Hero, String> {

    fun getByUserLink(userLink: User) : Hero

    fun getByUserLinkAndIsAlive(userLink: User, isAlive: Boolean = true) : Hero

    fun countByUserLink(userLink: User) : Long

    fun countByUserLinkAndIsAlive(userLink: User, isAlive: Boolean = true) : Long
}