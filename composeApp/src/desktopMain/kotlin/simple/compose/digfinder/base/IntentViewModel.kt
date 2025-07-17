package simple.compose.digfinder.base

abstract class IntentViewModel<UIState : BaseUIState, Intent : BaseIntent>(
    initUIState: UIState
) : UIStateViewModel<UIState>(initUIState) {

    abstract fun performIntent(intent: Intent)

}