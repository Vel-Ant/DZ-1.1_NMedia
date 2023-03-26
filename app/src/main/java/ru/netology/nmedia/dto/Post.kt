package ru.netology.nmedia.dto

import java.math.RoundingMode

//  расчет текущего времени
val timestamp = System.currentTimeMillis()
val sdf = java.text.SimpleDateFormat("dd-MM-yyyy' в 'HH:mm:ss' '")
val sdfFormat = sdf.format(timestamp)

data class Post(

    val postId: Long,
    val author: String,
    val content: String,
//    val published: String,
    val published: String = sdfFormat,
    val iLiked: Boolean = false,
    val iShared: Boolean = false,
    val likes_count: Int = 1_099,
    val share_count: Int = 2_099_999,
    val viewings_count: Int = 9_999
)

object CounterService {

    fun ModifyQuantityDisplay(count: Int): String {

        val cutThousands = (count / 1_000.00)
        val cutMillions = (count / 1_000_000.00)
        val roundedDownThousands =
            cutThousands.toBigDecimal().setScale(1, RoundingMode.FLOOR).toDouble()
        val roundedDownMillions =
            cutMillions.toBigDecimal().setScale(1, RoundingMode.FLOOR).toDouble()

        val textQuantity = when (count) {
            in 0..999 -> "$count"
            in 1_000..1_099 -> "${cutThousands.toInt()}K"
            in 2_000..2_099 -> "${cutThousands.toInt()}K"
            in 3_000..3_099 -> "${cutThousands.toInt()}K"
            in 4_000..4_099 -> "${cutThousands.toInt()}K"
            in 5_000..5_099 -> "${cutThousands.toInt()}K"
            in 6_000..7_099 -> "${cutThousands.toInt()}K"
            in 8_000..8_099 -> "${cutThousands.toInt()}K"
            in 9_000..9_099 -> "${cutThousands.toInt()}K"
            in 1_100..9_999 -> "${roundedDownThousands}K"
            in 10_000..999_999 -> "${cutThousands.toInt()}K"
            in 1_000_000..1_099_999 -> "${cutMillions.toInt()}M"
            in 2_000_000..2_099_999 -> "${cutMillions.toInt()}M"
            in 3_000_000..3_099_999 -> "${cutMillions.toInt()}M"
            in 4_000_000..4_099_999 -> "${cutMillions.toInt()}M"
            in 5_000_000..5_099_999 -> "${cutMillions.toInt()}M"
            in 6_000_000..6_099_999 -> "${cutMillions.toInt()}M"
            in 7_000_000..7_099_999 -> "${cutMillions.toInt()}M"
            in 8_000_000..8_099_999 -> "${cutMillions.toInt()}M"
            in 9_000_000..9_099_999 -> "${cutMillions.toInt()}M"
            in 1_100_000..9_999_999 -> "${roundedDownMillions}M"
            in 10_000_000..Int.MAX_VALUE -> "${cutMillions.toInt()}M"
            else -> "?"
        }
        return textQuantity
    }
}
