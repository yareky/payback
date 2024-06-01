package com.example.payback.view.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.payback.R
import com.example.payback.view.components.HitSection
import com.example.payback.view.navigation.Navigation.Details.navigateToDetails
import com.example.payback.view.viewmodel.ViewModelSearch
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun LayoutSearch(
    navHostController: NavHostController,
    viewModel: ViewModelSearch = koinViewModel()
) {
    var text by remember { mutableStateOf("") }
    val hits by viewModel.hits.collectAsState()
    val snackbarState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        viewModel.refresh()
    }

    Box {
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

            val context = LocalContext.current
            HitSection(hits) { hit ->
                scope.launch {
                    val snackbarResult = snackbarState.showSnackbar(
                        message = context.getString(R.string.do_you_want_to_open),
                        actionLabel = context.getString(R.string.yes),
                        duration = SnackbarDuration.Indefinite,
                        withDismissAction = true
                    )

                    when (snackbarResult) {
                        SnackbarResult.ActionPerformed -> navHostController.navigateToDetails(hit.id)
                        SnackbarResult.Dismissed -> Unit
                    }
                }
            }
        }

        SnackbarHost(
            hostState = snackbarState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .systemBarsPadding()
        )
    }
}
