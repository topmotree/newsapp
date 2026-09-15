package dev.martinsv.newsapp.news.presentation.list.paging

import androidx.paging.PagingConfig
import dev.martinsv.newsapp.news.domain.DEFAULT_PAGE_SIZE

const val defaultPagingLoadSize = DEFAULT_PAGE_SIZE * 3

val defaultPagingConfig = PagingConfig(
    pageSize = DEFAULT_PAGE_SIZE,
    initialLoadSize = defaultPagingLoadSize,
    enablePlaceholders = true,
)