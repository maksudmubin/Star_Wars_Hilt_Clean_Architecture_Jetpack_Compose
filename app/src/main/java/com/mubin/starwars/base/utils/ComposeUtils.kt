package com.mubin.starwars.base.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * Handler to make any lazy column (or lazy row) infinite. Will notify the [onLoadMore]
 * callback once needed
 * @param listState state of the list that needs to also be passed to the LazyColumn composable.
 * Get it by calling rememberLazyListState()
 * @param buffer the number of items before the end of the list to call the onLoadMore callback
 * @param onLoadMore will notify when we need to load more
 *
 * REF: https://gist.github.com/luismierez/ae5a4f6c84682d6184533ad4587942e2#file-infinitelisthandler-kt
 */
@Composable
fun InfiniteListHandler(
    listState: LazyListState,
    buffer: Int = 2,
    onLoadMore: () -> Unit
) {
    val isLoading = remember { mutableStateOf(false) }
    val loadMore = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

            lastVisibleItemIndex > (totalItemsNumber - buffer)
        }
    }

    LaunchedEffect(loadMore) {
        snapshotFlow { loadMore.value }
            .distinctUntilChanged()
            .collect { shouldLoadMore ->
                if (shouldLoadMore && !isLoading.value) {
                    isLoading.value = true
                    onLoadMore()
                    isLoading.value = false
                }
            }
    }
}

@Composable
fun CustomAlertDialog(
    shouldShowDialog: MutableState<Boolean>,
    title: String,
    text: String,
    positiveButtonTitle: String
) {

    if (shouldShowDialog.value) {
        AlertDialog(
            onDismissRequest = {
                shouldShowDialog.value = false
            },
            title = {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            },
            text = {
                Text(
                    text = text,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        shouldShowDialog.value = false
                    },
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline
                    )
                ) {
                    Text(
                        text = positiveButtonTitle,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        )
    }

}