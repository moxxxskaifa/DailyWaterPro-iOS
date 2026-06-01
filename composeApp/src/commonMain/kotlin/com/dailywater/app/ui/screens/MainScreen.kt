package com.dailywater.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dailywater.app.platform.HapticFeedback

val DAILY_GOAL = 2000
val QUICK_ML = listOf(100, 200, 250, 300, 500)

@Composable
fun MainScreen() {
    var totalMl by remember { mutableStateOf(0) }
    var customMl by remember { mutableStateOf("") }
    val haptic = remember { HapticFeedback() }
    val remaining = (DAILY_GOAL - totalMl).coerceAtLeast(0)
    val progress = (totalMl.toFloat() / DAILY_GOAL).coerceAtMost(1f)

    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text("Water Tracker", fontWeight = FontWeight.Bold) }) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(20.dp))
            Card(modifier = Modifier.size(180.dp), shape = CircleShape,
                 colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))) {
                Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text(totalMl.toString(), fontSize = 40.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Text("ml", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("of {DAILY_GOAL}ml", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Spacer(Modifier.height(16.dp))
            LinearProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                color = MaterialTheme.colorScheme.primary, trackColor = MaterialTheme.colorScheme.surfaceVariant)
            Spacer(Modifier.height(4.dp))
            Text("{remaining}ml remaining", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(24.dp))
            Text("Quick Add", fontWeight = FontWeight.SemiBold); Spacer(Modifier.height(12.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                QUICK_ML.forEach { ml ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Button(onClick = { totalMl += ml; haptic.light() }, modifier = Modifier.size(56.dp), shape = CircleShape,
                               contentPadding = PaddingValues(0.dp)) { Text(ml.toString(), fontSize = 13.sp, fontWeight = FontWeight.Bold) }
                        Text("ml", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = customMl, onValueChange = { customMl = it.filter { c -> c.isDigit() }.take(4) },
                    modifier = Modifier.width(120.dp), placeholder = { Text("Ml") }, singleLine = true)
                Button(onClick = { val ml = customMl.toIntOrNull() ?: return@Button; totalMl += ml; customMl = ""; haptic.light() }) { Text("Add") }
            }
        }
    }
}
