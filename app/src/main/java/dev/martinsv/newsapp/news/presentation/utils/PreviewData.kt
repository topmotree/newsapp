package dev.martinsv.newsapp.news.presentation.utils

import dev.martinsv.newsapp.news.presentation.model.ArticleUiModel

object PreviewData {
    val article1 = ArticleUiModel(
        sourceName = "The Verge",
        author = "Martins Vasiljevs",
        title = "Title short",
        description = "Description of the article.",
        url = "https://www.google.com/#q=tota",
        urlToImage = "https://thumb.wikimedia.org/wikipedia/commons/thumb/f/fa/Breil-Brigels._%28actm%29_02.jpg/1280px-Breil-Brigels._%28actm%29_02.jpg",
        publishedAtFormatted = "12 October 2023 12:12",
        content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere, ligula ut tincidunt bibendum, velit magna cursus massa, ullamcorper rhoncus erat ligula a lorem. Sed sodales nibh sit amet vulputate auctor. Integer metus augue, mollis vel scelerisque sit amet, egestas in est. Maecenas non mollis nulla. Nunc ut arcu tortor. Curabitur ornare dignissim augue. Quisque nec volutpat risus, sed consequat felis. Nulla elementum magna vitae imperdiet porta. Mauris velit quam, facilisis at purus ut, lobortis dapibus massa. Suspendisse potenti. Duis gravida dolor quis aliquet consequat. Fusce placerat dolor eu erat porta, vel vulputate diam venenatis. Donec id velit eget eros vulputate molestie. Cras ornare suscipit mauris, nec commodo neque."
    )

    val article2 = ArticleUiModel(
        sourceName = "Reuters",
        author = "Martins Vasiljevs",
        title = "Title long. Proin lacus erat, vehicula non pellentesque et, interdum ut magna. Fusce eu vulputate lectus. Morbi vehicula arcu risus, at fringilla elit sodales eget.",
        description = "Description long. Cras ornare pharetra tempus. Curabitur aliquet mollis mi, eget fermentum ante eleifend id.",
        url = "https://www.google.com/#q=tota",
        urlToImage = "https://thumb.wikimedia.org/wikipedia/commons/thumb/f/fa/Breil-Brigels._%28actm%29_02.jpg/1280px-Breil-Brigels._%28actm%29_02.jpg",
        publishedAtFormatted = "12 October 2023 12:12",
        content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere, ligula ut tincidunt bibendum, velit magna cursus massa, ullamcorper rhoncus erat ligula a lorem. Sed sodales nibh sit amet vulputate auctor. Integer metus augue, mollis vel scelerisque sit amet, egestas in est. Maecenas non mollis nulla. Nunc ut arcu tortor. Curabitur ornare dignissim augue. Quisque nec volutpat risus, sed consequat felis. Nulla elementum magna vitae imperdiet porta. Mauris velit quam, facilisis at purus ut, lobortis dapibus massa. Suspendisse potenti. Duis gravida dolor quis aliquet consequat. Fusce placerat dolor eu erat porta, vel vulputate diam venenatis. Donec id velit eget eros vulputate molestie. Cras ornare suscipit mauris, nec commodo neque."
    )
}