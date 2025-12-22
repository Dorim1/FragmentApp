package ru.anlyashenko.fragmentapp.dataStore.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.anlyashenko.fragmentapp.R
import ru.anlyashenko.fragmentapp.databinding.FragmentSettingsDataStoreBinding
import ru.anlyashenko.fragmentapp.dataStore.data.model.UserRole
import ru.anlyashenko.fragmentapp.dataStore.ui.viewModel.GameCharacterViewModel
import ru.anlyashenko.fragmentapp.dataStore.ui.viewModel.GameCharacterViewModelFactory
import ru.anlyashenko.fragmentapp.dataStore.ui.viewModel.SettingsDataStoreViewModel
import ru.anlyashenko.fragmentapp.dataStore.ui.viewModel.SettingsViewModelFactory

class SettingsDataStoreFragment : Fragment() {

    private var _binding: FragmentSettingsDataStoreBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for FragmentSettingsDataStoreBinding must not be null")

    private val viewModel: SettingsDataStoreViewModel by viewModels {
        SettingsViewModelFactory(requireContext())
    }

    private val viewModelGameCharacter: GameCharacterViewModel by viewModels {
        GameCharacterViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsDataStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelGameCharacter.character.observe(viewLifecycleOwner) { character ->
            binding.tvNickname.text = character.nickname
            binding.tvLevel.text = "level: ${character.level}"
            binding.tvHp.text = "HP: ${character.stats.hp}"
            binding.tvStamina.text = "Stamina: ${character.stats.stamina}"
            binding.tvMana.text = "Mana: ${character.stats.mana}"
            binding.tvInventory.text = character.inventory.joinToString(", ")
        }

        binding.btnLevelUp.setOnClickListener {
            viewModelGameCharacter.levelUp()
        }

        binding.btnAddItem.setOnClickListener {
            viewModelGameCharacter.addItem("Sword")
        }


        with(binding) {
            etInputName.doAfterTextChanged {
                val name = etInputName.text.toString()
                viewModel.onNameChanged(name)
            }

            switchNotification.setOnCheckedChangeListener { _, isChecked ->
                viewModel.onNotificationChanged(isChecked)
            }

            btnClearAll.setOnClickListener {
                viewModel.onClearClicked()
                viewModelGameCharacter.resetCharacter()
            }

            radioGroupRoles.setOnCheckedChangeListener { _, isChecked ->
                val roleKey = when(isChecked) {
                    R.id.rbRoleAdmin -> UserRole.ADMIN
                    R.id.rbRoleUser -> UserRole.USER
                    else -> UserRole.GUEST
                }
                viewModel.onRoleChanged(roleKey)
            }
        }

        viewModel.role.observe(viewLifecycleOwner) { prefs ->
            if (binding.etInputName.text.toString() != prefs.name) {
                binding.etInputName.setText(prefs.name)
            }

            if (binding.switchNotification.isChecked != prefs.notification) {
                binding.switchNotification.isChecked = prefs.notification
            }
            binding.tvName.text = prefs.name

            when (prefs.role) {
                UserRole.USER -> binding.rbRoleUser.isChecked = true
                UserRole.ADMIN -> binding.rbRoleAdmin.isChecked = true
                else -> binding.rbRoleGuest.isChecked = true
            }
        }


        viewModel.settings.observe(viewLifecycleOwner) {prefs ->
            if (binding.cbStock.isChecked != prefs.showInStock) {
                binding.cbStock.isChecked = prefs.showInStock
            }
            when (prefs.sortKey) {
                "price" -> binding.rbSortPrice.isChecked = true
                "alphabet" -> binding.rbSortAlphabet.isChecked = true
            }
            if (binding.etMinPrice.text.toString() != prefs.minPrice.toString()) {
                binding.etMinPrice.setText(prefs.minPrice.toString())
            }
            binding.tvMinPrice.text = prefs.minPrice.toString()
        }

        binding.cbStock.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onShowInStockChanged(isChecked)
        }

        binding.radioGroupSort.setOnCheckedChangeListener { _, checkedId ->
            val sortKey = when(checkedId) {
                R.id.rbSortPrice -> "price"
                else -> "alphabet"
            }
            viewModel.onSortKeyChanged(sortKey)
        }

        binding.etMinPrice.doAfterTextChanged { text ->
            val price = text.toString().toIntOrNull() ?: 0
            viewModel.onMinPriceChanged(price)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}