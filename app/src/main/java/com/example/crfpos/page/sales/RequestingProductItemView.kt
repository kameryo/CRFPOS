package com.example.crfpos.page.sales

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.crfpos.R

@Composable
fun RequestingProductItemView(
    productName: String,
    quantity: Int,
    price: Int,
    modifier: Modifier = Modifier,
    onClickMinus: () -> Unit,
    onClickPlus: () -> Unit,
    onClickDelete: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp)
            .fillMaxWidth(),
    ) {
        Text(
            text = productName,
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            FilledTonalIconButton(onClick = onClickMinus) {
                Icon(imageVector = Icons.Default.Remove, contentDescription = null)
            }
            Text(
                text = quantity.toString(),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.width(32.dp),
                textAlign = TextAlign.Center,
            )
            FilledTonalIconButton(onClick = onClickPlus) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.yen, price),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.width(100.dp),
                textAlign = TextAlign.Center,
            )
            FilledTonalIconButton(onClick = onClickDelete) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RequestingProductItemViewPreview() {
    RequestingProductItemView(
        productName = "Product name",
        quantity = 1,
        price = 100,
        onClickMinus = {},
        onClickPlus = {},
        onClickDelete = {},
    )
}
