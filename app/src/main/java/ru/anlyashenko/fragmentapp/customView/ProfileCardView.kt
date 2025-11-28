package ru.anlyashenko.fragmentapp.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import coil.Coil
import ru.anlyashenko.fragmentapp.databinding.ViewProfileCardBinding

class ProfileCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ViewProfileCardBinding

    init {
        val inflater = LayoutInflater.from(context)
        // раздуть xml и прикрепить его к этому view -> (this)
        binding = ViewProfileCardBinding.inflate(inflater, this, true)
    }

    fun setProfileName(name : String) {
        binding.tvName.text = name
    }


    fun setProfileImageUrl(url: String) {
        // тут можно загружать аватар через coil/glide
    }

}