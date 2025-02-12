package com.example.readme.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.readme.presentation.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun DrawerLayout(
    navController: NavHostController,
    content: @Composable (Modifier) -> Unit // Pass padding modifier
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(250.dp),
                drawerContainerColor = Color(0xFF7F85ED)
            ) {
                DrawerContent(navController)
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    onOpenDrawer = {
                        scope.launch {
                            if (drawerState.isClosed) drawerState.open() else drawerState.close()
                        }
                    }
                )
            }
        ) { padding ->
            content(Modifier.padding(padding)) // Pass padding to screen content
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onOpenDrawer: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            Icon(
                imageVector = Icons.Rounded.Menu,
                contentDescription = "Menu",
                tint = Color.White,
                modifier = Modifier
                    .padding(20.dp)
                    .size(30.dp)
                    .clickable {
                        onOpenDrawer()
                    }
            )
        },
        title = {
            Text(
                text = "ReadMe",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        },
        colors = topAppBarColors(
            containerColor = Color(0xFF7F85ED)
        )
    )
}

@Composable
fun DrawerContent(navController: NavHostController) {
    Text(
        modifier = Modifier.padding(10.dp),
        text = "ReadMe",
        color = Color.White,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(8.dp))

    NavigationDrawerItem(
        label = { Text("Main", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White) },
        selected = false,
        onClick = { navController.navigate(Screen.Main.route) },
        colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color(0xFF7F85ED))
    )

    Spacer(modifier = Modifier.height(8.dp))

    NavigationDrawerItem(
        label = { Text("Profile", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White) },
        selected = false,
        onClick = { navController.navigate(Screen.Profile.route) },
        colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color(0xFF7F85ED))
    )

    Spacer(modifier = Modifier.height(4.dp))

    NavigationDrawerItem(
        label = { Text("My Dictionary", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White) },
        selected = false,
        onClick = { navController.navigate(Screen.Dictionary.route) },
        colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color(0xFF7F85ED))
    )

    Spacer(modifier = Modifier.height(4.dp))

    NavigationDrawerItem(
        label = { Text("Start Lesson", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White) },
        selected = false,
        onClick = { navController.navigate(Screen.Lesson.route) },
        colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color(0xFF7F85ED))
    )

    Spacer(modifier = Modifier.height(4.dp))

    NavigationDrawerItem(
        label = { Text("Read Text", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White) },
        selected = false,
        onClick = { navController.navigate(Screen.ReadText.route) },
        colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color(0xFF7F85ED))
    )

    Spacer(modifier = Modifier.height(4.dp))
}
