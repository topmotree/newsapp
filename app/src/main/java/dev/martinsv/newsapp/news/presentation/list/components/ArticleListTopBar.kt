package dev.martinsv.newsapp.news.presentation.list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.martinsv.newsapp.R
import dev.martinsv.newsapp.core.presentation.icons.IconClose
import dev.martinsv.newsapp.core.presentation.icons.IconSearch
import dev.martinsv.newsapp.core.presentation.icons.IconSearchOff
import dev.martinsv.newsapp.core.presentation.theme.NewsappTheme
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
                        stringResource(R.string.article_list_top_bar_title_top_headlines)
                    } else {
                        stringResource(R.string.article_list_top_bar_title_search)
                    }

                )
            },
            actions = {
                IconButton(
                    onClick = onSearchIconClick
                ) {
                    Icon(
                        imageVector = if (isSearchBarVisible) IconSearchOff else IconSearch,
                        contentDescription = if (isSearchBarVisible) stringResource(R.string.article_list_close_search_button_descritpion)
                        else stringResource(R.string.article_list_open_search_button_descritpion)
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
                    Text(stringResource(R.string.article_list_search_textfield_placeholder))
                },
                leadingIcon = {
                    Icon(
                        imageVector = IconSearch,
                        contentDescription = null,
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = { searchFieldState.clearText() }
                    ) {
                        Icon(
                            imageVector = IconClose,
                            contentDescription = stringResource(R.string.article_list_search_textfield_clear_button_description),
                        )
                    }
                },
                shape = RoundedCornerShape(32.dp),
                lineLimits = TextFieldLineLimits.SingleLine,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
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
private fun ArticleListTopBarPreview() {
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