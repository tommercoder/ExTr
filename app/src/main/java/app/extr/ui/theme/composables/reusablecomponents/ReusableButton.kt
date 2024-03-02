package app.extr.ui.theme.composables.reusablecomponents

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import app.extr.ui.theme.LocalCustomColorsPalette
import app.extr.ui.theme.shapeScheme

@Composable
fun ReusableButton(
    onClick: () -> Unit,
    textId: Int,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = LocalCustomColorsPalette.current.balanceCardColor
    )
) {
    Button(
        modifier = modifier,
        shape = MaterialTheme.shapeScheme.large,
        onClick = { onClick() },
        colors = colors
    ) {
        Text(
            text = stringResource(textId)
        )
    }
}