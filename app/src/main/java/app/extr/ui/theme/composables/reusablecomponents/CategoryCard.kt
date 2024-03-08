package app.extr.ui.theme.composables.reusablecomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.extr.R
import app.extr.data.types.Balance
import app.extr.data.types.TransactionType
import app.extr.ui.theme.shapeScheme
import app.extr.ui.theme.viewmodels.TransactionByType
import app.extr.utils.helpers.AnimatedText
import app.extr.utils.helpers.AnimatedTextWithSign
import app.extr.utils.helpers.Constants
import app.extr.utils.helpers.resproviders.ResProvider

@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    transactionByType: TransactionByType,
    resProvider: ResProvider
) {
    val type = transactionByType.transactionType
    val currency = transactionByType.currency
    val balances = transactionByType.balances
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(45.dp)
                .background(
                    color = resProvider.getRes(type.colorId).color,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = resProvider.getRes(type.iconId).icon),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
        }

        Spacer(modifier = Modifier.width(15.dp))

        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = type.name,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = formatBalanceNames(balances),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            AnimatedTextWithSign(
                totalValue = transactionByType.totalAmount,
                currencySign = currency.symbol,
                valueStyle = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}

fun formatBalanceNames(balances: List<Balance>, separator: String = " · "): String {
    return balances.joinToString(separator) { it.customName }
}