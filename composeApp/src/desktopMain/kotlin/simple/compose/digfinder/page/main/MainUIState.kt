package simple.compose.digfinder.page.main

import simple.compose.digfinder.base.BaseUIState

interface MainUIState : BaseUIState {

    data object Loading : MainUIState

    data object Content : MainUIState

}