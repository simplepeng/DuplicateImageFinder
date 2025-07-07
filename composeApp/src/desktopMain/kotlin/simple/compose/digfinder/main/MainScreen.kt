package simple.compose.digfinder.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen() {
    MainScreenContent()
}

@Composable
fun MainScreenContent() {
    Box(
        modifier = Modifier.fillMaxSize().padding(bottom = 10.dp, end = 10.dp)
    ) {
        ExtendedFloatingActionButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = {},
            text = {
                Text(
                    text = "Add"
                )
            }
        )
    }
}