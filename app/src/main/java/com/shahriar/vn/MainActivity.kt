package com.shahriar.vn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.FolderOpen
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.runtime.CompositionLocalProvider
import com.shahriar.vn.ui.theme.ShahriarVNTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { ShahriarVNApp() }
    }
}

private enum class Lang { FA, EN }
private enum class Screen(val fa: String, val en: String, val icon: ImageVector) {
    Home("خانه", "Home", Icons.Outlined.Home),
    About("درباره من", "About", Icons.Outlined.Person),
    Skills("توانایی‌ها", "Skills", Icons.Outlined.Code),
    Projects("پروژه‌ها", "Projects", Icons.Outlined.WorkOutline),
    Nemoris("Nemoris", "Nemoris", Icons.Outlined.Public),
    Ideas("ایده‌ها", "Ideas", Icons.Outlined.Lightbulb),
    Library("کتابخانه", "Library", Icons.Outlined.MenuBook),
    Contact("ارتباط", "Contact", Icons.Outlined.MailOutline)
}

@Composable
private fun ShahriarVNApp() {
    var lang by rememberSaveable { mutableStateOf(Lang.FA) }
    var screen by rememberSaveable { mutableStateOf(Screen.Home) }
    val rtl = lang == Lang.FA

    CompositionLocalProvider(LocalLayoutDirection provides if (rtl) LayoutDirection.Rtl else LayoutDirection.Ltr) {
        ShahriarVNTheme {
            Scaffold(
                containerColor = Color(0xFF050609),
                topBar = {
                    TopAppBar(
                        modifier = Modifier.statusBarsPadding(),
                        title = {
                            Column {
                                Text("SHAHRIAR VN", fontSize = 16.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.8.sp)
                                Text(if (rtl) "هویت، ایده‌ها و جهان‌هایی که می‌سازم" else "Identity, ideas and worlds I build", fontSize = 10.sp, color = Color(0xFF9A9BA3))
                            }
                        },
                        actions = {
                            IconButton(onClick = { lang = if (rtl) Lang.EN else Lang.FA }) {
                                Text(if (rtl) "EN" else "فا", fontWeight = FontWeight.Bold, color = Color(0xFFF0C86F))
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent, titleContentColor = Color.White)
                    )
                },
                bottomBar = {
                    NavigationBar(containerColor = Color(0xFF0B0D12), modifier = Modifier.navigationBarsPadding()) {
                        listOf(Screen.Home, Screen.About, Screen.Projects, Screen.Nemoris, Screen.Contact).forEach { item ->
                            NavigationBarItem(
                                selected = screen == item,
                                onClick = { screen = item },
                                icon = { Icon(item.icon, contentDescription = null) },
                                label = { Text(if (rtl) item.fa else item.en, maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 10.sp) }
                            )
                        }
                    }
                }
            ) { pad ->
                AnimatedContent(
                    targetState = screen,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "screen-transition",
                    modifier = Modifier.padding(pad).fillMaxSize()
                ) { current ->
                    when (current) {
                        Screen.Home -> HomeScreen(rtl, { screen = Screen.About })
                        Screen.About -> AboutScreen(rtl)
                        Screen.Skills -> SkillsScreen(rtl)
                        Screen.Projects -> ProjectsScreen(rtl)
                        Screen.Nemoris -> NemorisScreen(rtl)
                        Screen.Ideas -> IdeasScreen(rtl)
                        Screen.Library -> LibraryScreen(rtl)
                        Screen.Contact -> ContactScreen(rtl)
                    }
                }
            }
        }
    }
}

@Composable
private fun ScrollPage(content: @Composable () -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 18.dp, vertical = 12.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) { content() }
}

@Composable
private fun HeroImage(modifier: Modifier = Modifier) {
    Box(modifier.clip(RoundedCornerShape(30.dp)).background(Color(0xFF11141B))) {
        Image(painterResource(R.drawable.hero_cartoon), null, Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
        Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color(0xB8050609)))))
    }
}

@Composable
private fun Portrait(modifier: Modifier = Modifier) {
    Image(painterResource(R.drawable.portrait_real), null, modifier.clip(CircleShape), contentScale = ContentScale.Crop)
}

@Composable
private fun HomeScreen(rtl: Boolean, onAbout: () -> Unit) {
    ScrollPage {
        Box(Modifier.fillMaxWidth().height(390.dp)) { HeroImage(Modifier.fillMaxSize())
            Column(Modifier.align(Alignment.BottomStart).padding(24.dp)) {
                Text("SHAHRIAR", color = Color(0xFFF0C86F), fontSize = 14.sp, fontWeight = FontWeight.Bold, letterSpacing = 3.sp)
                Text(if (rtl) "سازنده، کاوشگر، جهان‌ساز" else "Builder. Explorer. Worldbuilder.", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(Modifier.height(6.dp))
                Text(if (rtl) "از فیزیک و فناوری تا داستان، طراحی و جهان‌های مستقل." else "From physics and technology to story, design and independent worlds.", color = Color(0xFFD0D1D7), fontSize = 14.sp)
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            StatCard(if (rtl) "ریاضی–فیزیک" else "Math–Physics", if (rtl) "پایه فکری" else "Foundation", Modifier.weight(1f))
            StatCard("NEMORIS", if (rtl) "جهان در حال ساخت" else "World in progress", Modifier.weight(1f))
        }
        CardBlock(if (rtl) "یک معرفی کوتاه" else "A concise introduction") {
            Text(if (rtl) "من شیفته مرز میان علم، هنر و ساختن چیزهایی هستم که قبلاً وجود نداشته‌اند." else "I am drawn to the boundary between science, art, and building things that did not exist before.", color = Color(0xFFD6D6DC), lineHeight = 23.sp)
            FilledTonalButton(onClick = onAbout) { Text(if (rtl) "بیشتر درباره من" else "More about me") }
        }
    }
}

@Composable
private fun StatCard(value: String, label: String, modifier: Modifier = Modifier) {
    Surface(modifier, shape = RoundedCornerShape(22.dp), color = Color(0xFF0E1016), border = BorderStroke(1.dp, Color.White.copy(alpha = .08f))) {
        Column(Modifier.padding(16.dp)) { Text(value, color = Color(0xFFF0C86F), fontWeight = FontWeight.Bold, fontSize = 14.sp); Spacer(Modifier.height(4.dp)); Text(label, color = Color(0xFFA6A8B0), fontSize = 12.sp) }
    }
}

@Composable
private fun CardBlock(title: String, content: @Composable () -> Unit) {
    Surface(shape = RoundedCornerShape(26.dp), color = Color(0xFF0E1016), border = BorderStroke(1.dp, Color.White.copy(alpha = .08f))) {
        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) { Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold); content() }
    }
}

@Composable
private fun AboutScreen(rtl: Boolean) {
    ScrollPage {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Box(Modifier.size(108.dp).border(2.dp, Color(0xFFF0C86F).copy(alpha = .6f), CircleShape).padding(5.dp)) { Portrait(Modifier.fillMaxSize()) }
            Column { Text(if (rtl) "شهریار" else "Shahriar", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold); Text("SH A H R I A R  V N", color = Color(0xFFF0C86F), fontSize = 11.sp, letterSpacing = 2.sp); Text(if (rtl) "سازنده و جهان‌ساز" else "Builder & Worldbuilder", color = Color(0xFF9B9CA5), fontSize = 13.sp) }
        }
        CardBlock(if (rtl) "رویکرد" else "Approach") { Text(if (rtl) "کنجکاوی علمی، تفکر سیستمی، طراحی تجربه و ساخت جهان‌هایی که قابلیت رشد دارند." else "Scientific curiosity, systems thinking, experience design, and worlds designed to grow.", color = Color(0xFFD6D6DC), lineHeight = 23.sp) }
        CardBlock(if (rtl) "علاقه‌ها" else "Interests") { TagGrid(listOf(if (rtl) "فیزیک کوانتوم" else "Quantum Physics", if (rtl) "نجوم" else "Astronomy", if (rtl) "تاریخ" else "History", if (rtl) "فلسفه" else "Philosophy", "AI", if (rtl) "سینما" else "Cinema", if (rtl) "موسیقی" else "Music", if (rtl) "ادبیات" else "Literature")) }
    }
}

@Composable
private fun SkillsScreen(rtl: Boolean) {
    ScrollPage {
        PageHeader(if (rtl) "نقشه توانایی‌ها" else "Capability Map", if (rtl) "حوزه‌هایی که روی هم اثر می‌گذارند." else "Domains that influence one another.")
        listOf(
            if (rtl) "تفکر تحلیلی" else "Analytical thinking",
            if (rtl) "برنامه‌نویسی و فناوری" else "Programming & technology",
            if (rtl) "جهان‌سازی و طراحی سیستم" else "Worldbuilding & systems design",
            if (rtl) "فیزیک و نجوم" else "Physics & astronomy",
            if (rtl) "روایت، تئاتر و سینما" else "Narrative, theatre & cinema",
            if (rtl) "زبان و ادبیات" else "Language & literature"
        ).forEachIndexed { i, title -> CardBlock("0${i + 1}  $title") { Text(if (rtl) "یک مهارت منفرد نیست؛ بخشی از یک سیستم خلاق است." else "Not a standalone skill; part of a larger creative system.", color = Color(0xFFB9BBC3)) } }
    }
}

@Composable
private fun ProjectsScreen(rtl: Boolean) {
    ScrollPage {
        PageHeader(if (rtl) "پروژه‌ها" else "Projects", if (rtl) "چیزهایی که ایده را به محصول تبدیل می‌کنند." else "Where ideas become products.")
        CardBlock("NEMORIS") { Text(if (rtl) "یک جهان بزرگ، سرد، صنعتی و پویا با زمان، شکستگی‌ها، تمدن‌ها و داستان‌های چندلایه." else "A large, cold, industrial, dynamic universe shaped by time, fractures, civilizations, and layered stories.", color = Color(0xFFD0D1D7), lineHeight = 22.sp); TagGrid(listOf("World Engine", "NSE", "Nox", "Lore", "Simulation")) }
        CardBlock("TECHANEH") { Text(if (rtl) "پروژه فناوری و رسانه‌ای شخصی؛ جایی برای ساخت، آزمایش و انتشار." else "A personal technology and media project for building, experimenting, and publishing.", color = Color(0xFFD0D1D7), lineHeight = 22.sp); TagGrid(listOf("Technology", "AI", "Design", "Web")) }
    }
}

@Composable
private fun NemorisScreen(rtl: Boolean) {
    ScrollPage {
        PageHeader("NEMORIS", if (rtl) "جهانی که قرار نیست ثابت بماند." else "A world that refuses to stay still.")
        CardBlock("WORLD ENGINE") { Text(if (rtl) "قوانین جهان، زمان، تمدن‌ها، موجودات و شکستگی‌ها در یک سیستم قابل توسعه." else "Rules, time, civilizations, creatures, and fractures in an extensible system.", color = Color(0xFFD0D1D7), lineHeight = 22.sp); TagGrid(listOf("Time", "Civilizations", "Creatures", "Fractures")) }
        CardBlock("NOX") { Text(if (rtl) "یک موجود مستقل که می‌تواند نظم جهان را تغییر دهد؛ قدرتی که همیشه هزینه دارد." else "An independent entity capable of altering the world's order; power that always carries a cost.", color = Color(0xFFD0D1D7), lineHeight = 22.sp) }
        CardBlock("NSE") { Text(if (rtl) "مسیر آینده برای یک موتور شبیه‌سازی ساختاری که جهان را به یک سیستم زنده نزدیک می‌کند." else "A future structural simulation engine that moves the world toward a living system.", color = Color(0xFFD0D1D7), lineHeight = 22.sp) }
    }
}

@Composable
private fun IdeasScreen(rtl: Boolean) {
    ScrollPage { PageHeader(if (rtl) "آرشیو ایده‌ها" else "Idea Archive", if (rtl) "ایده‌ها را به نقاط قابل پیگیری تبدیل کن." else "Turn ideas into trackable concepts.")
        listOf("Quantum Narrative", "Dynamic World Systems", "AI-assisted Creation", "Interactive Identity").forEachIndexed { i, idea ->
            CardBlock("0${i + 1}  $idea") { Text(if (rtl) "ایده در حال توسعه و آماده برای تبدیل شدن به یک نمونه اولیه." else "An evolving concept ready to become a prototype.", color = Color(0xFFB9BBC3)) }
        }
    }
}

@Composable
private fun LibraryScreen(rtl: Boolean) {
    ScrollPage { PageHeader(if (rtl) "کتابخانه ذهنی" else "Mental Library", if (rtl) "کتاب، فیلم، موسیقی و دانشی که روی نگاه من اثر می‌گذارد." else "Books, films, music, and knowledge that shape how I see.")
        TagGrid(listOf(if (rtl) "تاریخ ایران" else "Iranian History", if (rtl) "فلسفه" else "Philosophy", if (rtl) "علم" else "Science", if (rtl) "ادبیات" else "Literature", if (rtl) "سینما" else "Cinema", if (rtl) "موسیقی" else "Music", "Édito", if (rtl) "زبان فرانسه" else "French"))
        CardBlock(if (rtl) "اصل کتابخانه" else "Library principle") { Text(if (rtl) "هر اثر خوب باید یا یک سؤال تازه بسازد، یا یک زاویه دید تازه بدهد." else "A good work should create a new question or offer a new angle of view.", color = Color(0xFFD0D1D7), lineHeight = 22.sp) }
    }
}

@Composable
private fun ContactScreen(rtl: Boolean) {
    ScrollPage { PageHeader(if (rtl) "ارتباط" else "Contact", if (rtl) "برای همکاری، پروژه یا گفت‌وگوی جدی." else "For collaboration, projects, or serious conversations.")
        CardBlock(if (rtl) "پروفایل حرفه‌ای" else "Professional profile") { TagGrid(listOf("GitHub", "Techaneh", "Nemoris", if (rtl) "توسعه محصول" else "Product building")) }
        CardBlock(if (rtl) "مرحله بعد" else "Next step") { Text(if (rtl) "این پروژه برای build خودکار APK از طریق GitHub Actions آماده شده است." else "This project is prepared for automated APK builds through GitHub Actions.", color = Color(0xFFD0D1D7), lineHeight = 22.sp) }
    }
}

@Composable
private fun PageHeader(title: String, subtitle: String) { Column { Text(title, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold); Spacer(Modifier.height(5.dp)); Text(subtitle, color = Color(0xFF9FA1A9), lineHeight = 20.sp) } }

@Composable
private fun TagGrid(items: List<String>) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) { items.forEach { item -> Surface(shape = RoundedCornerShape(999.dp), color = Color(0xFF151820), border = BorderStroke(1.dp, Color.White.copy(alpha = .08f))) { Text(item, Modifier.padding(horizontal = 13.dp, vertical = 8.dp), color = Color(0xFFD8D9DE), fontSize = 12.sp) } } }
}
