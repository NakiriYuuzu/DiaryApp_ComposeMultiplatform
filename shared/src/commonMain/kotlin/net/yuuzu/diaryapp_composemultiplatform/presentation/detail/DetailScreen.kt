package net.yuuzu.diaryapp_composemultiplatform.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import com.mohamedrejeb.calf.picker.toImageBitmap
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel
import net.yuuzu.diaryapp_composemultiplatform.common.ImageType
import net.yuuzu.diaryapp_composemultiplatform.presentation.detail.components.AddImage
import net.yuuzu.diaryapp_composemultiplatform.presentation.detail.components.IconButtonWithText
import net.yuuzu.diaryapp_composemultiplatform.presentation.detail.components.TransparentHintTextField
import net.yuuzu.diaryapp_composemultiplatform.utils.expect.rememberBitmapFromBytes

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DetailScreen(navigator: Navigator, diaryId: Long) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackbarHostState = remember { SnackbarHostState() }

    val viewModel = viewModel(DetailViewModel::class) { DetailViewModel(diaryId) }
    val state by viewModel.state.collectAsStateWithLifecycle()

    val pickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files ->
            files.firstOrNull()?.let { file ->
                viewModel.onEvent(DetailEvent.OnPhotoPicked(file.readByteArray()))
            }
        }
    )

    LaunchedEffect(state.message) {
        if (state.message.isNotBlank()) {
            snackbarHostState.showSnackbar(state.message)
        }
    }

    LaunchedEffect(state.completed) {
        if (state.completed) {
            keyboardController?.hide()
            navigator.popBackStack()
        }
    }

    Scaffold(
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (diaryId > 0) {
                    FloatingActionButton(
                        onClick = { viewModel.onEvent(DetailEvent.OnDeleteClicked) },
                        containerColor = MaterialTheme.colorScheme.error,
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = null,
                        )
                    }
                }
                FloatingActionButton(
                    onClick = { viewModel.onEvent(DetailEvent.OnSaveClicked) }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Save,
                        contentDescription = null,
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .verticalScroll(rememberScrollState())
                .pointerInput(Unit) {
                    detectTapGestures { keyboardController?.hide() }
                }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.4f)
                    .clip(shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                if (viewModel.newDiary?.imageBytes != null) {
                    AddImage(
                        imageType = ImageType.BitmapType(viewModel.newDiary?.imageBytes?.toImageBitmap()!!),
                        contentScale = ContentScale.Crop,
                        onClicked = {
                            keyboardController?.hide()
                            pickerLauncher.launch()
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                    )
                } else {
                    AddImage(
                        imageType = ImageType.VectorType(Icons.Rounded.Image),
                        iconSize = 128.dp,
                        onClicked = {
                            keyboardController?.hide()
                            pickerLauncher.launch()
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                    )
                }
                IconButtonWithText(
                    text = "Back",
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    iconColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(CircleShape)
                        .clickable { navigator.popBackStack() }
                        .padding(8.dp)
                )
                TransparentHintTextField(
                    text = viewModel.newDiary?.tag ?: "",
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 20.sp,
                        textAlign = TextAlign.End
                    ),
                    hint = "Enter tag here",
                    isHintVisible = viewModel.newDiary?.tag?.isEmpty() ?: true,
                    onValueChanged = { println("YuuzuX: " + viewModel.newDiary)
                        viewModel.onEvent(DetailEvent.OnTagChanged(it)) },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .fillMaxWidth()
                        .padding(16.dp),
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.6f)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    TransparentHintTextField(
                        text = viewModel.newDiary?.title ?: "",
                        textStyle = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                        hint = "Enter title here",
                        isHintVisible = viewModel.newDiary?.title?.isEmpty() ?: true,
                        onValueChanged = { viewModel.onEvent(DetailEvent.OnTitleChanged(it)) },
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TransparentHintTextField(
                        text = viewModel.newDiary?.content ?: "",
                        onValueChanged = { viewModel.onEvent(DetailEvent.OnContentChanged(it)) },
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 20.sp
                        ),
                        hint = "Enter content here",
                        isHintVisible = viewModel.newDiary?.content?.isEmpty() ?: true,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    )
                }
            }
        }
    }
}