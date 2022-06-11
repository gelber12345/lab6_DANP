package com.example.lab6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.lab6.ui.theme.Lab6Theme
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.lab6.blood.BloodViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab6Theme {
                // A surface container using the 'background' color from the theme

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val viewmodel = ViewModelProvider(this).get(BloodViewModel::class.java)
                    BloodScreen(viewmodel)

                }
            }
        }
    }
    
}

@Composable
fun BloodScreen(viewmodel: BloodViewModel) {
    val lazyBlood = viewmodel.bloodFlow.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight() ,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        items(lazyBlood) { lazyBlood ->

            Card(elevation = 4.dp ,
                    modifier = Modifier.fillMaxWidth()
                ) {
                Text(
                    text = "ID : ${lazyBlood?.id} \n" +
                            "Grupo : ${lazyBlood?.group} \n" +
                            "RH factor : ${lazyBlood?.rh_factor} \n" +
                            "Tipo : ${lazyBlood?.type}",
                    fontSize = 20.sp,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(10.dp) )

        }
        val refreshState = lazyBlood.loadState.refresh
        if (refreshState is LoadState.Loading)
            item {
                Column(horizontalAlignment =  Alignment.CenterHorizontally) {
                    Text("Initial data fetch ...")
                    CircularProgressIndicator()
                }
            }
        else if (refreshState is LoadState.Error)
            item {
                Box(modifier = Modifier.fillParentMaxSize()) {
                    val error = refreshState.error

                    Text("Initial Data Error: ${error.localizedMessage}")
                    Button(onClick = { lazyBlood.retry() }) {
                        Text("Retry")
                    }
                }
            }

        val appendState = lazyBlood.loadState.append
        if (appendState is LoadState.Loading)
            item {
                Column(horizontalAlignment =  Alignment.CenterHorizontally) {
                    Text("Subsequent data fetch ...")
                    CircularProgressIndicator()
                }
            }
        else if (appendState is LoadState.Error)
            item {
                val error = appendState.error
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Subsequent data Error: ${error.localizedMessage}")
                    Button(onClick = { lazyBlood.retry() }) {
                        Text("Retry")
                    }
                }
            }
    }
}
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Lab6Theme {
        Greeting("Android")
    }
}
