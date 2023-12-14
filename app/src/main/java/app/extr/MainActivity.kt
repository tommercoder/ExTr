package app.extr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import app.extr.ui.theme.ExTrTheme
import app.extr.ui.theme.viewmodels.MoneyTypeViewModel
import app.extr.ui.theme.viewmodels.ViewModelsProvider
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import app.extr.utils.helpers.Res
import app.extr.utils.helpers.resproviders.MoneyTypeRes

//import app.extr.data.types.IconFromId


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExTrTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ExTrApp()


                }
            }
        }


    }
}

@Composable
fun ExTrApp() {

    //navcontroller here?
    //top bar
    //bottom bar
    Scaffold(
        topBar = {
            Text(text = "test")
        },
        bottomBar = {
            Text(text = "test")
        }
    ) { innerPadding ->
        val temp = innerPadding

        val viewmodeltest: MoneyTypeViewModel = viewModel(factory = ViewModelsProvider.Factory)
        val moneytypes = viewmodeltest.moneyTypes.observeAsState(initial = emptyList())
        var expanded by remember { mutableStateOf(true) }

        Box {
            Text("Choose Money Type", Modifier.clickable { expanded = true })
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                // Iterate over moneyTypes and create a menu item for each
                moneytypes.value.forEach { moneyType ->

                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                        },
                        text = { Text(moneyType.name) },
                        leadingIcon = {
                            Icon(
                                painterResource(
                                    id = MoneyTypeRes().getAttributesById(
                                        moneyType.iconId
                                    ).icon
                                ), contentDescription = null
                            )
                        },
                        modifier = Modifier.background(MoneyTypeRes().getAttributesById(moneyType.iconId).color)
                        )
                }
//                        leadingIcon = {Icon(
//                            painterResource(LocalContext.current.resources.getIdentifier(
//                            moneyType.iconName, "drawable", LocalContext.current.packageName)),
//                            contentDescription = null)}
//                        leadingIcon = {
//                            Icon(
//                                painterResource(id = IconFromId.asRes(moneyType.iconId)),
//                                contentDescription = null
//                            )
//                        }
//                        leadingIcon = {
//                            Icon(
//                                painterResource(
//                                    id = ExTrApplication.resourceProvider.getIconByname(
//                                        moneyType.iconName
//                                    )
//                                ),
//                                contentDescription = null
//                            )
//                        }

            }
        }
    }
}


@Preview
@Composable
fun preview() {
    ExTrApp()
}
