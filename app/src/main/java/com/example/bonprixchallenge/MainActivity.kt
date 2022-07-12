package com.example.bonprixchallenge

import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberAsyncImagePainter
import com.example.bonprixchallenge.domain.Categories
import com.example.bonprixchallenge.domain.Category
import com.example.bonprixchallenge.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.closeApp.observe(this) {
            if (it) finish()
        }

        setContent {
            MainContent(viewModel)
        }
    }
}

@Composable
fun MainContent(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val listOfCategories by viewModel.categoryList.observeAsState()
    val urlToDisplay by viewModel.urlToDisplay.observeAsState()
    val labelToDisplay by viewModel.labelToDisplay.observeAsState()

    BackPressHandler(onBackPressed = { viewModel.navigateBack() })

    Scaffold(
        topBar = { TopAppBar(title = { Text("BonPrix Code Challenge", color = Color.White) }, backgroundColor = Color(0xff0f9d58)) },
        content = { Column{
            labelToDisplay?.let { label -> Text(label, fontSize = 20.sp) }
            listOfCategories?.let { it ->
                ComponentList(it, onCategoryChanged = { viewModel.navigateTo(it) }, modifier)
            } 
            urlToDisplay?.let {
                ShowWebView(it)
            }
        }}
    )
}


@Composable
fun ComponentList(categories: Categories, onCategoryChanged: (Category) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(modifier = Modifier) {
        items(items = categories.categories) { category ->
            MenuComposableItem(category, onClickMenu = { item ->
                run {
                    onCategoryChanged(item)
                }
            },  modifier = modifier)
        }
    }
}

@Composable
fun MenuComposableItem(
    category: Category,
    onClickMenu: (category: Category) -> Unit,
    modifier: Modifier = Modifier
){
    Column {
        TextButton(onClick = { onClickMenu(category) }) {
            Box {
                category.image?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    )
                }
                Text(category.label,
                    color = if (category.image == null)  Color.Black else Color.White,
                    fontSize = if (category.image == null)  16.sp else 30.sp,
                )
            }
        }
        Divider()
    }
}

@Composable
fun ShowWebView(url: String){
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    }, update = {
        it.loadUrl(url)
    })
}

@Composable
fun BackPressHandler(
    backPressedDispatcher: OnBackPressedDispatcher? =
        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressed: () -> Unit
) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }

    DisposableEffect(key1 = backPressedDispatcher) {
        backPressedDispatcher?.addCallback(backCallback)

        onDispose {
            backCallback.remove()
        }
    }
}
