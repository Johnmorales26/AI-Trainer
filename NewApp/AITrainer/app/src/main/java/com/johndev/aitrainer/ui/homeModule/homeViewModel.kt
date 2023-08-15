package com.johndev.aitrainer.ui.homeModule

import androidx.lifecycle.ViewModel
import com.johndev.aitrainer.R
import com.johndev.aitrainer.common.entities.MainMenuEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class homeViewModel @Inject constructor() : ViewModel() {

    fun getMenuItems(): List<MainMenuEntity> = listOf(
        MainMenuEntity(
            id = 1,
            title = R.string.menu_manual_regression,
            description = R.string.menu_manual_regression_description,
            image = R.drawable.ic_manual_regression
        ),
        MainMenuEntity(
            id = 2,
            title = R.string.menu_regression,
            description = R.string.menu_regression_description,
            image = R.drawable.ic_automatic
        ),
        MainMenuEntity(
            id = 3,
            title = R.string.menu_vector_regression,
            description = R.string.menu_vector_regression_description,
            image = R.drawable.ic_vector
        )
    )

}