package ru.anlyashenko.fragmentapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val age: Int,
    val name: String,
) : Parcelable
