package com.example.bonprixchallenge

import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import coil.compose.rememberAsyncImagePainter
import com.example.bonprixchallenge.data.MainRepository
import com.example.bonprixchallenge.domain.Categories
import com.example.bonprixchallenge.interactors.MainViewModel
import com.example.bonprixchallenge.interactors.MyViewModelFactory
import com.example.bonprixchallenge.network.RetrofitService

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            val retrofitService = RetrofitService.getInstance()
            val mainRepository = MainRepository(retrofitService)
            viewModel = ViewModelProvider(this, MyViewModelFactory(mainRepository))[MainViewModel::class.java]

        setContent {
            viewModel.getAllLinks()
            MainContent(viewModel.categoryList)
        }
    }
}

@Composable
fun MainContent(listCategories: LiveData<Categories>, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("BonPrix Code Challenge", color = Color.White) }, backgroundColor = Color(0xff0f9d58)) },
        content = { MyContent(listCategories, modifier) }
    )
}

@Composable
fun MyContent(listCategories: LiveData<Categories>, modifier: Modifier = Modifier){
    val categoryList by listCategories.observeAsState()
    categoryList?.let {
        if (it.isEmpty()) {
            LoadingComponent()
        } else {
            ComponentList(it, modifier = modifier)
        }
    }
}

@Composable
fun LoadingComponent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {

        CircularProgressIndicator(modifier = Modifier.wrapContentWidth(CenterHorizontally))
    }
}

@Composable
fun ComponentList(categories: Categories, modifier: Modifier = Modifier) {
    LazyColumn(modifier = Modifier) {
        items(items = categories.categories) { category ->
            Column(modifier = Modifier) {
                category.label?.let { Text(it) }
                category.image?.let { Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = null,
                    modifier = Modifier.size(128.dp)
                ) }
                category.url?.let { url ->
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
                }) }

            }

        }
    }
}
    // Adding a WebView inside AndroidView
   /* // with layout as full screen
   */

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
      //  MainContent()
}