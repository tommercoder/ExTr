package app.extr.ui.theme.composables.reusablecomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.extr.R
import app.extr.ui.theme.CustomColorsPalette
import app.extr.ui.theme.LocalCustomColorsPalette
import app.extr.ui.theme.shapeScheme

@Composable
fun ErrorScreen(
    modifier : Modifier = Modifier,
    onRefresh: () -> Unit,
    resourceId: Int
) {
    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.header_error_occured),
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(id = resourceId),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(30.dp))
            Button(
                modifier = Modifier,
                shape = MaterialTheme.shapeScheme.large,
                onClick = { onRefresh() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = LocalCustomColorsPalette.current.balanceCardColor
                )
            ) {
                Text(text = stringResource(R.string.button_refresh))
            }
        }
    }
}