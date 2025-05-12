package com.attt.vazitaapp.view.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.attt.vazitaapp.R
import com.attt.visitaapp.view.component.CustomTextField

@Composable
fun SelectionDePiste(
    navController: NavController
) {

    var pisteId by remember { mutableStateOf("") }

    Column(
        modifier = androidx.compose.ui.Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Selection de Piste",
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            modifier = androidx.compose.ui.Modifier.padding(bottom = 16.dp)
        )
        CustomTextField(
            value = pisteId,
            onValueChange = { newValue ->
                pisteId = newValue
            },
            placeholkder = "Entrez l'ID de piste",
            label = "ID de piste",
            icon = painterResource(id = R.drawable.route),
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 30.dp)
                .fillMaxWidth(0.7f),
        )

    }
}