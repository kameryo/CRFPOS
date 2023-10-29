package com.example.crfpos.page.sales

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GoodsItemView(
    name: String,
    price: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(5.dp)
            .clickable(onClick = onClick)
            .background(Color.White)
            .padding(16.dp),
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = price.toString(),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview
@Composable
private fun GoodsItemPreview() {
    GoodsItemView(
        name = "Product name",
        price = 100,
        onClick = {},
    )
}
