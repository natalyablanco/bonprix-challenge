package com.example.bonprixchallenge

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.bonprixchallenge.data.MainRepository
import com.example.bonprixchallenge.domain.Categories
import com.example.bonprixchallenge.domain.Category
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
fun MyContent(
    listCategories: LiveData<Categories>,
    modifier: Modifier = Modifier
){
    val categoryList by listCategories.observeAsState()
    var label by rememberSaveable { mutableStateOf("") }

    categoryList?.let { it ->
        if (it.isEmpty()) {
            LoadingComponent()
        } else {
            Column {
                Text(label)
                ComponentList(onNameChange = { label = it }, it, modifier = modifier)
            }
        }
    }
}

@Composable
fun ComponentList(onNameChange: (String) -> Unit, categories: Categories, modifier: Modifier = Modifier) {
    val viewModel: MainViewModel = viewModel()
    LazyColumn(modifier = Modifier) {
        items(items = categories.categories) { category ->
            MenuComposableItem(category, onClickMenu = { item ->
                run {
                    onNameChange(item.label)
                    item.children?.let { viewModel.changeCategoryList(it) }
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
    TextButton(onClick = { onClickMenu(category) }) {
        Column(modifier = modifier) {
            Box {
                category.image?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(200.dp)
                    )
                }
                Text(category.label, color = Color.Black)
            }
            Divider()
//            category.url?.let { url ->
//                if(category.children.isNullOrEmpty()){
//                    AndroidView(factory = {
//                        WebView(it).apply {
//                            layoutParams = ViewGroup.LayoutParams(
//                                ViewGroup.LayoutParams.MATCH_PARENT,
//                                ViewGroup.LayoutParams.MATCH_PARENT
//                            )
//                            webViewClient = WebViewClient()
//                            loadUrl(url)
//                        }
//                    }, update = {
//                        it.loadUrl(url)
//                    }) }
//            }
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
      //  MainContent()
}