package com.example.payback.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.payback.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HitCard(hit: com.example.domain.model.Hit, onClick: (com.example.domain.model.Hit) -> Unit) {
    Card(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(), onClick = { onClick(hit) }) {
        Row {
            AsyncImage(
                model = hit.previewUrl,
                contentDescription = hit.user,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .width(128.dp),
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = stringResource(R.string.user, hit.user))
                Text(text = stringResource(R.string.comments, hit.comments))
                Text(text = stringResource(R.string.tags, hit.tags))
            }
        }
    }
}