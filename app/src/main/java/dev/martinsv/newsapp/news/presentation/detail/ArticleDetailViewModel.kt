package dev.martinsv.newsapp.news.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.martinsv.newsapp.core.presentation.navigation.ArticleDetailRoute
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val argument: ArticleDetailArgument =
        savedStateHandle.toRoute<ArticleDetailRoute>(typeMap = articleDetailTypeMap).argument

    val article = argument.article

    val eventChannel = Channel<ArticleDetailEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onReadFullArticleClick() {
        val url = article.url ?: return
        viewModelScope.launch { eventChannel.send(ArticleDetailEvent.OpenUrl(url)) }
    }
}