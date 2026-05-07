package com.moseswn.kurahubke.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moseswn.kurahubke.ui.Screens.OnboardingScreen.OnboardingScreen
import com.moseswn.kurahubke.ui.Screens.about.AboutScreen
import com.moseswn.kurahubke.ui.Screens.faqs.FaqsScreen
import com.moseswn.kurahubke.ui.Screens.home.HomeScreen
import com.moseswn.kurahubke.ui.Screens.pollingfinder.PollingFinderScreen
import com.moseswn.kurahubke.ui.Screens.splash.SplashScreen
import com.moseswn.kurahubke.ui.Screens.timeline.TimelineScreen
import com.moseswn.kurahubke.ui.Screens.votingchecklist.VotingCheckinglistScreen
import java.sql.Time

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUT_SPLASH
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(ROUT_SPLASH) {
            SplashScreen(navController)
        }
        composable(ROUT_ABOUT) {
            AboutScreen(navController)
        }
        composable(ROUT_HOME) {
            HomeScreen(navController)
        }
        composable(ROUT_TIMELINE) {
            TimelineScreen(navController)

        }
        composable(ROUT_POLLINGFINDER) {
            PollingFinderScreen(navController)


        }
        composable(ROUT_FAQS) {
            FaqsScreen(navController)


        }
        composable(ROUT_VOTINGCHECKINGLIST) {
            VotingCheckinglistScreen(navController)
        }
        composable(ROUT_ONBOARDING) {
            OnboardingScreen(navController)



    }



}}