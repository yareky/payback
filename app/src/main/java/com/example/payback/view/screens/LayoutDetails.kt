package com.example.payback.view.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.example.domain.model.Hit
import com.example.domain.model.Status
import com.example.payback.view.components.HitCardDetailed
import com.example.payback.view.viewmodel.ViewModelDetails
import org.koin.androidx.compose.koinViewModel

@Composable
fun LayoutDetails(
    navController: NavController,
    hitId: Long,
    viewModel: ViewModelDetails = koinViewModel()
) {
    val hit: Status<Hit> by viewModel.hit.collectAsState(
        Status.EMPTY
    )
    LaunchedEffect(true) {
        viewModel.getImage(hitId)
    }

    HitCardDetailedStatus(hit)

}

@Composable
fun HitCardDetailedStatus(hit: Status<Hit>) {
    when (hit) {
        is Status.SUCCESS -> HitCardDetailed(hit.value)
        else -> Unit
    }
}