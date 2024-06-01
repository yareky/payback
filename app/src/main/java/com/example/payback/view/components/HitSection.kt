package com.example.payback.view.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.domain.model.Hit

@Composable
fun HitSection(
    hits: List<Hit>,
    onClick: (Hit) -> Unit
) {
    LazyColumn {
        items(hits, key = { hit -> hit.id }) { hit ->
            HitCard(hit, onClick)
        }
    }

}