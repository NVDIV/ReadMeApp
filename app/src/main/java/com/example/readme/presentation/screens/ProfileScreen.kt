package com.example.readme.presentation.screens

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.readme.broadcast.Notification
import com.example.readme.broadcast.channelID
import com.example.readme.presentation.components.ActionButton
import com.example.readme.presentation.components.DrawerLayout
import com.example.readme.presentation.viewmodels.AuthViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    var userData by remember { mutableStateOf<Map<String, Any>?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            userData = viewModel.getUserProfile()
            isLoading = false
        }
    }

    DrawerLayout(navController) { modifier ->
        ProfileScreenContent(
            modifier,
            userData,
            isLoading,
            onSignOut = {
                viewModel.signOut()
                navController.navigate("auth_screen") {
                    popUpTo("profile_screen") { inclusive = true }
                }
            }
        )
    }
}

@Composable
fun ProfileScreenContent(
    modifier: Modifier,
    userData: Map<String, Any>?,
    isLoading: Boolean,
    onSignOut: () -> Unit
) {
    val context = LocalContext.current
    val timePickerDialog = remember { mutableStateOf(false) }
    val selectedTime = remember { mutableStateOf<Pair<Int, Int>?>(null) }

    // Create Notification Channel
    LaunchedEffect(Unit) {
        createNotificationChannel(context)
    }

    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Profile", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))

        if (isLoading) {
            Text("Loading...")
        } else {
            userData?.let {
                Text("Email: ${it["email"] ?: "Unknown"}")
                Text("Level: ${it["level"] ?: "Unknown"}")
                Text("Interests:")
                (it["interests"] as? List<*>)?.forEach { interest ->
                    Text("- $interest")
                }
            } ?: Text("Failed to load profile data.")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Button to Set Notification
        ActionButton(text = "Set Daily Notification", onClick = { timePickerDialog.value = true })

        selectedTime.value?.let { (hour, minute) ->
            Text("Notification set for: %02d:%02d".format(hour, minute))
        }

        if (timePickerDialog.value) {
            TimePickerDialog(
                context = context,
                onTimeSelected = { hour, minute ->
                    selectedTime.value = Pair(hour, minute)
                    scheduleNotification(context, hour, minute)
                    Toast.makeText(context, "Notification set for $hour:$minute", Toast.LENGTH_SHORT).show()
                }
            )
            timePickerDialog.value = false
        }

        Spacer(modifier = Modifier.height(16.dp))

        ActionButton("Sign Out", onClick = onSignOut)
    }
}

// Notification Scheduling
@SuppressLint("ServiceCast", "ScheduleExactAlarm")
private fun scheduleNotification(context: Context, hour: Int, minute: Int) {
    val intent = Intent(context, Notification::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
    }

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        pendingIntent
    )
}

// Notification Channel
private fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Daily Notification"
        val descriptionText = "Daily reminders"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
}

// TimePicker Dialog
fun TimePickerDialog(context: Context, onTimeSelected: (Int, Int) -> Unit) {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    android.app.TimePickerDialog(context, { _, selectedHour, selectedMinute ->
        onTimeSelected(selectedHour, selectedMinute)
    }, hour, minute, true).show()
}
