package com.example.tags

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common_ui.listOfBackgroundColors
import com.example.local.model.Label

@Composable
fun LabelDialogColors(
    labelVM: LabelVM = hiltViewModel(),
    dialogState:MutableState<Boolean>,
    idState: MutableState<Long>,
    labelState: MutableState<String>,
    colorState: MutableState<Int>
    ) {
    AlertDialog(
        onDismissRequest = { dialogState.value = false },
        confirmButton = {},
        text = {
            LazyVerticalGrid(columns = GridCells.Fixed(7), content = {
                items(listOfBackgroundColors) {
                    Canvas(
                        modifier = Modifier
                            .size(37.dp)
                            .padding(2.dp)
                            .clickable {
                                colorState.value = it.toArgb()
                                labelVM.updateLabel(
                                    Label(id = idState.value, label = labelState.value, color = it.toArgb())
                                )
                                dialogState.value = false
                            }
                    ) {
                        drawArc(
                            color = it,
                            startAngle = 1f,
                            sweepAngle = 360f,
                            useCenter = true,
                            style = Fill
                        )
                    }
                }
            })
        }
    )
}
