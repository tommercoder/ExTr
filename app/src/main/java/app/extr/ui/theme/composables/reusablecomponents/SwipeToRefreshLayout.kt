package app.extr.ui.theme.composables.reusablecomponents

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToRefreshLayout(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
//    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)
//    SwipeRefresh(
//        state = swipeRefreshState,
//        onRefresh = { onRefresh() }
//    ) {
//
//    }

}