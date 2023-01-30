package com.example.mobile.ui.top_action_bar.dialogs

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.datastore.DataStore
import com.example.mobile.cons.KEY_CLICK
import com.example.mobile.cons.SURFACE_TINT
import com.example.mobile.fp.getMaterialColor
import com.example.mobile.ui.settings_screen.makeSound

@Composable
fun EraseDialog(
    dialogState: MutableState<Boolean>,
    confirmation : () -> Unit,
) {
    val ctx = LocalContext.current
    val thereIsSoundEffect = DataStore(ctx).thereIsSoundEffect.collectAsState(false)

    AlertDialog(
        onDismissRequest = { dialogState.value = false },
        confirmButton = {
            ClickableText(
                text = AnnotatedString("Confirm"),
                style = TextStyle(
                    color = getMaterialColor(SURFACE_TINT),
                    fontSize = 17.sp
                ),
                onClick = {
                    Unit.makeSound.invoke(ctx, KEY_CLICK, thereIsSoundEffect.value)
                    confirmation.invoke()
                    dialogState.value = false
                }
            )
                        },
        dismissButton = {
            ClickableText(
                text = AnnotatedString("Cansel"),
                style = TextStyle(
                    color = getMaterialColor(SURFACE_TINT),
                    fontSize = 17.sp
                ),
                onClick = {
                    Unit.makeSound.invoke(ctx, KEY_CLICK, thereIsSoundEffect.value)
                    dialogState.value = false
                }
            )
        },
        title = {
                Text(text = "Empty Trash?")
        },
        text = {
            Text(text = "All notes is trash will be permanently deleted.")
        }
    )
}