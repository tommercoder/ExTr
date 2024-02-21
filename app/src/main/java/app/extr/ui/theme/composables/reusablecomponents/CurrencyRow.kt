package app.extr.ui.theme.composables.reusablecomponents

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import app.extr.ui.theme.ExTrTheme

@Composable
fun CurrencyRow(
    amount: Double,
    currencySymbol: Char,
    numberStyle: TextStyle
) {
    // Create an annotated string with two different styles
    val text = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
            color = MaterialTheme.colorScheme.secondary,
            fontSize = numberStyle.fontSize / 1.7)
        ) {
            append(currencySymbol)
        }
        withStyle(style = SpanStyle(fontSize = numberStyle.fontSize)) {
            append("%.2f".format(amount))
        }
    }

    // Display the styled text
    Text(text = text)
}

@Preview
@Composable
fun preview(
) {
    val numberStyle = TextStyle(
        fontSize = 24.sp, // Adjust the size as needed
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Default
    )
  ExTrTheme {
      CurrencyRow(amount = 123.4, currencySymbol = '$', numberStyle = numberStyle)
  }
}