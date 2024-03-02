package app.extr.ui.theme.composables.reusablecomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.extr.R
import app.extr.ui.theme.ExTrTheme

@Composable
fun OnOffSwitch(
    textId: Int,
    checked: Boolean,
    onChecked: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    // var checked by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
    ) {
        HorizontalDivider(thickness = 1.dp)
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = textId),
                //style = MaterialTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.weight(1.0f))
            Switch(
                modifier = Modifier.scale(0.7f),
                checked = checked,
                onCheckedChange = {
                    onChecked(it)
                }
            )
        }
    }
}

@Preview
@Composable
fun SwitchPreview() {
    ExTrTheme {
        OnOffSwitch(textId = R.string.app_name, checked = true, onChecked = {})
    }
}