package com.example.finazo_mobile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.finazo_mobile.model.PinSetter
import com.example.finazo_mobile.route.OtpScreenPin
import com.example.finazo_mobile.viewmodel.OtpScreenPinViewModel

@Composable
fun OtpScreenPinScreen(args: OtpScreenPin, navController: NavController, viewModel: OtpScreenPinViewModel) {
    val emoji = String(Character.toChars(0x1F910))
    val emojiHide = String(Character.toChars(0x1FAE3))
    val otpValue by viewModel.otp.observeAsState("")
    val pinValue by viewModel.pin.observeAsState("")
    val timerState by viewModel.timerState.observeAsState(300)
    val resendOtpVisible by viewModel.resendOtpVisible.observeAsState(false)
    val context = LocalContext.current



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .size(40.dp)
                .shadow(10.dp, RoundedCornerShape(10.dp))
                .background(Color.LightGray, shape = RoundedCornerShape(10.dp))
                .clickable { navController.popBackStack() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Arrow",
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Please Enter OTP $emoji",
            modifier = Modifier.padding(top = 20.dp),
            fontFamily = FontFamily.Serif,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Your One Time Password(OTP) has been\nsent to your email id ${args.email}",
            modifier = Modifier.padding(top = 15.dp),
            fontFamily = FontFamily.Serif,
            fontSize = 15.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium,
            maxLines = 3,
        )


        Spacer(modifier = Modifier.height(20.dp))

        OtpTextField(
            Modifier
                .height(40.dp)
                .fillMaxWidth(),
            otpText = otpValue,
            onOtpTextChange = {value,it->viewModel.onOtpChange(value)},
            keyboardType = KeyboardType.Number
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (resendOtpVisible) {
                Text(
                    text = "Resend OTP?",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {viewModel.onResendClicked() },
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    color = Color.Magenta
                )
            } else {
                Text(
                    text = "Resend OTP? ${timerState / 60}:${(timerState % 60).toString().padStart(2, '0')}",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    color = Color.Black
                )
            }
        }


        Spacer(modifier = Modifier.height(40.dp))


        Text(
            text = "Set up 6-digit Pin $emojiHide",
            modifier = Modifier.padding(top = 20.dp),
            fontFamily = FontFamily.Serif,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        OtpTextField(
            Modifier
                .height(40.dp)
                .fillMaxWidth(),
            otpText = pinValue,
            onOtpTextChange = {value,it->viewModel.onPinChange(value)},
            keyboardType = KeyboardType.NumberPassword
        )



        Spacer(modifier = Modifier.height(50.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp)
                .background(Color(0xFF1313F3), shape = RoundedCornerShape(20.dp))
        ) {
            Button(
                enabled = (pinValue.length == 6 && otpValue.length == 6),
                onClick = {
                    viewModel.verifyOtpAndSetPin(PinSetter(args.email,pinValue,otpValue),context)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text(
                    "Verify OTP & Set Pin",
                    color = Color.White,
                    fontSize = 15.sp,
                    letterSpacing = 1.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewOtp() {
    OtpScreenPinScreen(args = OtpScreenPin("mahesh"), navController = rememberNavController(), OtpScreenPinViewModel())
}


@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    onOtpTextChange: (String, Boolean) -> Unit,
    keyboardType: KeyboardType
) {
    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
    }

    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange.invoke(it.text, it.text.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpCount) { index ->
                    CharView(
                        index = index,
                        text = otpText
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}

@Composable
private fun CharView(
    index: Int,
    text: String
) {
    val isFocused = text.length == index
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text[index].toString()
    }
    Text(
        modifier = Modifier
            .width(40.dp)
            .height(40.dp)
            .border(
                1.dp, when {
                    isFocused -> Color.DarkGray
                    else -> Color.LightGray
                }, RoundedCornerShape(8.dp)
            )
            .padding(2.dp),
        text = char,
        style = MaterialTheme.typography.headlineLarge,
        color = if (isFocused) {
            Color.LightGray
        } else {
            Color.DarkGray
        },
        textAlign = TextAlign.Center
    )
}