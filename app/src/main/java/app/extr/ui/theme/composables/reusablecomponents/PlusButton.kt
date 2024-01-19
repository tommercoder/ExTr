package app.extr.ui.theme.composables.reusablecomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PlusButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    size: Dp
) {
    val circleSize = size * 0.4f
    val iconSize = circleSize * 0.6f
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
    ) {
        Box(
            Modifier
                .size(circleSize)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Filled.Add,
                contentDescription = "Add",
                modifier = Modifier.size(iconSize),
                //tint = MaterialTheme.colorScheme.background
            )
        }

    }
}

@Preview
@Composable
fun PlusButtonPreview() {
    PlusButton(
        Modifier, {}, 180.dp
    )
}