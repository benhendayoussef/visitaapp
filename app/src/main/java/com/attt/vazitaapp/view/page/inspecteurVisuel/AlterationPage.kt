package com.attt.vazitaapp.view.page.inspecteurVisuel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.attt.vazitaapp.modelView.DossierViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.attt.vazitaapp.data.model.Alteration
import kotlin.String

@Composable
fun AlterationPage(
    navController: NavController,
    chapterNavController: NavController,
    pointNavController: NavController,
    dossierViewModel: DossierViewModel,
) {

    val selectedPoint by dossierViewModel.selectedPoint.observeAsState()
    val selectedChapitre by dossierViewModel.selectedChapitre.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if(selectedPoint == null) {
            return@Column
        }
        if(selectedChapitre == null) {
            return@Column
        }
        if(selectedPoint!=null)
            AlterationTopBarWithTabs(
            title = "Point ${selectedPoint?.codePoint}",
            nextChapitre = {
                val nextIndex =
                    (selectedChapitre?.pointDefautResponses?.indexOfFirst { it.codePoint == selectedPoint?.codePoint }
                        ?: 0) + 1
                if (nextIndex < (selectedChapitre?.pointDefautResponses?.size ?: 0)) {
                    dossierViewModel.setSelectedPoint(selectedChapitre?.pointDefautResponses?.get(nextIndex))
                }
            },
            previousChapitre = {
                val previousIndex =
                    (selectedChapitre?.pointDefautResponses?.indexOfFirst { it.codePoint == selectedPoint?.codePoint }
                        ?: 0) - 1
                if (previousIndex >= 0) {
                    dossierViewModel.setSelectedPoint(selectedChapitre?.pointDefautResponses?.get(previousIndex))
                }
            },
            onTitleClicked = {
                pointNavController.navigate("PointView")
                dossierViewModel.setSelectedPoint(null)
            },
            selectedTabIndex = selectedChapitre?.pointDefautResponses?.indexOfFirst { it.codePoint == selectedPoint?.codePoint }
                ?: 0,
            onTabSelected = { index ->
                dossierViewModel.setSelectedPoint(selectedChapitre?.pointDefautResponses?.get(index))
            },
            tabTitles = selectedChapitre?.pointDefautResponses?.map {
                it.libellePoint
            }?: emptyList()
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            items(selectedPoint?.alterationResponses?.size ?: 0) { it->
                AlterationItem(
                    alteration = selectedPoint?.alterationResponses?.get(it),
                    modifier = Modifier,
                    onClick = {
                        dossierViewModel
                            .changeAlterationSelected(
                                selectedPoint?.codeChapitre,
                                selectedPoint?.codePoint,
                                selectedPoint?.alterationResponses?.get(it)?.codeAlteration)
                    },

                    )
            }

        }
    }

}

@Composable
fun AlterationTopBarWithTabs(
    title: String,
    subtitle: String? = null,
    previousChapitre: () -> Unit = {},
    nextChapitre: () -> Unit = {},
    tabTitles: List<String>,
    selectedTabIndex: Int = 0,
    onTabSelected: (Int) -> Unit = {},
    onTitleClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
    enablePreviousButton: Boolean = true,
    enableNextButton: Boolean = true,
) {

        Column(modifier = modifier
            .background(MaterialTheme.colorScheme.surface)) {
            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 8.dp, vertical = 0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Navigation buttons (left)
                Row(
                    modifier = Modifier
                        .fillMaxWidth().padding(top=10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Previous chapter button
                    IconButton(
                        onClick = previousChapitre,
                        enabled = enablePreviousButton,
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = if (enablePreviousButton)
                                    MaterialTheme.colorScheme.surface.copy(alpha = 0.1f)
                                else
                                    MaterialTheme.colorScheme.surface,
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Previous Chapter",
                            tint = if (enablePreviousButton)
                                MaterialTheme.colorScheme.onSurface
                            else
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth(0.8f).background(Color.Red),
                        contentAlignment = Alignment.Center
                    ) {
                        ScrollableTabRow(
                            modifier = Modifier.wrapContentWidth(),
                            selectedTabIndex = selectedTabIndex,
                            edgePadding = 16.dp,
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.primary,
                            indicator = { tabPositions ->
                                if (selectedTabIndex < tabPositions.size) {
                                    TabRowDefaults.PrimaryIndicator(
                                        modifier = Modifier
                                            .tabIndicatorOffset(tabPositions[selectedTabIndex])
                                            .padding(horizontal = 16.dp), // this controls width indirectly
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            },
                            divider = {
                                HorizontalDivider(
                                    thickness = 1.dp,
                                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                                )
                            },

                            ) {
                            tabTitles.forEachIndexed { index, title ->
                                Tab(
                                    selected = selectedTabIndex == index,
                                    onClick = { onTabSelected(index) },
                                    modifier = Modifier.padding(horizontal = 4.dp),
                                    text = {
                                        Text(
                                            text = title,
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontSize = 20.sp,
                                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                                            color = if (selectedTabIndex == index)
                                                MaterialTheme.colorScheme.primary
                                            else
                                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                        )
                                    }
                                )

                            }
                        }
                    }

                    // Next chapter button
                    IconButton(
                        onClick = nextChapitre,
                        enabled = enableNextButton,
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = if (enableNextButton)
                                    MaterialTheme.colorScheme.surface.copy(alpha = 0.1f)
                                else
                                    MaterialTheme.colorScheme.surface,
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = "Next Chapter",
                            tint = if (enableNextButton)
                                MaterialTheme.colorScheme.onSurface
                            else
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                // Action icons (right) - can be added here if needed
                // For now, keeping it empty to match original design
            }

            // Tabs row with animated indicator

        }

}


@Composable
fun AlterationItem(
    alteration: Alteration?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    if(alteration == null) return
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (alteration.isSelected)
                MaterialTheme.colorScheme.tertiary.copy(alpha = 1f)
            else
                MaterialTheme.colorScheme.surface
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(36.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left side with code badge and title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f, fill = false)
            ) {
                // Code badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.wrapContentWidth().height(40.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.wrapContentWidth().padding(horizontal = 5.dp)
                    ) {
                        Text(
                            text = String.format("%03d", alteration.codeAlteration),
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                Spacer(modifier = Modifier.width(36.dp))

                // Alteration title
                Text(
                    text = alteration.libelleAlteration,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    fontSize = 26.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false)
                )
            }

            // Right side with selected status
            Box(modifier = Modifier.padding(start = 8.dp)) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (alteration.isSelected)
                        MaterialTheme.colorScheme.tertiary.copy(alpha = 0.15f)
                    else
                        MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = if (alteration.isSelected)
                                Icons.Default.CheckCircle
                            else
                                Icons.Default.RadioButtonUnchecked,
                            contentDescription = if (alteration.isSelected) "Selected" else "Not Selected",
                            tint = if (alteration.isSelected)
                                MaterialTheme.colorScheme.tertiary
                            else
                                MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}
