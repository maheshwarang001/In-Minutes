package com.example.rider_app.view.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PermissionDialog(
    permissionTextProvider: String,
    isPermanentlyDeclined : Boolean,
    onDismiss : ()-> Unit,
    onOkClick: ()->Unit,
    onGoToAppSettingClick: ()-> Unit,
    modifier: Modifier = Modifier
) {

    AlertDialog(

        onDismissRequest = onDismiss,

        title = {
            Text(text = "Permission Required")
        },

        text = {
            Text(text = permissionTextProvider)
        },
        confirmButton = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                HorizontalDivider()
                Text(
                    text = if(isPermanentlyDeclined) "Grant Permission" else "Ok",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isPermanentlyDeclined) {
                                onGoToAppSettingClick()
                            } else {
                                onOkClick()
                            }
                        }
                        .padding(16.dp)
                )


            }
        },

        modifier = modifier
    )
}

interface PermissionTextProvider{
    val description:String
}