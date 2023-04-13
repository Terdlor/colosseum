package com.terdev.colosseum

import kotlin.random.Random

class Randomizer {
    // Бросок кубика (Стандартные кубики: 4, 6, 8, 10, 12, 20)
    fun getDiceRoll(numberOfSides: Int): Int {
        return Random.nextInt(1, numberOfSides + 1)
    }

    // Задать вероятность события (от 1 до 100), функция вернет, попали мы в вероятность или нет
    fun getChanceRoll(requiredPercentage: Int): Boolean {
        val rollResult = Random.nextInt(1, 101)
        return rollResult <= requiredPercentage
    }

    // Вернет случайный процент от 1 до 100
    fun getPercentage(): Int {
        return Random.nextInt(1, 101)
    }

    // Вернет случайное значение из заданного диапазона (включая оба значения)
    fun getRandomFromRange(from: Int, to: Int): Int {
        return Random.nextInt(from, to + 1)
    }
}