package com.example.payback.view.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable

@Composable
fun HitSection(
    hits: List<com.example.domain.model.Hit>,
    onClick: (com.example.domain.model.Hit) -> Unit
) {
    LazyColumn {
        items(hits, key = { hit -> hit.id }) { hit ->
            HitCard(hit, onClick)
        }
    }

}