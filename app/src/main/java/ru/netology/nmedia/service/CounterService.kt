package ru.netology.nmedia.service

import java.math.RoundingMode

object CounterService {

    fun modifyQuantityDisplay(count: Int): String {

        val cutThousands = (count / 1_000.00)
        val cutMillions = (count / 1_000_000.00)

        val roundedDownThousands =
            cutThousands.toBigDecimal().setScale(1, RoundingMode.FLOOR).toDouble()
        val roundedDownMillions =
            cutMillions.toBigDecimal().setScale(1, RoundingMode.FLOOR).toDouble()

        val calculateThousands =
            if (count < 10_000 && count % 1_000 in 0..99) "${cutThousands.toInt()}K"
            else "${roundedDownThousands}K"

        val calculateMillions =
            if (count < 10_000_000 && count % 1_000_000 in 0..99_999) "${cutMillions.toInt()}M"
            else "${roundedDownMillions}M"

        val textQuantity = when (count) {
            in 0..999 -> "$count"
            in 1_000..9_999 -> calculateThousands
            in 10_000..999_999 -> "${cutThousands.toInt()}K"
            in 1_000_000..9_999_999 -> calculateMillions
            in 10_000_000..Int.MAX_VALUE -> "${cutMillions.toInt()}M"
            else -> "?"
        }
        return textQuantity
    }
}