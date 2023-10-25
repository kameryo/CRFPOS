package com.example.crfpos.page.sales

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.crfpos.R
import com.example.crfpos.model.coupon.Coupon
import com.example.crfpos.model.goods.Goods
import com.example.crfpos.model.selected.PendingPurchase

private const val MAX_PRICE_DIGITS = 7

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun SalesView(
    bindModel: SalesBindModel,
    onChangeAdultCount: (count: Int) -> Unit,
    onChangeChildCount: (count: Int) -> Unit,
    onChangeAdultManualCountText: (countText: String) -> Unit,
    onChangeChildManualCountText: (countText: String) -> Unit,
    onClickApplyAdultManualCountText: () -> Unit,
    onClickApplyChildManualCountText: () -> Unit,
    onClickAdjust: () -> Unit,
    onClickMinusForSelectedGoods: (selectedGoods: PendingPurchase) -> Unit,
    onClickPlusForSelectedGoods: (selectedGoods: PendingPurchase) -> Unit,
    onClickDeleteForSelectedGoods: (selectedGoods: PendingPurchase) -> Unit,
    onClickMinusForSelectedCoupon: (selectedGoods: PendingPurchase) -> Unit,
    onClickPlusForSelectedCoupon: (selectedGoods: PendingPurchase) -> Unit,
    onClickDeleteForSelectedCoupon: (selectedGoods: PendingPurchase) -> Unit,
    onClickGoodsFromStocks: (goods: Goods) -> Unit,
    onClickCouponFromStocks: (coupon: Coupon) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 32.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier.width(400.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(id = R.string.adult),
                    style = MaterialTheme.typography.displayMedium,
                )
                Box(
                    modifier = Modifier
                        .width(110.dp)
                        .height(70.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = bindModel.adultCount.toString(),
                        style = MaterialTheme.typography.displayMedium,
                    )
                }
                Text(
                    text = stringResource(id = R.string.child),
                    style = MaterialTheme.typography.displayMedium,
                )
                Box(
                    modifier = Modifier
                        .width(110.dp)
                        .height(70.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = bindModel.childCount.toString(),
                        style = MaterialTheme.typography.displayMedium,
                    )
                }
            }
            LazyColumn {
                items(bindModel.selectedCoupon) { selected ->
                    RequestingProductItemView(
                        productName = selected.name,
                        quantity = selected.numOfOrder,
                        price = selected.price,
                        onClickMinus = { onClickMinusForSelectedCoupon(selected) },
                        onClickPlus = { onClickPlusForSelectedCoupon(selected) },
                        onClickDelete = { onClickDeleteForSelectedCoupon(selected) },
                    )
                }
            }
            LazyColumn {
                items(bindModel.selectedGoods) { selected ->
                    RequestingProductItemView(
                        productName = selected.name,
                        quantity = selected.numOfOrder,
                        price = selected.price,
                        onClickMinus = { onClickMinusForSelectedGoods(selected) },
                        onClickPlus = { onClickPlusForSelectedGoods(selected) },
                        onClickDelete = { onClickDeleteForSelectedGoods(selected) },
                    )
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column {
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Text(
                            text = stringResource(id = R.string.fare),
                            style = MaterialTheme.typography.displaySmall,
                        )
                        Text(
                            text = stringResource(id = R.string.yen, bindModel.subtotalFare),
                            style = MaterialTheme.typography.displaySmall,
                            modifier = Modifier
                                .width(
                                    // MAX_PRICE_DIGITS分のwidthを確保する
                                    with(LocalDensity.current) {
                                        MaterialTheme.typography.titleLarge.fontSize.toDp() * MAX_PRICE_DIGITS
                                    }
                                ),
                            textAlign = TextAlign.End
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Text(
                            text = stringResource(id = R.string.toku_toku),
                            style = MaterialTheme.typography.displaySmall,
                        )
                        Text(
                            text = stringResource(id = R.string.yen, bindModel.subtotalCoupon),
                            style = MaterialTheme.typography.displaySmall,
                            modifier = Modifier
                                .width(
                                    // MAX_PRICE_DIGITS分のwidthを確保する
                                    with(LocalDensity.current) {
                                        MaterialTheme.typography.titleLarge.fontSize.toDp() * MAX_PRICE_DIGITS
                                    }
                                ),
                            textAlign = TextAlign.End
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Text(
                            text = stringResource(id = R.string.goods_sales),
                            style = MaterialTheme.typography.displaySmall,
                        )
                        Text(
                            text = stringResource(id = R.string.yen, bindModel.subtotalGoods),
                            style = MaterialTheme.typography.displaySmall,
                            modifier = Modifier
                                .width(
                                    // MAX_PRICE_DIGITS分のwidthを確保する
                                    with(LocalDensity.current) {
                                        MaterialTheme.typography.titleLarge.fontSize.toDp() * MAX_PRICE_DIGITS
                                    }
                                ),
                            textAlign = TextAlign.End
                        )
                    }
                }
                Button(onClick = onClickAdjust) {
                    Text(
                        text = stringResource(id = R.string.adjust),
                        style = MaterialTheme.typography.displayMedium,
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                FlowRow(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    listOf(1, 2, 3, 4, 0).forEach { count ->
                        if (bindModel.adultCount == count) {
                            SquareButton(
                                text = stringResource(id = R.string.adult) + count,
                                onClick = { onChangeAdultCount(count) },
                                backgroundColor = MaterialTheme.colorScheme.primary,
                                textColor = MaterialTheme.colorScheme.onPrimary,
                            )
                        } else {
                            SquareButton(
                                text = stringResource(id = R.string.adult) + count,
                                onClick = { onChangeAdultCount(count) },
                                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                                textColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            )
                        }
                    }
                    TextField(
                        value = bindModel.adultManualCountText,
                        onValueChange = onChangeAdultManualCountText,
                        modifier = Modifier
                            .width(60.dp)
                            .height(80.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        textStyle = MaterialTheme.typography.titleLarge,
                        singleLine = true,
                    )
                    SquareButton(
                        text = stringResource(id = R.string.input),
                        onClick = onClickApplyAdultManualCountText,
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                        textColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
                FlowRow(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    listOf(1, 2, 3, 4, 0).forEach { count ->
                        if (bindModel.childCount == count) {
                            SquareButton(
                                text = stringResource(id = R.string.child) + count,
                                onClick = { onChangeChildCount(count) },
                                backgroundColor = MaterialTheme.colorScheme.primary,
                                textColor = MaterialTheme.colorScheme.onPrimary,
                            )
                        } else {
                            SquareButton(
                                text = stringResource(id = R.string.child) + count,
                                onClick = { onChangeChildCount(count) },
                                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                                textColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            )
                        }
                    }
                    TextField(
                        value = bindModel.childManualCountText,
                        onValueChange = onChangeChildManualCountText,
                        modifier = Modifier
                            .width(60.dp)
                            .height(80.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        textStyle = MaterialTheme.typography.titleLarge,
                        singleLine = true,
                    )
                    SquareButton(
                        text = stringResource(id = R.string.input),
                        onClick = onClickApplyChildManualCountText,
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                        textColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
            }
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(120.dp),
            ) {
                items(bindModel.coupon) { coupon ->
                    GoodsItemView(
                        name = coupon.name,
                        price = coupon.price,
                        onClick = { onClickCouponFromStocks(coupon) },
                    )
                }
            }
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(120.dp),
            ) {
                items(bindModel.goods) { stock ->
                    GoodsItemView(
                        name = stock.name,
                        price = stock.price,
                        onClick = { onClickGoodsFromStocks(stock) },
                    )
                }
            }
        }
    }
}

@Composable
private fun SquareButton(
    text: String,
    textColor: Color,
    backgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .width(110.dp)
            .height(80.dp)
            .padding(1.dp)
            .background(backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            color = textColor,
        )
    }
}

@Preview(
    device = Devices.TABLET,
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun SalesViewPreview() {
    MaterialTheme {
        SalesView(
            bindModel = SalesBindModel(
                adultCount = 1,
                childCount = 2,
                selectedGoods = listOf(
                    PendingPurchase(
                        name = "test",
                        price = 100,
                        numOfOrder = 1,
                    )
                ),
                selectedCoupon = listOf(
                    PendingPurchase(
                        name = "coupon",
                        price = 100,
                        numOfOrder = 1,
                    )
                ),
                subtotalFare = 100,
                subtotalCoupon = 0,
                subtotalGoods = 30000,
                goods = List(20) {
                    Goods(
                        name = "test",
                        price = 100,
                        purchases = 0,
                        remain = 0,
                    )
                },
                coupon = List(2) {
                    Coupon(
                        name = "coupon",
                        price = 100,
                        remain = 0,
                    )
                },
            ),
            onChangeAdultCount = {},
            onChangeChildCount = {},
            onChangeAdultManualCountText = {},
            onChangeChildManualCountText = {},
            onClickApplyAdultManualCountText = {},
            onClickApplyChildManualCountText = {},
            onClickAdjust = {},
            onClickGoodsFromStocks = {},
            onClickMinusForSelectedGoods = {},
            onClickPlusForSelectedGoods = {},
            onClickDeleteForSelectedGoods = {},
            onClickMinusForSelectedCoupon = {},
            onClickPlusForSelectedCoupon = {},
            onClickDeleteForSelectedCoupon = {},
            onClickCouponFromStocks = {},
        )
    }
}
