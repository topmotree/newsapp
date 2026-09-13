package dev.martinsv.newsapp.core.presentation.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val IconSearchOff: ImageVector
    get() {
        if (_IconSearchOff != null) {
            return _IconSearchOff!!
        }
        _IconSearchOff = ImageVector.Builder(
            name = "IconSearchOff",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFFE3E3E3))) {
                moveTo(15.5f, 14f)
                horizontalLineToRelative(-0.79f)
                lineToRelative(-0.28f, -0.27f)
                curveTo(15.41f, 12.59f, 16f, 11.11f, 16f, 9.5f)
                curveTo(16f, 5.91f, 13.09f, 3f, 9.5f, 3f)
                curveTo(6.08f, 3f, 3.28f, 5.64f, 3.03f, 9f)
                horizontalLineToRelative(2.02f)
                curveTo(5.3f, 6.75f, 7.18f, 5f, 9.5f, 5f)
                curveTo(11.99f, 5f, 14f, 7.01f, 14f, 9.5f)
                reflectiveCurveTo(11.99f, 14f, 9.5f, 14f)
                curveToRelative(-0.17f, 0f, -0.33f, -0.03f, -0.5f, -0.05f)
                verticalLineToRelative(2.02f)
                curveTo(9.17f, 15.99f, 9.33f, 16f, 9.5f, 16f)
                curveToRelative(1.61f, 0f, 3.09f, -0.59f, 4.23f, -1.57f)
                lineTo(14f, 14.71f)
                verticalLineToRelative(0.79f)
                lineToRelative(5f, 4.99f)
                lineTo(20.49f, 19f)
                lineTo(15.5f, 14f)
                close()
            }
            path(fill = SolidColor(Color(0xFFE3E3E3))) {
                moveTo(6.47f, 10.82f)
                lineToRelative(-2.47f, 2.47f)
                lineToRelative(-2.47f, -2.47f)
                lineToRelative(-0.71f, 0.71f)
                lineToRelative(2.47f, 2.47f)
                lineToRelative(-2.47f, 2.47f)
                lineToRelative(0.71f, 0.71f)
                lineToRelative(2.47f, -2.47f)
                lineToRelative(2.47f, 2.47f)
                lineToRelative(0.71f, -0.71f)
                lineToRelative(-2.47f, -2.47f)
                lineToRelative(2.47f, -2.47f)
                close()
            }
        }.build()

        return _IconSearchOff!!
    }

@Suppress("ObjectPropertyName")
private var _IconSearchOff: ImageVector? = null