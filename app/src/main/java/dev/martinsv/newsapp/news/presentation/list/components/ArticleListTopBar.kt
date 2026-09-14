package dev.martinsv.newsapp.news.presentation.list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.martinsv.newsapp.core.presentation.icons.IconSearch
import dev.martinsv.newsapp.core.presentation.icons.IconSearchOff
import dev.martinsv.newsapp.core.presentation.theme.NewsappTheme
import dev.martinsv.newsapp.core.presentation.utils.hs
import dev.martinsv.newsapp.news.presentation.list.paging.NewsType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleListTopBar(
    newsType: NewsType,
    onSearchIconClick: () -> Unit,
    isSearchBarVisible: Boolean,
    searchFieldState: TextFieldState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
    ) {
        TopAppBar(
            title = {
                Text(
                    if (newsType == NewsType.TopHeadlines) {
                        hs("Top headlines")
                    } else {
                        hs("Search")
                    }

                )
            },
            actions = {
                IconButton(
                    onClick = onSearchIconClick
                ) {
                    Icon(
                        imageVector = if (isSearchBarVisible) IconSearchOff else IconSearch,
                        contentDescription = null
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors()
                .copy(containerColor = MaterialTheme.colorScheme.background),
        )

        AnimatedVisibility(isSearchBarVisible) {
            OutlinedTextField(
                state = searchFieldState,
                placeholder = {
                    Text(hs("Search news"))
                },
                leadingIcon = {
                    Icon(
                        imageVector = IconSearch,
                        contentDescription = hs("Search icon")
                    )
                },
                shape = RoundedCornerShape(32.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun ArticleListTopBarPreview(){
    NewsappTheme {
        Column {
            ArticleListTopBar(
                newsType = NewsType.TopHeadlines,
                onSearchIconClick = {},
                isSearchBarVisible = false,
                searchFieldState = rememberTextFieldState()
            )

            ArticleListTopBar(
                newsType = NewsType.Everything("martin"),
                onSearchIconClick = {},
                isSearchBarVisible = true,
                searchFieldState = rememberTextFieldState("martin")
            )

        }
    }
}