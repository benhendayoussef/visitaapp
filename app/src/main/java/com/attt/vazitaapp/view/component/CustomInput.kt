package com.attt.visitaapp.view.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.attt.vazitaapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun CustomTextField(value: String="",
                    onValueChange: (String) -> Unit={},
                    label:String="email",
                    placeholkder:String="Enter your email",
                    icon: Painter= painterResource(R.drawable.username),
                    modifier: Modifier = Modifier,
                    enabled:Boolean=true
) {
    var isFocused by remember { mutableStateOf(false) }


    TextField(
        enabled = enabled,
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { isFocused = it.isFocused }
            .border(
                width = 2.dp,

                color =MaterialTheme.colorScheme.onBackground.copy(alpha = if (isFocused) 0.3f else 0.1f),
                shape = RoundedCornerShape(20.dp)
            ),
        label = {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(5.dp),
                fontWeight = FontWeight.Black

            )
                },
        shape = RoundedCornerShape(20.dp),

// Enhanced input features
        singleLine = false,
        maxLines = 1,

// Visual customization
        colors = TextFieldDefaults.colors(
            // Text colors
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,

            // Indicator colors
            focusedIndicatorColor = MaterialTheme.colorScheme.background,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.background.copy(alpha = 0.5f),

            // Cursor color
            cursorColor = MaterialTheme.colorScheme.background,

            // Background colors
            disabledTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            disabledContainerColor = MaterialTheme.colorScheme.surface
        ),

// Optional decorations
        placeholder = {
            Text(
                text = placeholkder,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),

                fontWeight = FontWeight.Bold)
        },

// Input validation and error handling


// Optional leading/trailing icons
        leadingIcon = {
            Icon(
                painter = icon,
                contentDescription = "Input Icon",
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 1f),
                modifier = Modifier
                    .size(60.dp)
                    .padding(horizontal =10.dp),

            )
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    value: String = "",
    onValueChange: (String) -> Unit = {},
    label: String,
    placeholkder: String,
    icon: Painter = painterResource(R.drawable.password),
    modifier: Modifier = Modifier,
    enabled:Boolean=true
) {
    var isFocused by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    TextField(
        enabled = enabled,
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { isFocused = it.isFocused }
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = if (isFocused) 0.3f else 0.1f),
                shape = RoundedCornerShape(20.dp)
            ),
        label = {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(5.dp),
                fontWeight = FontWeight.Black
            )
        },
        shape = RoundedCornerShape(20.dp),
        singleLine = true,
        visualTransformation = if (isPasswordVisible)
            VisualTransformation.None
        else
            PasswordVisualTransformation(),
        colors = TextFieldDefaults.colors(
            // Text colors
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,

            // Indicator colors
            focusedIndicatorColor = MaterialTheme.colorScheme.background,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.background.copy(alpha = 0.5f),

            // Cursor color
            cursorColor = MaterialTheme.colorScheme.background,

            // Background colors
            disabledTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            disabledContainerColor = MaterialTheme.colorScheme.surface
        ),
        placeholder = {
            Text(
                text = placeholkder,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),

                fontWeight = FontWeight.Bold

            )
        },
        leadingIcon = {
            Icon(
                painter = icon,
                contentDescription = "Password Icon",
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 1f),
                modifier = Modifier
                    .size(60.dp)
                    .padding(horizontal = 10.dp)
            )
        },
        trailingIcon = {
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(
                    painter = if (isPasswordVisible)
                        painterResource(R.drawable.visibility)
                    else
                        painterResource(R.drawable.visibility_off),
                    contentDescription = "Toggle password visibility"
                )
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpInput(
    otpLength: Int = 6,
    modifier: Modifier,
    onOtpEntered: (String) -> Unit
) {
    val otp = remember { mutableStateListOf(*Array(otpLength) { "" }) }
    val focusRequesters = List(otpLength) { FocusRequester() }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until otpLength) {
            OutlinedTextField(
                value = otp[i],
                onValueChange = { value ->
                    if (value.length <= 1 && value.all { it.isDigit() }) {
                        otp[i] = value
                        if (value.isNotEmpty() && i < otpLength - 1) {
                            focusRequesters[i + 1].requestFocus()
                        }
                        if (otp.all { it.isNotEmpty() }) {
                            onOtpEntered(otp.joinToString(""))
                        }
                    }
                },
                modifier = Modifier
                    .width(50.dp)
                    .focusRequester(focusRequesters[i]),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                ),
                colors = TextFieldDefaults.colors(
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.primary,
                    unfocusedTextColor = MaterialTheme.colorScheme.primary
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = if (i == otpLength - 1) ImeAction.Done else ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (otp.all { it.isNotEmpty() }) {
                            onOtpEntered(otp.joinToString(""))
                        }
                    },
                    onNext = {
                        onOtpEntered(otp.joinToString(""))
                    }

                )
            )
        }
    }

    // Auto-focus the first box when the Composable loads
    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
    }
}