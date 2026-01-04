package com.teils.tellem.navigation.presentation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.teils.main_menu.presentation.screens.aiChatScreen.AiChatScreenView
import com.teils.main_menu.presentation.screens.gamificationScreen.GamificationScreenView
import com.teils.main_menu.presentation.screens.homeScreen.HomeScreenView
import com.teils.main_menu.presentation.screens.resultScreen.ResultScreenView
import com.teils.main_menu.presentation.screens.resultScreen.ResultType
import com.teils.main_menu.presentation.screens.tatarPlacesScreen.TatarPlacesScreenView
import com.teils.tellem.main.menu.presentation.screens.courseDetailsScreen.CourseDetailsScreenView
import com.teils.tellem.main.menu.presentation.screens.flashcards.FlashcardsScreenView
import com.teils.tellem.main.menu.presentation.screens.lessonScreen.LessonScreenView
import com.teils.tellem.main.menu.presentation.screens.practiceScreen.PracticeScreenView
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Serializable
object AiChatScreen

@Serializable
object GamificationScreen

@Serializable
object TatarPlacesScreen

@Serializable
data class ResultScreen(val courseId: Int, val type: Int)

@Serializable
data class CourseDetails(val courseId: Int)

@Serializable
data class Lesson(val courseId: Int)

@Serializable
data class Flashcards(val courseId: Int)

@Serializable
data class Practice(val courseId: Int)

@Composable
fun CoreNavHost() {
    val navController = rememberNavController()

    NavHost(navController, HomeScreen) {
        composable<HomeScreen> {
            HomeScreenView(
                navigateToGamificationScreen = { navController.navigate(GamificationScreen) },
                navigateToCourseDetailsScreen = { navController.navigate(CourseDetails(it)) },
                navigateToAiChat = { navController.navigate(AiChatScreen) },
                navigateToTatarPlaces = { navController.navigate(TatarPlacesScreen) },
            )
        }

        composable<AiChatScreen>(
            enterTransition = { slideInHorizontally { it } },
            exitTransition = { null },
            popEnterTransition = { null },
            popExitTransition = { slideOutHorizontally { it } },
        ) {
            AiChatScreenView(
                navigateBack = navController::popBackStack
            )
        }

        composable<TatarPlacesScreen>(
            enterTransition = { slideInHorizontally { it } },
            exitTransition = { null },
            popEnterTransition = { null },
            popExitTransition = { slideOutHorizontally { it } },
        ) {
            TatarPlacesScreenView(
                navigateBack = navController::popBackStack
            )
        }

        composable<GamificationScreen>(
            enterTransition = { slideInHorizontally { it } },
            exitTransition = { null },
            popEnterTransition = { null },
            popExitTransition = { slideOutHorizontally { it } },
        ) {
            GamificationScreenView(
                navigateBack = navController::popBackStack
            )
        }

        composable<CourseDetails>(
            enterTransition = { slideInHorizontally { it } },
            exitTransition = { null },
            popEnterTransition = { null },
            popExitTransition = { slideOutHorizontally { it } },
        ) { backStackEntry ->
            val courseDetails: CourseDetails = backStackEntry.toRoute()
            CourseDetailsScreenView(
                courseId = courseDetails.courseId,
                navigateToHomeScreen = { navController.popBackStack() },
                navigateToLessonScreen = { navController.navigate(Lesson(courseDetails.courseId)) },
                navigateToFlashcardsScreen = { navController.navigate(Flashcards(courseDetails.courseId)) },
                navigateToPracticeScreen = { navController.navigate(Practice(courseDetails.courseId)) },
                navigateToAiChat = { navController.navigate(AiChatScreen) }
            )
        }

        composable<Lesson>(
            enterTransition = { slideInVertically { it } },
            exitTransition = { null },
            popEnterTransition = { null },
            popExitTransition = { slideOutVertically { it } },
        ) { backStackEntry ->
            val lesson: Lesson = backStackEntry.toRoute()
            LessonScreenView(
                courseId = lesson.courseId,
                onLessonCompleted = {
                    if (it) navController.popBackStack()
                    else navController.navigate(ResultScreen(lesson.courseId, 1))
                },
                navigateToCourseDetails = { navController.popBackStack() }
            )
        }

        composable<ResultScreen>(
            enterTransition = { slideInVertically { it } },
            exitTransition = { null },
            popEnterTransition = { null },
            popExitTransition = { slideOutVertically { it } },
        ) { backStackEntry ->
            val resultScreen: ResultScreen = backStackEntry.toRoute()
            ResultScreenView(
                type = when (resultScreen.type) {
                    1 -> ResultType.LESSON
                    2 -> ResultType.FLASHCARDS
                    else -> ResultType.AI_CHAT
                },
                navigateToCourseDetails = {
                    navController.popBackStack(
                        CourseDetails(resultScreen.courseId),
                        false
                    )
                }
            )
        }

        composable<Flashcards>(
            enterTransition = { slideInVertically { it } },
            exitTransition = { null },
            popEnterTransition = { null },
            popExitTransition = { slideOutVertically { it } },
        ) { backStackEntry ->
            val lesson: Flashcards = backStackEntry.toRoute()
            FlashcardsScreenView(
                courseId = lesson.courseId,
                onLessonCompleted = {
                    if (it) navController.popBackStack()
                    else navController.navigate(ResultScreen(lesson.courseId, 2))
                },
                navigateToCourseDetails = { navController.popBackStack() }
            )
        }

        composable<Practice>(
            enterTransition = { slideInHorizontally { it } },
            exitTransition = { null },
            popEnterTransition = { null },
            popExitTransition = { slideOutHorizontally { it } },
        ) { backStackEntry ->
            val lesson: Practice = backStackEntry.toRoute()
            PracticeScreenView(
                courseId = lesson.courseId,
                navigateToCourseDetails = { navController.popBackStack() }
            )
        }
    }
}