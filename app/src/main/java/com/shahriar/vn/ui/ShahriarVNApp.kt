package com.shahriar.vn.ui

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private enum class Lang { EN, FA }
private enum class Screen { HOME, ABOUT, PROJECTS, INTERESTS, NEMORIS, IDEAS, LIBRARY, CONTACT }

@Composable
fun ShahriarVNApp(darkMode: Boolean, onToggleTheme: () -> Unit) {
    var lang by rememberSaveable { mutableStateOf(Lang.EN) }
    var screen by rememberSaveable { mutableStateOf(Screen.HOME) }
    val rtl = lang == Lang.FA
    CompositionLocalProvider(LocalLayoutDirection provides if (rtl) LayoutDirection.Rtl else LayoutDirection.Ltr) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar = { Header(rtl, darkMode, { lang = if (rtl) Lang.EN else Lang.FA }, onToggleTheme) },
            bottomBar = { BottomNav(screen, rtl) { screen = it } }
        ) { p ->
            AnimatedContent(
                targetState = screen,
                modifier = Modifier.padding(p).fillMaxSize(),
                transitionSpec = { (fadeIn() + slideInHorizontally { it / 14 }) togetherWith (fadeOut() + slideOutHorizontally { -it / 20 }) },
                label = "screen"
            ) { s ->
                when (s) {
                    Screen.HOME -> Home(rtl) { screen = Screen.PROJECTS }
                    Screen.ABOUT -> About(rtl)
                    Screen.PROJECTS -> Projects(rtl) { screen = Screen.NEMORIS }
                    Screen.INTERESTS -> Interests(rtl)
                    Screen.NEMORIS -> Nemoris(rtl)
                    Screen.IDEAS -> Info(if (rtl) "ایده‌ها" else "Ideas", if (rtl) "آرشیوی زنده برای مفاهیم و تجربه‌های آینده." else "A living archive for concepts and future creations.")
                    Screen.LIBRARY -> Info(if (rtl) "کتابخانه" else "Library", if (rtl) "کتاب، سینما، موسیقی و منابع الهام." else "Books, cinema, music and references.")
                    Screen.CONTACT -> Info(if (rtl) "ارتباط" else "Contact", if (rtl) "برای همکاری و پروژه‌های تازه." else "For collaboration and new projects.")
                }
            }
        }
    }
}

@Composable
private fun Header(rtl: Boolean, dark: Boolean, toggleLang: () -> Unit, toggleTheme: () -> Unit) {
    Row(Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Column {
            Text("SHAHRIAR VN", fontWeight = FontWeight.ExtraBold, letterSpacing = 2.sp)
            Text(if (rtl) "ایده‌ها را به جهان تبدیل می‌کنم" else "Shaping ideas into worlds", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextButton(toggleLang) { Text(if (rtl) "EN" else "فا", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold) }
            IconButton(toggleTheme) { Icon(if (dark) Icons.Outlined.LightMode else Icons.Outlined.DarkMode, null) }
        }
    }
}

@Composable
private fun BottomNav(screen: Screen, rtl: Boolean, go: (Screen) -> Unit) {
    NavigationBar(Modifier.navigationBarsPadding()) {
        listOf(Screen.HOME, Screen.ABOUT, Screen.PROJECTS, Screen.NEMORIS).forEach { s ->
            NavigationBarItem(
                selected = screen == s,
                onClick = { go(s) },
                icon = { Icon(icon(s), null) },
                label = { Text(nav(s, rtl), fontSize = 10.sp) }
            )
        }
    }
}

private fun icon(s: Screen) = when (s) {
    Screen.HOME -> Icons.Outlined.Home
    Screen.ABOUT -> Icons.Outlined.Person
    Screen.PROJECTS -> Icons.Outlined.WorkOutline
    Screen.NEMORIS -> Icons.Outlined.Public
    else -> Icons.Outlined.Menu
}

private fun nav(s: Screen, rtl: Boolean) = when (s) {
    Screen.HOME -> if (rtl) "خانه" else "Home"
    Screen.ABOUT -> if (rtl) "من" else "About"
    Screen.PROJECTS -> if (rtl) "پروژه‌ها" else "Projects"
    else -> "Nemoris"
}

@Composable
private fun AssetImage(assetName: String, modifier: Modifier, contentScale: ContentScale) {
    val context = LocalContext.current
    val bitmap = remember(assetName) {
        runCatching {
            val encoded = context.assets.open(assetName).bufferedReader().use { it.readText() }
            val bytes = Base64.decode(encoded, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }.getOrNull()
    }
    bitmap?.let { Image(it.asImageBitmap(), contentDescription = null, modifier = modifier, contentScale = contentScale) }
}

@Composable
private fun Home(rtl: Boolean, openProjects: () -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp), verticalArrangement = Arrangement.spacedBy(18.dp)) {
        Box(Modifier.fillMaxWidth().height(510.dp).clip(RoundedCornerShape(32.dp)).border(BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(.38f)), RoundedCornerShape(32.dp))) {
            AssetImage("hero_cartoon.webp.b64", Modifier.fillMaxSize(), ContentScale.Crop)
            Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color.Transparent, MaterialTheme.colorScheme.background.copy(.98f)))))
            Column(Modifier.align(Alignment.BottomStart).padding(24.dp)) {
                Text("SHAHRIAR", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, letterSpacing = 3.sp)
                Text("VN", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.ExtraBold)
                Text(if (rtl) "سازنده، کاوشگر، جهان‌ساز" else "Builder. Explorer. Worldbuilder.", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))
                Text(if (rtl) "علم · فناوری · طراحی · داستان · جهان‌سازی" else "Science · Technology · Design · Story · Worldbuilding", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(16.dp))
                Button(openProjects, shape = RoundedCornerShape(16.dp)) { Text(if (rtl) "ورود به جهان من" else "Enter my world") }
            }
        }
        Label(if (rtl) "محورهای من" else "MY DOMAINS")
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(if (rtl) listOf("فیزیک", "نجوم", "هوش مصنوعی", "فلسفه", "تاریخ", "سینما", "موسیقی", "ادبیات") else listOf("Physics", "Astronomy", "AI", "Philosophy", "History", "Cinema", "Music", "Literature")) { Chip(it) }
        }
        Feature(if (rtl) "جهان‌هایی که می‌سازم" else "Worlds I build", if (rtl) "Nemoris و پروژه‌هایی برای تبدیل ایده به تجربه." else "Nemoris and projects that turn ideas into experiences.", openProjects)
    }
}

@Composable
private fun About(rtl: Boolean) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Label(if (rtl) "هویت" else "IDENTITY")
        AssetImage("portrait_real.webp.b64", Modifier.fillMaxWidth().height(370.dp).clip(RoundedCornerShape(30.dp)), ContentScale.Crop)
        Text("SHAHRIAR", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.ExtraBold)
        Text(if (rtl) "متفکر. پژوهشگر. خالق." else "Thinker. Researcher. Creator.", color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.titleLarge)
        Text(if (rtl) "کنجکاوی برای من موتور ساختن است؛ مسیرم میان ریاضی، فیزیک، فناوری، تاریخ، فلسفه، سینما، موسیقی و جهان‌سازی حرکت می‌کند." else "Curiosity is my engine for making; my path moves between mathematics, physics, technology, history, philosophy, cinema, music and worldbuilding.", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun Projects(rtl: Boolean, openNemoris: () -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Label(if (rtl) "پروژه‌های منتخب" else "SELECTED WORK")
        Feature("Nemoris", if (rtl) "جهانی زنده، سرد، صنعتی و دیستوپیایی برای روایت و بازی." else "A living cold industrial dystopia for narrative and games.", openNemoris)
        Feature("Techaneh", if (rtl) "پروژه‌ای تکنولوژی‌محور برای ساخت و انتشار ایده‌ها." else "A technology-focused platform for building and publishing ideas.", {})
    }
}

@Composable
private fun Interests(rtl: Boolean) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Label(if (rtl) "نقشه کنجکاوی" else "CURIOSITY MAP")
        Text(if (rtl) "کنجکاوی موتور است؛ خلق، خروجی آن." else "Curiosity is the engine. Creation is the output.", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
        listOf("Quantum Physics", "Astronomy", "Evolution", "AI", "Iranian History", "Philosophy", "Cinema", "Music", "Literature", "Worldbuilding").forEach { Chip(if (rtl) mapFa(it) else it) }
    }
}

@Composable
private fun Nemoris(rtl: Boolean) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Label("NEMORIS")
        Box(Modifier.fillMaxWidth().height(330.dp).clip(RoundedCornerShape(30.dp)).background(Brush.radialGradient(listOf(Color(0xFF173B50), MaterialTheme.colorScheme.background)))) {
            Column(Modifier.align(Alignment.BottomStart).padding(24.dp)) {
                Text("NEMORIS", style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.ExtraBold)
                Text(if (rtl) "جهانی زاده‌شده از شکستگی زمان و فضا." else "A world born from fractures in time and space.")
            }
        }
        Text(if (rtl) "واقع‌گرا، سرد، صنعتی، فرسوده و دائماً در حال تغییر." else "Realistic, cold, industrial, worn and constantly changing.", style = MaterialTheme.typography.headlineSmall)
        Text(if (rtl) "برای بازی، فیلم، انیمیشن و تجربه‌های تعاملی." else "Designed for games, film, animation and interactive experiences.", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun Info(title: String, body: String) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Label(title.uppercase())
        Text(title, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
        Text(body, style = MaterialTheme.typography.headlineSmall)
        Text("Shahriar VN is a living personal identity system.", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun Label(t: String) {
    Text(t, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
}

@Composable
private fun Chip(t: String) {
    Surface(shape = RoundedCornerShape(50), color = MaterialTheme.colorScheme.surfaceVariant, border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(.25f))) {
        Text(t, Modifier.padding(horizontal = 14.dp, vertical = 9.dp), fontSize = 12.sp)
    }
}

@Composable
private fun Feature(title: String, body: String, click: () -> Unit) {
    Card(
        onClick = click,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(.25f))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(5.dp))
                Text(body, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Icon(Icons.Outlined.ArrowForward, null, tint = MaterialTheme.colorScheme.primary)
        }
    }
}

private fun mapFa(s: String) = mapOf(
    "Quantum Physics" to "فیزیک کوانتوم",
    "Astronomy" to "نجوم",
    "Evolution" to "تکامل",
    "AI" to "هوش مصنوعی",
    "Iranian History" to "تاریخ ایران",
    "Philosophy" to "فلسفه",
    "Cinema" to "سینما",
    "Music" to "موسیقی",
    "Literature" to "ادبیات",
    "Worldbuilding" to "جهان‌سازی"
)[s] ?: s
