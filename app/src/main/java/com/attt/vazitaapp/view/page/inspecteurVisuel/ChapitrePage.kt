package com.attt.vazitaapp.view.page.inspecteurVisuel

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.attt.vazitaapp.modelView.DossierViewModel


@Composable
fun ChapitrePage(
    navController: NavController,
    dossierViewModel: DossierViewModel,
) {

    val chapterNavController = rememberNavController()
    NavHost(
        navController = chapterNavController,
        startDestination = "ChapitreView",
    ) {
        composable(
            route = "ChapitreView",
            exitTransition = {
                fadeOut(animationSpec = tween(300)) +
                        scaleOut(targetScale = 1.5f, animationSpec = tween(500))
            }
        ) {
            ChapitreView(
                navController=navController,
                chapterNavController = chapterNavController,
                dossierViewModel = dossierViewModel
            )
        }
        composable(
            route = "PointPage",
            exitTransition = {
                fadeOut(animationSpec = tween(300)) +
                        scaleOut(targetScale = 1.5f, animationSpec = tween(500))
            }
        ) {
            PointPage(
                navController=navController,
                chapterNavController = chapterNavController,
                dossierViewModel = dossierViewModel
            )
        }
    }

}

