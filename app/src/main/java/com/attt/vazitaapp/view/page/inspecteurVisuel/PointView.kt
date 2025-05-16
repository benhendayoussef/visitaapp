package com.attt.vazitaapp.view.page.inspecteurVisuel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.attt.vazitaapp.modelView.DossierViewModel


@Composable
fun PointView(
    navController: NavController,
    chapterNavController: NavController,
    pointNavController: NavController,
    dossierViewModel: DossierViewModel,
) {
    val selectedChapitre by dossierViewModel.selectedChapitre.observeAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        items(selectedChapitre?.pointDefautResponses?.size ?: 0) { it->
            PointItem(
                pointDefault = selectedChapitre?.pointDefautResponses?.get(it),
                modifier = Modifier,
                onClick = {
                    dossierViewModel.setPointDefaultViewed(selectedChapitre?.codeChapitre?:-1,
                        selectedChapitre?.pointDefautResponses?.get(it)?.codePoint?:-1)
                    dossierViewModel.setSelectedPoint(selectedChapitre?.pointDefautResponses?.get(it))
                    pointNavController.navigate("AlterationPage")
                },

                )
        }

    }
}