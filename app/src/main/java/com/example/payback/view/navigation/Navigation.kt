package com.example.payback.view.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Navigation(val route: String, val arguments: List<NamedNavArgument>) {
    data object Search : Navigation(LAYOUT_SEARCH, emptyList())

    data object Details : Navigation(
        "$LAYOUT_DETAILS/{$PAR_IMAGE_ID}",
        listOf(navArgument(PAR_IMAGE_ID) { type = NavType.LongType })
    ) {
        fun getImageId(backStackEntry: NavBackStackEntry): Long? =
            backStackEntry.arguments?.getLong(PAR_IMAGE_ID)

        fun NavHostController.navigateToDetails(imageId: Long) =
            navigate("$LAYOUT_DETAILS/$imageId")
    }

    private companion object {
        const val LAYOUT_SEARCH = "layoutSearch"
        const val LAYOUT_DETAILS = "layoutDetails"
        const val PAR_IMAGE_ID = "imageId"
    }

}