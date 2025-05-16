package com.attt.vazitaapp.view.page.inspecteurVisuel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.attt.vazitaapp.R
import com.attt.vazitaapp.modelView.DossierViewModel
import com.attt.visitaapp.view.component.CustomButton
import com.attt.visitaapp.view.component.CustomTextField
import kotlinx.coroutines.launch
import kotlin.Int

@Composable
fun SelectionDePiste(
    navController: NavController,
    dossierViewModel: DossierViewModel,
) {

    val pisteId by dossierViewModel.pisteId.observeAsState("")
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Selection de Piste",
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        CustomTextField(
            value = pisteId.toString(),
            onValueChange = { newValue ->
                dossierViewModel.setPisteId(newValue)
            },
            placeholkder = "Entrez l'ID de piste",
            label = "ID de piste",
            icon = painterResource(id = R.drawable.route),
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 30.dp)
                .fillMaxWidth(0.7f)

        )
        CustomButton(
            text = "Valider",
            color = MaterialTheme.colorScheme.primary,
            onClick = {


                coroutineScope.launch {
                    dossierViewModel.loadDossier()
                    if (dossierViewModel.dossier.value != null)
                        navController.navigate("FileDattente")
                }
            },
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 30.dp)
                .fillMaxWidth(0.7f)
        )

    }
}