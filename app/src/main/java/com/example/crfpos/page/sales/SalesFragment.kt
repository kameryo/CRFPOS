package com.example.crfpos.page.sales

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.crfpos.theme.CRFPOSTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SalesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                CRFPOSTheme {
                    SalesPage(
                        navigateToHome = { findNavController().popBackStack() }
                    )
                }
            }
        }
    }
}
