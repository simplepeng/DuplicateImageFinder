package simple.compose.digfinder.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import duplicateimagefinder.composeapp.generated.resources.Res
import duplicateimagefinder.composeapp.generated.resources.ic_back
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(
    onBack: () -> Unit = {},
    showLoading: Boolean = false,
    modifier: Modifier = Modifier.fillMaxSize(),
    title: @Composable () -> Unit,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            TopAppBar(
                navigationIcon = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_back),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            onBack.invoke()
                        }
                    )
                },
                title = title,
                modifier = Modifier.fillMaxWidth()
            )
            Box(
                modifier = Modifier.fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        }
        if (showLoading) {

        }
    }
}
