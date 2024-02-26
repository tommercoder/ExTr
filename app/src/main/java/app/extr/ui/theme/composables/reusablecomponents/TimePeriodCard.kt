package app.extr.ui.theme.composables.reusablecomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.extr.R
import app.extr.ui.theme.AppPadding
import app.extr.ui.theme.ExTrTheme
import app.extr.ui.theme.shapeScheme
import app.extr.utils.helpers.AnimatedTextWithSign
import app.extr.utils.helpers.Constants

@Composable
fun TimePeriodCard(
    labelId: Int,
    amount: Int,
    currencySign: Char,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                shape = MaterialTheme.shapeScheme.extraLarge),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(labelId)
            )

            AnimatedTextWithSign(
                totalValue = amount.toDouble(),
                currencySign = currencySign,
                format = Constants.precisionZero,
                valueStyle = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Preview
@Composable
fun preview() {
    ExTrTheme {
        Row(
            modifier = Modifier
        ) {
            TimePeriodCard(
                labelId = R.string.app_name,
                amount = 100,
                currencySign = '$',
                modifier = Modifier.padding(AppPadding.ExtraSmall).size(height = 90.dp, width = 120.dp)
            )
            TimePeriodCard(
                labelId = R.string.app_name,
                amount = 100,
                currencySign = '$',
                modifier = Modifier.padding(AppPadding.ExtraSmall).size(height = 90.dp, width = 120.dp)
            )
            TimePeriodCard(
                labelId = R.string.app_name,
                amount = 100,
                currencySign = '$',
                modifier = Modifier.padding(AppPadding.ExtraSmall).size(height = 90.dp, width = 120.dp)
            )
        }
    }
}