package net.yuuzu.diaryapp_composemultiplatform.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.EditCalendar
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.ReadMore
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.calf.picker.toImageBitmap
import kotlinx.coroutines.launch
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel
import net.yuuzu.diaryapp_composemultiplatform.common.ImageType
import net.yuuzu.diaryapp_composemultiplatform.common.navigation.Screen
import net.yuuzu.diaryapp_composemultiplatform.data.source.local.model.Diary
import net.yuuzu.diaryapp_composemultiplatform.presentation.main.components.CustomIcon
import net.yuuzu.diaryapp_composemultiplatform.presentation.main.components.IconVertical
import net.yuuzu.diaryapp_composemultiplatform.presentation.main.components.InteractionBlocker
import net.yuuzu.diaryapp_composemultiplatform.presentation.main.components.SectionWithHeader
import net.yuuzu.diaryapp_composemultiplatform.presentation.main.components.ShortcutButton
import net.yuuzu.diaryapp_composemultiplatform.presentation.main.components.TopHeader
import net.yuuzu.diaryapp_composemultiplatform.utils.toDateTimeString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navigator: Navigator) {
    val coroutineScope = rememberCoroutineScope()
    val bottomScaffoldState = rememberBottomSheetScaffoldState()

    val viewModel = viewModel(MainViewModel::class) { MainViewModel() }
    val state by viewModel.state.collectAsStateWithLifecycle()

    BottomSheetScaffold(
        scaffoldState = bottomScaffoldState,
        topBar = {
            TopHeader(
                name = "Yuuzu",
                description = "Cloudy, 25°C",
                profileImage = null,
                onProfileClicked = {},
                options = {
                    CustomIcon(
                        imageType = ImageType.VectorType(Icons.Rounded.Settings),
                        size = 24.dp,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        background = MaterialTheme.colorScheme.inversePrimary.copy(0.4f),
                        onClick = {}
                    )
                }
            )
        },
        sheetContent = {
            HomeSheetContent(state, navigator, viewModel)
        },
        sheetSwipeEnabled = true,
        sheetPeekHeight = 300.dp,
        sheetContainerColor = MaterialTheme.colorScheme.background,
        sheetContentColor = MaterialTheme.colorScheme.onBackground,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Current Total${if (state.diaries.size > 1) " Diaries" else " Diary"}:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = "${state.diaries.size}",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                ShortcutButton(
                    text = "New Diary",
                    icon = Icons.Rounded.Add,
                    iconTint = MaterialTheme.colorScheme.primary,
                    background = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    modifier = Modifier.weight(1f)
                ) { navigator.navigate(route = Screen.DetailScreen.route.plus("/0")) }
                ShortcutButton(
                    text = "Calendar",
                    icon = Icons.Rounded.EditCalendar,
                    iconTint = MaterialTheme.colorScheme.tertiary,
                    background = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f),
                    modifier = Modifier.weight(1f)
                ) {
                    // TODO: Calendar
                }
                ShortcutButton(
                    text = "Show All",
                    icon = Icons.Rounded.ReadMore,
                    iconTint = MaterialTheme.colorScheme.tertiary,
                    background = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f),
                    modifier = Modifier.weight(1f)
                ) {
                    coroutineScope.launch {
                        bottomScaffoldState.bottomSheetState.show()
                    }
                }
            }
            // Recently added
            RecentlyActivity(
                state = state,
                navigator = navigator,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun HomeSheetContent(
    state: MainState,
    navigator: Navigator,
    viewModel: MainViewModel
) {
    InteractionBlocker(
        blockCondition = false,
        shouldShowLoadingIndicator = true,
        shouldShowAlphaEffect = true,
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                TagFilter(
                    state = state,
                    viewModel = viewModel,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            items(
                items = state.diaries,
                key = { item -> item.id!! }
            ) { diary ->
                DiaryItem(diary = diary, navigator = navigator)
            }
        }
    }
}

@Composable
fun TagFilter(
    state: MainState,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(
            count = state.tags.size,
            key = { index -> state.tags[index] }
        ) {
            val currentTag = state.tags[it]
            val isSelectedTag = state.selectedTag == currentTag
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        shape = MaterialTheme.shapes.small,
                        color = if (isSelectedTag) Color.Transparent
                        else MaterialTheme.colorScheme.primary
                    )
                    .clip(MaterialTheme.shapes.medium)
                    .background(
                        if (isSelectedTag) MaterialTheme.colorScheme.primary
                        else Color.Transparent
                    )
                    .clickable { viewModel.onEvent(MainEvent.OnTagSelected(currentTag)) }
                    .padding(24.dp, 12.dp)
            ) {
                Text(
                    text = currentTag,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = if (isSelectedTag) MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun DiaryItem(
    diary: Diary,
    navigator: Navigator
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navigator.navigate(Screen.DetailScreen.route.plus("/${diary.id}")) }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        if (diary.imageBytes != null) {
            CustomIcon(
                imageType = ImageType.BitmapType(diary.imageBytes.toImageBitmap()),
                size = 50.dp,
                background = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            ) {}
        } else {
            CustomIcon(
                imageType = ImageType.VectorType(Icons.Rounded.Image),
                size = 50.dp,
                background = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            ) {}
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = diary.title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium),
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = diary.toDateTimeString(),
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Normal),
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End,
        ) {
            Text(
                text = diary.tag,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
            )
        }
    }
}

@Composable
fun RecentlyActivity(
    state: MainState,
    navigator: Navigator,
    modifier: Modifier = Modifier
) {
    SectionWithHeader(
        title = "Recently Added Weekly",
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        LazyRow(
            contentPadding = PaddingValues(end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(
                items = state.recentlyDiaries,
                key = { item -> item.id!! }
            ) { diary ->
                IconVertical(
                    text = diary.title,
                    imageType = if (diary.imageBytes != null) ImageType.BitmapType(diary.imageBytes.toImageBitmap())
                                else ImageType.VectorType(Icons.Rounded.Image),
                    textColor = MaterialTheme.colorScheme.onBackground,
                    textStyle = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Medium),
                    backgroundColor = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.1f),
                    padding = if (diary.imageBytes != null) 0.dp else 8.dp,
                    onClick = { navigator.navigate(Screen.DetailScreen.route.plus("/${diary.id}")) }
                )
            }
        }
    }
}