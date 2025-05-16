package com.attt.vazitaapp.view.page.inspecteurVisuel

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.attt.vazitaapp.modelView.DossierViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.attt.vazitaapp.data.model.Alteration
import com.attt.vazitaapp.data.model.DossierDefault
import com.attt.vazitaapp.modelView.UserViewModel
import com.attt.visitaapp.view.component.CustomButton
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SubmitPage(
    navController: NavController,
    chapterNavController: NavController,
    dossierViewModel: DossierViewModel,
    userViewModel: UserViewModel
) {
    val caroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    val chapitres by dossierViewModel.chapitres.observeAsState()
    val currentDateTime = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault()).format(Date())
    val selectedAlteration:List<Alteration> =chapitres?.flatMap { chapitre ->
        chapitre.pointDefautResponses.flatMap { pointDefault ->
            pointDefault.alterationResponses.filter { alteration ->
                alteration.isSelected
            }
        }
    }?:emptyList()

    LazyColumn {
        items(selectedAlteration.size) { index ->
            ControlDataItem(
                data = DossierDefault(
                    datCtrl = currentDateTime,
                    numCentr = userViewModel.user.value?.idCentre.toString(),
                    nDossier = dossierViewModel.selectedDossier.value?.numDossier.toString(),
                    dateHeur = dossierViewModel.selectedDossier.value?.dateHeureEnregistrement.toString(),
                    numChass = dossierViewModel.selectedDossier.value?.numChassis.toString(),
                    codeDefa = selectedAlteration[index].codeAlteration.toString(),
                    matAgent = userViewModel.user.value?.idUser?:""
                )
            )
        }
        item{
            CustomButton(
                text = "Enregistrer",
                color= MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(16.dp)
                    .height(70.dp)
                    .fillMaxWidth(),
                onClick = {
                    caroutineScope.launch {
                        dossierViewModel.submitDossier(
                            dossierViewModel.selectedDossier.value?.numDossier.toString(),
                            selectedAlteration.map { it.codeAlteration },
                            onFinish = {
                                if(it.code==200){
                                    error = false
                                    message=it.message.toString()
                                    showDialog = true
                                    Log.d("test", "test")

                                }
                                else{
                                    error = true
                                    message=it.message.toString()
                                    showDialog = true
                                    Log.d("test", it.code.toString())

                                }
                            }

                        )
                    }


                }

            )
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                if(error==false){
                    navController.navigate("FileDattente")
                }
                showDialog = false
                               },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = if (error) Icons.Default.Error else Icons.Default.Check,
                        contentDescription = if (error) "Erreur" else "Succès",
                        tint = if (error) MaterialTheme.colorScheme.error else Color(0xFF4CAF50)
                    )
                    Text(
                        text = if (error) "Erreur" else "Succès",
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            text = {
                Text(message)
            },
            confirmButton = {
                Button(onClick = {
                    if(error==false){
                        navController.navigate("FileDattente")
                    }
                    showDialog = false
                }) {
                    Text("OK")
                }
            }
        )
    }

}


@Composable
fun ControlDataItem(
    data: DossierDefault,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header row with key identifiers
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Dossier: ${data.nDossier}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = data.dateHeur,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Details section
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DataRow("Control ID", data.datCtrl)
                DataRow("Centre", data.numCentr)
                DataRow("Chassis", data.numChass)
                DataRow("Date d\'enregistrement", data.dateHeur)
                DataRow("Code Defaut", data.codeDefa)
                DataRow("Agent", data.matAgent)
            }
        }
    }
}

@Composable
private fun DataRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(0.4f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(0.6f),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}