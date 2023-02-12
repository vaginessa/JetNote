package com.example.grqph.settings_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.common_ui.AdaptingRowBetween
import com.example.common_ui.Cons.KEY_CLICK
import com.example.common_ui.MatColors
import com.example.common_ui.MatColors.Companion.ON_SURFACE
import com.example.common_ui.MatColors.Companion.SURFACE
import com.example.common_ui.SoundEffect
import com.example.datastore.DataStore
import com.example.grqph.navigation_drawer.NavigationDrawer
import com.example.grqph.top_action_bar.CustomTopAppBar
import kotlinx.coroutines.launch

private val getMatColor = MatColors().getMaterialColor

@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter",
    "UnusedMaterialScaffoldPaddingParameter"
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(
    navC: NavController
) {
    val ctx = LocalContext.current
    val dataStore = DataStore(ctx)
    val isDarkTheme = dataStore.isDarkTheme.collectAsState(false)
    val thereIsSoundEffect = dataStore.thereIsSoundEffect.collectAsState(false)

    val sound = SoundEffect()

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    val lazyListState = rememberLazyListState()
    val scaffoldState = rememberScaffoldState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
                        NavigationDrawer(
                            navController = navC,
                            drawerState = drawerState,
                            searchLabel = null,
                            searchTitle = null
                        )
        },
        modifier = Modifier.navigationBarsPadding()
    ){
        androidx.compose.material.Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = getMatColor(SURFACE),
            topBar = {
                CustomTopAppBar(
                    drawerState = drawerState,
                    topAppBarScrollBehavior = scrollBehavior,
                    title = "Settings"
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyListState
            ) {

                item {
                    PreferenceItem(
                        title = "Dark Mode",
                        description = "This application following the system mode by default.",
                        active = isDarkTheme.value
                    ) {
                        sound.makeSound.invoke(ctx, KEY_CLICK, thereIsSoundEffect.value)
                        scope.launch {
                            dataStore.saveTheme(!isDarkTheme.value)
                        }
                    }
                }

                item {
                    Divider(modifier = Modifier.padding(10.dp))
                }

                item {
                    PreferenceItem(
                        title = "Sound Effect",
                        description = "Make sound when any key is pressed.",
                        active = thereIsSoundEffect.value
                    ) {
                        scope.launch {
                            sound.makeSound.invoke(ctx, KEY_CLICK, thereIsSoundEffect.value)
                            dataStore.saveSoundEffect(!thereIsSoundEffect.value)
                        }
                    }
                }

                item {
                    Divider(modifier = Modifier.padding(10.dp))
                }

                item {
                    PreferenceItem(title = "Licenses", description = "Public repositories on GitHub are often used to share open source software.") {
                        sound.makeSound.invoke(ctx, KEY_CLICK, thereIsSoundEffect.value)
                        navC.navigate("licenses")
                    }
                }

            }
        }
    }
}

@Composable
private fun PreferenceItem(
    title: String,
    description: String? = null,
    active: Boolean? = null,
    onItemClick: () -> Unit = { },
) {
    Column(
        modifier = Modifier
            .clickable { onItemClick() }
            .padding(start = 32.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
    ) {
        AdaptingRowBetween(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                color = getMatColor(ON_SURFACE)
            )

            Canvas(
                modifier = Modifier
                    .size(10.dp)
            ) {
                drawArc(
                    color = if (active == true) Color.Green else Color.Transparent,
                    startAngle = 1f,
                    sweepAngle = 360f,
                    useCenter = true,
                    style = Fill
                )
            }
        }
        if (description != null) {
            Text(
                text = description,
                Modifier.alpha(.5f),
                color = getMatColor(ON_SURFACE)
            )
        }
    }
}