package simple.compose.digfinder.base

sealed interface LoadDataUIState : BaseUIState {

    data object Loading : LoadDataUIState

    data object Content : LoadDataUIState

    data object Empty : LoadDataUIState

    data object Error : LoadDataUIState

}