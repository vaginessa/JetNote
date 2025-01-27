package com.example.note.bottom_bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.common_ui.Cons.KEY_CLICK
import com.example.common_ui.Icons.REDO_ICON
import com.example.common_ui.Icons.UNDO_ICON
import com.example.common_ui.MatColors
import com.example.common_ui.MatColors.Companion.SURFACE_VARIANT
import com.example.common_ui.SoundEffect
import com.example.datastore.DataStore

@Composable
fun UndoRedo(
    titleFieldState: MutableState<String?>,
    descriptionFieldState: MutableState<String?>,
    isTitleFieldSelected : MutableState<Boolean>,
    isDescriptionFieldSelected : MutableState<Boolean>,
) {
    val titleStack = remember { mutableStateListOf<String>() }
    val descriptionStack = remember { mutableStateListOf<String>() }

    val ctx = LocalContext.current
    val thereIsSoundEffect = DataStore(ctx).thereIsSoundEffect.collectAsState(false)

    val getMatColor = MatColors().getMaterialColor
    val sound = SoundEffect()

    Icon(
        painter = painterResource(id = UNDO_ICON),
        contentDescription = null,
        tint = contentColorFor(backgroundColor = getMatColor(SURFACE_VARIANT)),
        modifier = Modifier
            .size(20.dp)
            .clickable {
                sound.makeSound(ctx, KEY_CLICK, thereIsSoundEffect.value)

                if (isTitleFieldSelected.value) {
                    if (titleFieldState.value?.isNotEmpty() == true) {
                        titleStack += titleFieldState.value!!
                            .split(' ')
                            .takeLast(1)
                    }
                    titleFieldState.value = if (!titleFieldState.value?.contains(' ')!!) {
                        ""
                    } else {
                        titleFieldState.value!!.substringBeforeLast(' ')
                    }

                }

                //
                if (isDescriptionFieldSelected.value) {

                    if (descriptionFieldState.value?.isNotEmpty() == true) {
                        descriptionStack += descriptionFieldState.value!!
                            .split(' ')
                            .takeLast(1)
                    }
                    descriptionFieldState.value =
                        if (!descriptionFieldState.value?.contains(' ')!!) {
                            ""
                        } else {
                            descriptionFieldState.value!!.substringBeforeLast(' ')
                        }
                }

            }
    )

    Icon(
        painter = painterResource(id = REDO_ICON),
        contentDescription = null,
        tint = contentColorFor(backgroundColor = getMatColor(SURFACE_VARIANT)),
        modifier = Modifier
            .size(20.dp)
            .clickable {
                sound.makeSound(ctx, KEY_CLICK, thereIsSoundEffect.value)

                if (isTitleFieldSelected.value) {
                    if (titleStack.isNotEmpty()) {
                        titleFieldState.value += " " + titleStack.last()
                        titleStack -= titleStack.last()
                    }
                }
                if (isDescriptionFieldSelected.value) {
                    if (descriptionStack.isNotEmpty()) {
                        descriptionFieldState.value += " " + descriptionStack.last()
                        descriptionStack -= descriptionStack.last()
                    }
                }

            }
    )
}