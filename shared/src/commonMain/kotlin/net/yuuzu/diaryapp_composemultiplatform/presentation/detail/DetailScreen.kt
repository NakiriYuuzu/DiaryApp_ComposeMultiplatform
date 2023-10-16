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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
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
import moe.tlaster.precompose.navigation.Navigator
import net.yuuzu.diaryapp_composemultiplatform.common.ImageType
import net.yuuzu.diaryapp_composemultiplatform.presentation.detail.components.AddImage
import net.yuuzu.diaryapp_composemultiplatform.presentation.detail.components.IconButtonWithText
import net.yuuzu.diaryapp_composemultiplatform.presentation.detail.components.TransparentHintTextField
import net.yuuzu.diaryapp_composemultiplatform.utils.expect.rememberBitmapFromBytes

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DetailScreen(navigator: Navigator) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var tag by remember { mutableStateOf("") }
    var byteData by remember { mutableStateOf<ByteArray?>(null) }

    val pickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files ->
            byteData = files.firstOrNull()?.readByteArray()
        }
    )

    Scaffold(
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FloatingActionButton(
                    onClick = {},
                    containerColor = MaterialTheme.colorScheme.error,
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = null,
                    )
                }
                FloatingActionButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Save,
                        contentDescription = null,
                    )
                }
            }
        }
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
                if (byteData != null) {
                    val bitmap = rememberBitmapFromBytes(byteData)
                    AddImage(
                        imageType = ImageType.BitmapType(bitmap!!),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                            .clickable {
                                keyboardController?.hide()
                                pickerLauncher.launch()
                            }
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
                    text = tag,
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 20.sp,
                        textAlign = TextAlign.End
                    ),
                    hint = "Enter tag here",
                    isHintVisible = tag.isEmpty(),
                    onValueChanged = { tag = it },
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
                        text = title,
                        textStyle = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                        hint = "Enter title here",
                        isHintVisible = title.isEmpty(),
                        onValueChanged = { title = it },
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TransparentHintTextField(
                        text = content,
                        onValueChanged = { content = it },
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 20.sp
                        ),
                        hint = "Enter content here",
                        isHintVisible = content.isEmpty(),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    )
                }
            }
        }
    }
}