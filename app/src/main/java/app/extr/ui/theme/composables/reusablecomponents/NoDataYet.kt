package app.extr.ui.theme.composables.reusablecomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.extr.R
import app.extr.ui.theme.LocalCustomColorsPalette

@Composable
fun NoDataYet(
    modifier: Modifier = Modifier,
    headerId: Int,
    labelId: Int,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val iconSize = 60.dp
                Icon(
                    modifier = Modifier.size(iconSize),
                    painter = painterResource(id = R.drawable.card_icon),
                    contentDescription = null,
                    tint = LocalCustomColorsPalette.current.balanceCardColor
                )
                Icon(
                    modifier = Modifier.size(iconSize),
                    painter = painterResource(id = R.drawable.ewallet_icon),
                    contentDescription = null,
                    tint = LocalCustomColorsPalette.current.balanceCashColor
                )
                Icon(
                    modifier = Modifier.size(iconSize),
                    painter = painterResource(id = R.drawable.savings_icon),
                    contentDescription = null,
                    tint = LocalCustomColorsPalette.current.balanceSavingsColor
                )
                Icon(
                    modifier = Modifier.size(iconSize),
                    painter = painterResource(id = R.drawable.cash_icon),
                    contentDescription = null,
                    tint = LocalCustomColorsPalette.current.balanceEWalletColor
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(id = headerId),
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = stringResource(id = labelId),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}
