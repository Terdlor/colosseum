package com.terdev.colosseum.jpa.dao

import com.terdev.colosseum.jpa.entity.Hero
import com.terdev.colosseum.jpa.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface HeroRepository : JpaRepository<Hero, String> {

}