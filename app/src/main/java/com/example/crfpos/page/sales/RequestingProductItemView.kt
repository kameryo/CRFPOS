package com.example.crfpos.page.sales

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.crfpos.R

private const val MAX_PRICE_DIGITS = 4

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
            .width(400.dp)
            .padding(8.dp),
    ) {
        Text(
            text = productName,
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                FilledTonalIconButton(onClick = onClickMinus) {
                    Icon(imageVector = Icons.Default.Remove, contentDescription = null)
                }
                Text(
                    text = quantity.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                )
                FilledTonalIconButton(onClick = onClickPlus) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(id = R.string.yen, price),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(
                        // MAX_PRICE_DIGITS分のwidthを確保する
                        with(LocalDensity.current) {
                            MaterialTheme.typography.titleLarge.fontSize.toDp() * MAX_PRICE_DIGITS
                        }
                    )
                )
                FilledTonalIconButton(onClick = onClickDelete) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }
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
