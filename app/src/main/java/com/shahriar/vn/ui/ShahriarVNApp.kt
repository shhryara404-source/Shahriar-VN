package com.shahriar.vn.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

private enum class Screen(val title: String) {
    Home("Home"), About("About"), Projects("Projects"), Interests("Interests"), Nemoris("Nemoris"), Ideas("Ideas"), Library("Library"), Contact("Contact")
}

@Composable
fun ShahriarVNApp(darkMode: Boolean, onToggleTheme: () -> Unit) {
    var screenName by rememberSaveable { mutableStateOf(Screen.Home.name) }
    var english by rememberSaveable { mutableStateOf(true) }
    val screen = Screen.valueOf(screenName)

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("SHAHRIAR VN", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, letterSpacing = MaterialTheme.typography.labelLarge.letterSpacing)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { english = !english }) { Icon(Icons.Outlined.Language, contentDescription = "Language") }
                    IconButton(onClick = onToggleTheme) { Icon(if (darkMode) Icons.Outlined.LightMode else Icons.Outlined.DarkMode, contentDescription = "Theme") }
                }
            }
        },
        bottomBar = {
            NavigationBar(modifier = Modifier.navigationBarsPadding()) {
                listOf(Screen.Home, Screen.About, Screen.Projects, Screen.Interests).forEach { item ->
                    NavigationBarItem(
                        selected = screen == item,
                        onClick = { screenName = item.name },
                        icon = { Text(item.title.take(1), fontWeight = FontWeight.Bold) },
                        label = { Text(item.title) }
                    )
                }
            }
        }
    ) { padding ->
        AnimatedContent(
            targetState = screen,
            modifier = Modifier.padding(padding).fillMaxSize(),
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "screen-transition"
        ) { target ->
            when (target) {
                Screen.Home -> HomeScreen(english) { screenName = Screen.Projects.name }
                Screen.About -> InfoScreen("About", "A personal identity space built around technology, science, philosophy, history, cinema, music and worldbuilding.")
                Screen.Projects -> ProjectsScreen { screenName = Screen.Nemoris.name }
                Screen.Interests -> InterestsScreen()
                Screen.Nemoris -> InfoScreen("Nemoris", "A living, industrial and dystopian universe designed for deep narrative, simulation, games and visual storytelling.")
                Screen.Ideas -> InfoScreen("Ideas", "A structured archive for concepts, experiments and future creations.")
                Screen.Library -> InfoScreen("Library", "Books, cinema, music and references that shape the creative landscape.")
                Screen.Contact -> InfoScreen("Contact", "A focused space for professional links and collaboration.")
            }
        }
    }
}

@Composable
private fun HomeScreen(english: Boolean, onProjects: () -> Unit) {
    val title = if (english) "Shaping ideas into worlds." else "ایده‌ها را به جهان تبدیل می‌کنم."
    val subtitle = if (english) "Technology · Science · Design · Worldbuilding" else "فناوری · علم · طراحی · جهان‌سازی"
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 20.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().height(410.dp).clip(RoundedCornerShape(30.dp)).background(
                Brush.linearGradient(listOf(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.surface, MaterialTheme.colorScheme.background))
            ),
            contentAlignment = Alignment.BottomStart
        ) {
            Column(modifier = Modifier.padding(26.dp)) {
                Text("SHAHRIAR", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.ExtraBold)
                Spacer(Modifier.height(8.dp))
                Text(title, style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.height(8.dp))
                Text(subtitle, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Text("A personal operating space", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Text("Explore the person, the work, the ideas and the worlds behind them.", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Card(onClick = onProjects, shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Row(modifier = Modifier.fillMaxWidth().padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) { Text("Explore projects", fontWeight = FontWeight.Bold); Text("Nemoris, Techaneh and more", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                Icon(Icons.Outlined.ArrowForward, contentDescription = null)
            }
        }
    }
}

@Composable
private fun ProjectsScreen(onNemoris: () -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text("Projects", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
        ProjectCard("Nemoris", "Worldbuilding, narrative systems and a living universe.", onNemoris)
        ProjectCard("Techaneh", "A technology-focused creative platform.", {})
    }
}

@Composable
private fun ProjectCard(title: String, body: String, onClick: () -> Unit) {
    Card(onClick = onClick, shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(Modifier.padding(22.dp)) { Text(title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold); Spacer(Modifier.height(8.dp)); Text(body, color = MaterialTheme.colorScheme.onSurfaceVariant) }
    }
}

@Composable
private fun InterestsScreen() {
    val interests = listOf("Physics", "Astronomy", "AI", "History", "Philosophy", "Cinema", "Music", "Literature", "Evolution", "Worldbuilding")
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Text("Interests", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) { items(interests.size) { i -> Surface(shape = RoundedCornerShape(50), color = MaterialTheme.colorScheme.surfaceVariant) { Text(interests[i], Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) } } }
        Text("Curiosity is the engine. Creation is the output.", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun InfoScreen(title: String, body: String) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(title, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
        Text(body, style = MaterialTheme.typography.headlineSmall)
        Text("This foundation is intentionally modular so the final content, imagery and motion system can be expanded without restructuring the Android app.", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
