package dev.martinsv.newsapp.news.presentation.utils

import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Instant
import kotlin.time.toJavaInstant

@Singleton
class InstantFormatter @Inject constructor() {

    fun format(
        instant: Instant,
        locale: Locale = Locale.getDefault(),
        zoneId: ZoneId = ZoneId.systemDefault(),
    ): String {
        val formatter = DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
            .withLocale(locale)
        return formatter.format(instant.toJavaInstant().atZone(zoneId))
    }
}