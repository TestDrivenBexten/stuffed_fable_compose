package bexten.mobile.stuffed_fable_compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bexten.mobile.stuffed_fable_compose.ui.theme.Stuffed_fable_composeTheme

@Composable
fun DieButton(dieOrdinal: Int,
              buttonColor: Color,
              isSelected: Boolean,
              updateDiceSelectionCount: (Int) -> Unit) {
    OutlinedButton (
        onClick = { updateDiceSelectionCount(dieOrdinal) },
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
    ) {
        if (isSelected) {
            val textColor = if (buttonColor == Color.Black || buttonColor == Color.Blue) Color.White else Color.Black
            Text(text = dieOrdinal.toString(), color = textColor)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DieButtonPreview() {
    val updateDiceSelectionCount = { x: Int -> println(x) }
    Stuffed_fable_composeTheme {
        Row {
            DieButton(3, Color.Black, true, updateDiceSelectionCount)
            DieButton(3, Color.White, false, updateDiceSelectionCount)
        }
    }
}
