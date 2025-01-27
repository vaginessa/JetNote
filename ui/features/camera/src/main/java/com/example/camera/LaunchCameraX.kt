package com.example.camera

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import com.example.camera.Commons.allPermissionsGranted

@Composable
fun LaunchCameraX(
    ctx: Context = LocalContext.current,
    owner: LifecycleOwner = LocalLifecycleOwner.current,
    uid: String?
) {
    val cameraX = CameraXVM()
    CameraCompose(context = ctx, owner = owner, cameraX = cameraX) {
        if (allPermissionsGranted(ctx)) {
            cameraX.capturePhoto(context = ctx,owner = owner, uid = uid)
        }
    }
}