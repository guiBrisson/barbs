package presentation.utils

import androidx.compose.foundation.lazy.LazyListState

fun LazyListState.isScrolledToEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

suspend fun scrollToTheBottom(itemListSize: Int, scrollState: LazyListState) {
    if (itemListSize > 0) {
        scrollState.animateScrollToItem(itemListSize - 1)
    }
}