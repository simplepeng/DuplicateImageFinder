package simple.compose.digfinder.page.main

import simple.compose.digfinder.base.BaseViewModel

class MainViewModel : BaseViewModel<MainAction, MainUIState, MainIntent>(MainUIState.Content) {

    override fun performIntent(intent: MainIntent) {

    }

    fun addProject(projectName: String, projectPath: String) {
        doAction(MainAction.NavToFinder)
        updateUIState(MainUIState.Content)
    }

}