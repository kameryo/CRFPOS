package com.example.crfpos.page.sales

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.example.crfpos.R
//import com.example.crfpos.model.coupon.Coupon
import com.example.crfpos.model.goods.Goods
import com.example.crfpos.model.selected.PendingPurchase

private const val MAX_PRICE_DIGITS = 7
private const val MAX_TICKET_DIGITS = 4

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
//    onClickMinusForSelectedCoupon: (selectedGoods: PendingPurchase) -> Unit,
//    onClickPlusForSelectedCoupon: (selectedGoods: PendingPurchase) -> Unit,
//    onClickDeleteForSelectedCoupon: (selectedGoods: PendingPurchase) -> Unit,
    onClickGoodsFromStocks: (goods: Goods) -> Unit,
//    onClickCouponFromStocks: (coupon: Coupon) -> Unit,
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
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(70.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CustomBorder(
                        1.5.dp,
                        Color.DarkGray,
                        Color.White,
                        Color.DarkGray,
                        Color.White
                    )
                    Text(
                        text = stringResource(id = R.string.adult),
                        fontSize = 30.sp,
                        color = Color.Blue
                    )
                }
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(70.dp)
                        .background(Color.White),
                    contentAlignment = Alignment.Center,
                ) {
                    CustomBorder(
                        1.5.dp,
                        Color.DarkGray,
                        Color.DarkGray,
                        Color.DarkGray,
                        Color.DarkGray
                    )
                    Text(
                        text = bindModel.adultCount.toString(),
                        fontSize = 40.sp,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.weight(10F))
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(70.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CustomBorder(
                        1.5.dp,
                        Color.DarkGray,
                        Color.White,
                        Color.DarkGray,
                        Color.White
                    )
                    Text(
                        text = stringResource(id = R.string.child),
                        fontSize = 30.sp,
                        color = Color.Blue
                    )
                }
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(70.dp)
                        .background(Color.White),
                    contentAlignment = Alignment.Center,
                ) {
                    CustomBorder(
                        1.5.dp,
                        Color.DarkGray,
                        Color.DarkGray,
                        Color.DarkGray,
                        Color.DarkGray
                    )
                    Text(
                        text = bindModel.childCount.toString(),
                        fontSize = 40.sp,
                        color = Color.Black
                    )
                }
            }
            LazyColumn {
                items(bindModel.selectedGoods) { selected ->
                    RequestingProductItemView(
                        productName = selected.name,
                        numOfOrder = selected.numOfOrder,
                        unitPrice = selected.unitPrice,
                        onClickMinus = { onClickMinusForSelectedGoods(selected) },
                        onClickPlus = { onClickPlusForSelectedGoods(selected) },
                        onClickDelete = { onClickDeleteForSelectedGoods(selected) },
                    )
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(25.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column {
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Text(
                            text = stringResource(id = R.string.fare),
                            fontSize = 25.sp,
                        )
                        Text(
                            text = stringResource(id = R.string.yen, bindModel.subtotalFare),
                            fontSize = 25.sp,
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
                    Spacer(modifier = Modifier.height(1.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Text(
                            text = stringResource(id = R.string.goods_sales),
                            fontSize = 25.sp,
                        )
                        Text(
                            text = stringResource(id = R.string.yen, bindModel.subtotalGoods),
                            fontSize = 25.sp,
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
                    Spacer(modifier = Modifier.height(1.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = stringResource(id = R.string.sum),
                            fontSize = 30.sp,
                        )
                        Text(
                            text = stringResource(
                                id = R.string.yen,
                                bindModel.subtotalFare + bindModel.subtotalGoods
                            ),
                            fontSize = 30.sp,
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
                Column {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Column {
                            if (bindModel.subtotalFare != 0) {
                                Text(
                                    text = stringResource(id = R.string.normal_ticket),
                                    fontSize = 27.sp,
                                )
                                Text(
                                    text = stringResource(id = R.string.accompany_ticket),
                                    fontSize = 27.sp
                                )
                            }
                            if (bindModel.selectedGoods.any{it.name=="運転体験券"}) {
                                Text(
                                    text = stringResource(id = R.string.driving_ticket),
                                    fontSize = 27.sp
                                )
                            }
                        }
                        Column {
                            if (bindModel.subtotalFare != 0) {
                                Text(
                                    text = stringResource(
                                        id = R.string.mai,
                                        bindModel.subtotalFare / 100
                                    ),
                                    fontSize = 27.sp,
                                    modifier = Modifier
                                        .width(
                                            // MAX_TICKET_DIGITS分のwidthを確保する
                                            with(LocalDensity.current) {
                                                MaterialTheme.typography.titleLarge.fontSize.toDp() * MAX_TICKET_DIGITS
                                            }
                                        ),
                                    textAlign = TextAlign.End
                                )
                                Text(
                                    text = stringResource(
                                        id = R.string.mai,
                                        bindModel.adultCount + bindModel.childCount - bindModel.subtotalFare / 100
                                    ),
                                    fontSize = 27.sp,
                                    modifier = Modifier
                                        .width(
                                            // MAX_TICKET_DIGITS分のwidthを確保する
                                            with(LocalDensity.current) {
                                                MaterialTheme.typography.titleLarge.fontSize.toDp() * MAX_TICKET_DIGITS
                                            }
                                        ),
                                    textAlign = TextAlign.End
                                )
                            }
                            if (bindModel.selectedGoods.any{it.name=="運転体験券"}) {
                                Text(
                                    text = stringResource(id = R.string.mai, bindModel.selectedGoods.find { it.name == "運転体験券" }?.numOfOrder ?: 0
                                    ),
                                    fontSize = 27.sp,
                                    modifier = Modifier
                                        .width(
                                            // MAX_PRICE_DIGITS分のwidthを確保する
                                            with(LocalDensity.current) {
                                                MaterialTheme.typography.titleLarge.fontSize.toDp() * MAX_TICKET_DIGITS
                                            }
                                        ),
                                    textAlign = TextAlign.End
                                )
                            }
                        }

                    }

                    Spacer(modifier = Modifier.height(2.dp))
                }
                AdjustButton(
                    text = stringResource(id = R.string.adjust),
                    onClick = onClickAdjust,
                    backgroundColor = Color.Yellow,
                    textColor = Color.Black,
                )
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
                                backgroundColor = Color.Cyan,
                                textColor = Color.Black,
                            )
                        } else {
                            SquareButton(
                                text = stringResource(id = R.string.adult) + count,
                                onClick = { onChangeAdultCount(count) },
                                backgroundColor = Color.White,
                                textColor = Color.Black,
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
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White
                        ),
                        singleLine = true,
                    )
                    SquareButton(
                        text = stringResource(id = R.string.input),
                        onClick = onClickApplyAdultManualCountText,
                        backgroundColor = Color.White,
                        textColor = Color.Black,
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
                                backgroundColor = Color.Cyan,
                                textColor = Color.Black,
                            )
                        } else {
                            SquareButton(
                                text = stringResource(id = R.string.child) + count,
                                onClick = { onChangeChildCount(count) },
                                backgroundColor = Color.White,
                                textColor = Color.Black,
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
                        backgroundColor = Color.White,
                        textColor = Color.Black,
                    )
                }
            }
            Spacer(modifier = Modifier.width(15.dp))
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
            .width(120.dp)
            .height(85.dp)
            .padding(1.dp)
            .background(backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        CustomBorder(
            1.5.dp,
            Color.Black,
            Color.Black,
            Color.Black,
            Color.Black
        )
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            color = textColor,
        )
    }
}

@Composable
private fun AdjustButton(
    text: String,
    textColor: Color,
    backgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .width(180.dp)
            .height(120.dp)
            .padding(1.dp)
            .background(backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        CustomBorder(
            2.dp,
            Color.Black,
            Color.Black,
            Color.Black,
            Color.Black
        )
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            color = textColor,
            fontSize = 50.sp
        )
    }
}

@Composable
private fun CustomBorder(
    widthDp: Dp,
    topColor: Color,
    bottomColor: Color,
    leftColor: Color,
    rightColor: Color,
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = widthDp.toPx()
        // Boxの上と左の枠を黒に描画
        drawRect(
            color = topColor,
            topLeft = Offset(0f, 0f),
            size = Size(size.width, width),
        )
        drawRect(
            color = leftColor,
            topLeft = Offset(0f, 0f),
            size = Size(width, size.height),
        )
        // Boxの下と右の辺を白に描画
        drawRect(
            color = bottomColor,
            topLeft = Offset(0f, size.height - width),
            size = Size(size.width, width),
        )
        drawRect(
            color = rightColor,
            topLeft = Offset(size.width - width, 0f),
            size = Size(width, size.height),
        )
    }
}

@Preview(
    device = Devices.TABLET,
    showBackground = true,
    showSystemUi = true,
    backgroundColor = 0xFFCCCCCC
)
@Composable
private fun SalesViewPreview() {
    MaterialTheme {
        SalesView(
            bindModel = SalesBindModel(
                adultCount = 2,
                childCount = 4,
                selectedGoods = listOf(
                    PendingPurchase(
                        name = "test",
                        unitPrice = 100,
                        numOfOrder = 1,
                        amount = 100
                    ),
                    PendingPurchase(
                        name = "運転体験券",
                        unitPrice = 100,
                        numOfOrder = 1,
                        amount = 100
                    )
                ),
//                selectedCoupon = listOf(
//                    PendingPurchase(
//                        name = "coupon",
//                        price = 100,
//                        numOfOrder = 1,
//                    )
//                ),
                subtotalFare = 100,
//                subtotalCoupon = 0,
                subtotalGoods = 1000,
                goods = List(20) {
                    Goods(
                        name = "test",
                        price = 100,
                        purchases = 0,
                        remain = 0,
                    )
                },
//                coupon = List(2) {
//                    Coupon(
//                        name = "coupon",
//                        price = 100,
//                        remain = 0,
//                    )
//                },
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
//            onClickMinusForSelectedCoupon = {},
//            onClickPlusForSelectedCoupon = {},
//            onClickDeleteForSelectedCoupon = {},
//            onClickCouponFromStocks = {},
        )
    }
}
