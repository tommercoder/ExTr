package app.extr.data.converters

import androidx.room.TypeConverter
import app.extr.data.types.UiMode

class UiModeConverter {
    @TypeConverter
    fun fromUiMode(mode: UiMode): String {
        return mode.name
    }

    @TypeConverter
    fun toUiMode(mode: String): UiMode {
        return UiMode.valueOf(mode)
    }
}