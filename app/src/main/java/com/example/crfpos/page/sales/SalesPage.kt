package com.example.crfpos.page.sales

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.crfpos.R
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesPage(
    viewModel: SalesViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
) {
    val bindModel = viewModel.bindModel.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.clearInputs()

        viewModel.errorMessageFlow
            .filterNotNull()
            .filter { it.isNotEmpty() }
            .collect { errorMessage ->
                snackbarHostState.showSnackbar(errorMessage)
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                actions = {
                    IconButton(onClick = { viewModel.clearInputs() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                        )
                    }
                    IconButton(onClick = navigateToHome) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = null,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.LightGray,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black,
                ),
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { innerPadding ->
        SalesView(
            bindModel = bindModel.value,
            onChangeAdultCount = viewModel::updateAdultCount,
            onChangeChildCount = viewModel::updateChildCount,
            onChangeAdultManualCountText = viewModel::updateAdultManualCountText,
            onChangeChildManualCountText = viewModel::updateChildManualCountText,
            onClickApplyAdultManualCountText = viewModel::applyAdultManualCountText,
            onClickApplyChildManualCountText = viewModel::applyChildManualCountText,
            onClickAdjust = {
                viewModel.saveRecord()
                viewModel.clearInputs()
            },
            onClickMinusForSelectedGoods = viewModel::decrementGoods,
            onClickPlusForSelectedGoods = viewModel::incrementGoods,
            onClickDeleteForSelectedGoods = viewModel::deleteGoods,
//            onClickMinusForSelectedCoupon = viewModel::decrementCoupon,
//            onClickPlusForSelectedCoupon = viewModel::incrementCoupon,
//            onClickDeleteForSelectedCoupon = viewModel::deleteCoupon,
            onClickGoodsFromStocks = { goods ->
                if (bindModel.value.selectedGoods.any { it.name == goods.name }) return@SalesView
                viewModel.addGoodsToSelectedGoods(goods)
            },
            modifier = Modifier.padding(innerPadding),
//            onClickCouponFromStocks = { coupon ->
//                if (bindModel.value.selectedCoupon.any { it.name == coupon.name }) return@SalesView
//                viewModel.addCouponToSelectedCoupon(coupon)
//            },
        )
    }
}
