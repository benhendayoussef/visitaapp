package com.attt.visitaapp.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton(modifier: Modifier, text: String, color:Color, enabled:Boolean=true, onClick:() -> Unit) {
    // Button implementation
    Button(
        modifier = modifier
            .fillMaxWidth(0.9f),
        onClick = {
            if (enabled) {
                onClick()
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        )
        ) {
        if( enabled)
            Text(text = text,
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(5.dp)
            )
        else
            Text(text = "Loading ...",

                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(5.dp)
            )
    }

}
@Composable
fun CustomButton(modifier: Modifier, imageId:Int, color:Color, angle:Float, onClick: () -> Unit) {
    // Button implementation
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        )
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = "Language",
            modifier = Modifier
                .padding(5.dp)
                .clip(RoundedCornerShape(20.dp))
                .rotate(angle),
            contentScale = ContentScale.FillHeight,


            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(MaterialTheme.colorScheme.onSurface),

        )
    }

}
