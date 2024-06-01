package com.example.payback.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.domain.model.Hit
import com.example.domain.model.Status
import com.example.payback.R
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

@Composable
fun HitCardDetailed(hit: Hit) {
    Card(

        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = hit.largeImageUrl,
            contentDescription = hit.user,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )

        Column(Modifier.padding(16.dp)) {
            Text(text = stringResource(R.string.user, hit.user))
            Text(text = stringResource(R.string.tags, hit.tags))
            Text(text = stringResource(R.string.likes, hit.likes))
            Text(text = stringResource(R.string.downloads, hit.downloads))
            Text(text = stringResource(R.string.comments, hit.comments))
        }

    }
}

@Preview
@Composable
fun HitCardDetailedPreview() {
    HitCardDetailed(
        Hit(
            user = "",
            tags = "",
            previewUrl = "",
            likes = 3,
            largeImageUrl = "",
            id = 0,
            downloads = 5,
            comments = 13
        )
    )
}