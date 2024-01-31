package app.extr.ui.theme.composables.reusablecomponents

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import app.extr.R
import app.extr.ui.theme.AppPadding
import app.extr.ui.theme.ExTrTheme
import app.extr.ui.theme.shapeScheme

enum class SelectedButton {
    EXPENSES, INCOME
}

@Composable
fun ExpenseIncomeDateRow(
    modifier: Modifier = Modifier,
    onSelected: (SelectedButton) -> Unit,
    onDateClicked: () -> Unit
) {
    var selectedButton by remember { mutableStateOf(SelectedButton.EXPENSES) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = modifier
                //.height(IntrinsicSize.Min)
                .clip(MaterialTheme.shapeScheme.extraRoundedCorners)
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
                .padding(horizontal = 4.dp)
                .weight(0.6f),
            contentAlignment = Alignment.Center
        ) {
            Row {
                Button(
                    onClick = {
                        selectedButton = SelectedButton.EXPENSES
                        onSelected(SelectedButton.EXPENSES)
                    },
                    modifier = Modifier
                        .weight(1f)
                        //.height(48.dp)
                        .zIndex(if (selectedButton == SelectedButton.EXPENSES) 1f else 0f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =  if (selectedButton == SelectedButton.EXPENSES) MaterialTheme.colorScheme.primary else Color.Transparent
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.button_expenses),
                        color = if (selectedButton == SelectedButton.EXPENSES) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                    )
                }

                Button(
                    onClick = {
                        selectedButton = SelectedButton.INCOME
                        onSelected(SelectedButton.INCOME)
                    },
                    modifier = Modifier
                        .weight(1f)
                        //.offset(x = offset)
                        //.height(48.dp)
                        .zIndex(if (selectedButton == SelectedButton.EXPENSES) 1f else 0f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedButton == SelectedButton.INCOME) MaterialTheme.colorScheme.primary else Color.Transparent
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.button_income),
                        color = if (selectedButton == SelectedButton.INCOME) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(4.dp))
        Button(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(horizontal = 4.dp)
                .weight(0.4f),
            onClick = { onDateClicked() }
        ) {
            Text("01 Feb 24")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OverlappingSegmentedControlPreview() {
    ExTrTheme {
            ExpenseIncomeDateRow(
                modifier = Modifier,
                onSelected = {},
                onDateClicked = {}
            )
    }
}