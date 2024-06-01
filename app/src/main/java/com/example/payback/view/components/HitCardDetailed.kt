package com.example.payback.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.domain.model.Hit
import com.example.payback.R

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