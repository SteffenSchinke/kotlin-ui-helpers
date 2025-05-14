package de.schinke.steffen.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker

@Composable
fun CustomColorPicker(

    selectedColor: Color,
    onColorChanged: (Color) -> Unit,
    onDismiss: () -> Unit
) {

    val colorScheme = MaterialTheme.colorScheme
    val controller = remember {
        ColorPickerController().apply {
            selectByColor(selectedColor, true)
            setWheelColor(colorScheme.primary)
        }
    }

    Dialog(onDismissRequest = onDismiss) {

        Surface(
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 6.dp,
            color = MaterialTheme.colorScheme.surface
        ) {

            Column(

                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(vertical = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AlphaTile(
                        modifier = Modifier
                            .width(80.dp)
                            .height(80.dp)
                            .clip(RoundedCornerShape(6.dp)),
                        controller = controller
                    )

                    Spacer(modifier = Modifier.width(30.dp))

                    Button(
                        onClick = {
                            onColorChanged(controller.selectedColor.value)
                            onDismiss()
                        }
                    ) {
                        Text("Farbe benutzen", style = MaterialTheme.typography.titleMedium)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                HsvColorPicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .padding(10.dp),
                    controller = controller,
                    initialColor = selectedColor,
                    onColorChanged = {
                        if (it.fromUser) {
                            onColorChanged(it.color)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                AlphaSlider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp)
                        .padding(10.dp),
                    controller = controller,
                    wheelColor = colorScheme.primary
                )

                Spacer(modifier = Modifier.height(10.dp))

                BrightnessSlider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp)
                        .padding(10.dp),
                    controller = controller,
                    wheelColor = colorScheme.primary
                )
            }
        }
    }
}
