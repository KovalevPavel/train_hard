package me.kovp.trainhard.home_presentation

sealed interface HomeAction {
    data object Empty: HomeAction
    data object OpenNewTrainingScreen: HomeAction
}
