package com.example.mobile.top_action_bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.example.common_ui.Cons.KEY_INVALID
import com.example.common_ui.Icons.CIRCLE_ICON_18
import com.example.common_ui.Icons.CROSS_ICON
import com.example.common_ui.MatColors.Companion.SURFACE
import com.example.datastore.DataStore
import com.example.mobile.getMaterialColor
import com.example.graph.sound
import com.example.local.model.Label

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun SearchField(
    title: MutableState<String>,
    placeholder: String,
    label: MutableState<Label>?
) {
    val ctx = LocalContext.current
    val thereIsSoundEffect = DataStore(ctx).thereIsSoundEffect.collectAsState(false).value

    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = title.value,
        onValueChange = { title.value = it },
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 17.sp,
            color = contentColorFor(backgroundColor = getMaterialColor(SURFACE))
        ),
        leadingIcon = {
                    Icon(
                        painter = painterResource(id = CIRCLE_ICON_18),
                        contentDescription = null,
                        tint = label?.value?.color?.let { Color(it) } ?: Color.Transparent
                    )
        },
        trailingIcon = {
            if (title.value.isNotEmpty() || label?.value?.color != Color.Transparent.toArgb()) {
                Icon(
                    painter = painterResource(id = CROSS_ICON),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        sound.makeSound.invoke(ctx, KEY_INVALID, thereIsSoundEffect)
                        title.value = ""
                        label?.value = Label()
                    }
                )
            }
        },
        placeholder = {
            Text(text = placeholder, fontSize = 17.sp)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
            }
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.DarkGray,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        )
    )
}
