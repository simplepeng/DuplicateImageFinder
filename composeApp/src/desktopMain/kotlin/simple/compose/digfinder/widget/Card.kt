package simple.compose.digfinder.widget

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import simple.compose.digfinder.config.Dimen

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    OutlinedCard(
        modifier = modifier,
        shape = CardDefaults.outlinedShape,
        content = content
    )
}

@Composable
fun DialogCard(
    modifier: Modifier = Modifier.padding(Dimen.dialogPadding),
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(Dimen.dialogCornerSize),
        content = content
    )
}