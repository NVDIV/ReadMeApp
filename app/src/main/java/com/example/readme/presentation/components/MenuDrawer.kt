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
import kotlinx.coroutines.launch

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
fun DrawerContent(modifier: Modifier = Modifier) {
    Text(
        modifier = Modifier.padding(10.dp),
        text = "ReadMe",
        color = Color.White,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(8.dp))

    NavigationDrawerItem(
        label = {
            Text(
                text = "Profile",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 20.sp,
            )
        },
        selected = false,
        onClick = {},
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color(0xFF7F85ED),
            selectedContainerColor = Color(0xFF7F85ED)
        )
    )

    Spacer(modifier = Modifier.height(4.dp))

    NavigationDrawerItem(
        label = {
            Text(
                text = "My Dictionary",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 20.sp,
            )
        },
        selected = false,
        onClick = {},
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color(0xFF7F85ED),
            selectedContainerColor = Color(0xFF7F85ED)
        )
    )

    Spacer(modifier = Modifier.height(4.dp))

    NavigationDrawerItem(
        label = {
            Text(
                modifier = Modifier.padding(top = 5.dp),
                text = "Start Lesson",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 20.sp
            )
        },
        selected = false,
        onClick = {},
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color(0xFF7F85ED),
            selectedContainerColor = Color(0xFF7F85ED)
        )
    )

    Spacer(modifier = Modifier.height(4.dp))

    NavigationDrawerItem(
        label = {
            Text(
                text = "Read Text",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 20.sp,
            )
        },
        selected = false,
        onClick = {},
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color(0xFF7F85ED),
            selectedContainerColor = Color(0xFF7F85ED)
        )
    )

    Spacer(modifier = Modifier.height(4.dp))
}