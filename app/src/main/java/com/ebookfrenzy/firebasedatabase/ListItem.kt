package com.ebookfrenzy.firebasedatabase
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ListItem (
        val nameTxt: String? = null,
        val speciesTxt: String? = null
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
                "nameTxt" to nameTxt,
                "speciesTxt" to speciesTxt
        )
    }
}