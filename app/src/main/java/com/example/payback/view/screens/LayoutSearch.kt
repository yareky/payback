package com.example.payback.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.payback.R
import com.example.payback.view.components.HitSection
import com.example.payback.view.navigation.Navigation.Details.navigateToDetails
import com.example.payback.view.viewmodel.ViewModelSearch
import org.koin.androidx.compose.koinViewModel

@Composable
fun LayoutSearch(
    navHostController: NavHostController,
    viewModel: ViewModelSearch = koinViewModel()
) {
    var text by remember { mutableStateOf("") }
    val hits by viewModel.hits.collectAsState()

    LaunchedEffect(true) {
        viewModel.refresh()
    }

    Column {


        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.query,
            onValueChange = {
                text = it
                viewModel.updateQueryAndRefresh(it)
            },
            label = { Text(stringResource(R.string.search_query)) }
        )

        HitSection(hits) { hit ->
            navHostController.navigateToDetails(hit.id)
        }
    }
}
