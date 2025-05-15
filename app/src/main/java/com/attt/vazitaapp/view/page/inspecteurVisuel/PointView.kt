package com.attt.vazitaapp.view.page.inspecteurVisuel

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import androidx.navigation.NavController
import com.attt.vazitaapp.modelView.DossierViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.map
import com.attt.vazitaapp.data.model.PointDefault


@Composable
fun PointPage(
    navController: NavController,
    chapterNavController: NavController,
    dossierViewModel: DossierViewModel,
) {
    val selectedChapitre by dossierViewModel.selectedChapitre.observeAsState()
    val chapitres by dossierViewModel.chapitres.observeAsState()

Column(
    modifier = Modifier.fillMaxSize()
){
    CustomTopBarWithTabs(
        title = "Chapitre ${selectedChapitre?.CODE_CHAPITRE}",
        subtitle = "Choisir un point de default",
        nextChapitre= {
            val nextIndex = (chapitres?.indexOfFirst { it.CODE_CHAPITRE == selectedChapitre?.CODE_CHAPITRE } ?: 0) + 1
            if (nextIndex < (chapitres?.size ?: 0)) {
                dossierViewModel.setSelectedChapitre(chapitres?.get(nextIndex))
            }
        },
        previousChapitre = {
            val previousIndex = (chapitres?.indexOfFirst { it.CODE_CHAPITRE == selectedChapitre?.CODE_CHAPITRE } ?: 0) - 1
            if (previousIndex >= 0) {
                dossierViewModel.setSelectedChapitre(chapitres?.get(previousIndex))
            }
        },
        onTitleClicked = {
            chapterNavController.navigate("ChapitreView")
            dossierViewModel.setSelectedChapitre(null)
        },
        selectedTabIndex = chapitres?.indexOfFirst { it.CODE_CHAPITRE == selectedChapitre?.CODE_CHAPITRE } ?: 0,
        onTabSelected = { index ->
            dossierViewModel.setSelectedChapitre(chapitres?.get(index))
        },
        tabTitles = dossierViewModel.chapitres.value.map {
            it.LIBELLE_CHAPITRE
        }
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        items(chapitres?.size ?: 0) { it->
            PointItem(
                pointDefault = selectedChapitre?.pointsDefault?.get(it),
                modifier = Modifier,
                onClick = {
                    dossierViewModel.setPointDefaultViewed(selectedChapitre?.CODE_CHAPITRE?:-1,
                        selectedChapitre?.pointsDefault?.get(it)?.CODE_POINT?:-1)
                    dossierViewModel.setSelectedPoint(selectedChapitre?.pointsDefault?.get(it))
                    chapterNavController.navigate("PointView")
                },

                )
        }

    }

}


}

@Composable
fun PointItem(pointDefault: PointDefault?, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    if(pointDefault == null) return
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (pointDefault.isViewed)
                MaterialTheme.colorScheme.surface
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
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = pointDefault.CODE_POINT.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                Spacer(modifier = Modifier.width(36.dp))

                // Chapter title
                Text(
                    text = pointDefault.LIBELLE_POINT,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    fontSize = 26.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false)
                )
            }

            // Right side with viewed status
            Box(modifier = Modifier.padding(start = 8.dp)) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (pointDefault.isViewed)
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
                            imageVector = if (pointDefault.isViewed) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (pointDefault.isViewed) "Viewed" else "Not viewed",
                            tint = if (pointDefault.isViewed)
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









@Composable
fun CustomTopBar(
    title: String,
    subtitle: String? = null,
    previousChapitre: () -> Unit = {},
    nextChapitre: () -> Unit = {},
    showBottomDivider: Boolean = true,
    onTitleClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
    enablePreviousButton: Boolean = true,
    enableNextButton: Boolean = true,
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .statusBarsPadding()
                .padding(bottom = if (showBottomDivider) 0.dp else 8.dp),
            contentAlignment = Alignment.Center
        ) {
            // Top row with navigation and actions
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Navigation buttons (left)
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
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

                    Spacer(modifier = Modifier.width(4.dp))

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

            // Middle row with centered title
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(onClick = onTitleClicked)
                    .padding( horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        // Optional bottom divider
        if (showBottomDivider) {
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun CustomTopBarWithTabs(
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
    Column(modifier = modifier) {
        // Top bar
        CustomTopBar(
            title = title,
            subtitle = subtitle,
            previousChapitre = previousChapitre,
            nextChapitre = nextChapitre,
            onTitleClicked = onTitleClicked,
            showBottomDivider = false,
            enablePreviousButton = enablePreviousButton,
            enableNextButton = enableNextButton
        )

        // Tabs row with animated indicator
        ScrollableTabRow(
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
            }
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
}