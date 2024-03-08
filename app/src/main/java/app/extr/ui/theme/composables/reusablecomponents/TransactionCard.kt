package app.extr.ui.theme.composables.reusablecomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.extr.R
import app.extr.data.types.Transaction
import app.extr.data.types.TransactionWithDetails
import app.extr.ui.theme.shapeScheme
import app.extr.ui.theme.viewmodels.TransactionByType
import app.extr.utils.helpers.AnimatedTextWithSign
import app.extr.utils.helpers.resproviders.ResProvider

@Composable
fun TransactionCard(
    modifier: Modifier = Modifier,
    transaction: TransactionWithDetails,
    resProvider: ResProvider,
    onDelete: (Transaction) -> Unit
) {
    val type = transaction.transactionType
    val currency = transaction.currency
    val balance = transaction.balance
    val descriptionNotEmpty = transaction.transaction.description.isNotEmpty()

    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .background(
                shape = MaterialTheme.shapeScheme.large,
                color = resProvider.getRes(type.colorId).color.copy(alpha = 0.3f)
            )
            .height(IntrinsicSize.Min)
            .padding(8.dp)
    ) {
        Row(
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
                    text = balance.customName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                //horizontalAlignment = Alignment.End,
                modifier = Modifier.align(Alignment.CenterVertically),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedTextWithSign(
                    totalValue = transaction.transaction.transactionAmount,
                    currencySign = currency.symbol,
                    valueStyle = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.width(10.dp))

                if (descriptionNotEmpty) {
                    IconButton2(
                        onClick = { expanded = !expanded },
                        iconId = if (expanded) R.drawable.expand_less_icon else R.drawable.expand_more_icon
                    )
                } else {
                    IconButton2(
                        onClick = { onDelete(transaction.transaction) },
                        iconId = R.drawable.delete_icon
                    )
                }
            }
        }

        if (expanded) {
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = transaction.transaction.description)

                Spacer(modifier = Modifier.weight(1f))

                IconButton2(
                    onClick = { onDelete(transaction.transaction) },
                    iconId = R.drawable.delete_icon
                )
            }
        }
    }
}

@Composable
fun IconButton2(
    onClick: () -> Unit,
    iconId: Int
) {
    IconButton(onClick = { onClick() }) {
        Icon(
            painterResource(id = iconId),
            contentDescription = null
        )
    }
}