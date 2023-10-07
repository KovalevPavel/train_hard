package me.kovp.trainhard.home_presentation

sealed interface HomeEvent {
    data object OnStartTrainingClick : HomeEvent
}
