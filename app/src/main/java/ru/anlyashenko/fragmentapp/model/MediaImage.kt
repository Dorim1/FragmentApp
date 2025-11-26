package ru.anlyashenko.fragmentapp.model

import android.net.Uri

data class MediaImage (
    val id: Long,
    val uri: Uri,
    val name: String
)