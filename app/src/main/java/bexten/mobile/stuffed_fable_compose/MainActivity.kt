package bexten.mobile.stuffed_fable_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bexten.mobile.stuffed_fable_compose.ui.theme.Stuffed_fable_composeTheme

enum class DieColor {
    BLACK, WHITE, RED, GREEN, YELLOW, BLUE, PURPLE
}

fun getDieUIColor(dieColor: DieColor): Color {
    return when (dieColor) {
        DieColor.BLACK -> Color.Black
        DieColor.WHITE -> Color.White
        DieColor.RED -> Color.Red
        DieColor.GREEN -> Color.Green
        DieColor.YELLOW -> Color.Yellow
        DieColor.BLUE -> Color.Blue
        DieColor.PURPLE -> Color.Magenta
    }
}

data class Die(val faceValues: List<Int>, val dieColor: DieColor)
data class DiceSelection(val die: Die, val selectedCount: Int, val maxDice: Int)

val blackDie = Die(listOf(1, 2, 3, 4, 5, 6), DieColor.BLACK)
val selectedBlackDice = DiceSelection(blackDie, 3, 5)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Stuffed_fable_composeTheme {
                StuffedFablesHeader()
            }
        }
    }
}

@Composable
fun StuffedFablesHeader() {
    Text(text = "Stuffed Fables Dice Draw Calculator")
}

@Composable
fun DieButton(dieOrdinal: Int, buttonColor: Color, isSelected: Boolean) {
    OutlinedButton (
        onClick = {},
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),

    ) {
        if (isSelected) {
            val textColor = if (buttonColor == Color.Black) Color.White else Color.Black
            Text(text = dieOrdinal.toString(), color = textColor)
        }
    }
}

@Composable
fun DiceSelectionPanelRow(diceSelection: DiceSelection) {
    val diceToChooseFrom = diceSelection.maxDice
    val buttonColor = getDieUIColor(diceSelection.die.dieColor)
    LazyRow {
        items(diceToChooseFrom) {
            dice -> DieButton(dice + 1, buttonColor, dice < diceSelection.selectedCount)
        }
    }
}

@Composable
fun DiceSelectionPanelRow(diceSelected: Int, buttonColor: Color) {
    LazyRow {
        items(diceSelected) { dice -> DieButton(dice + 1, buttonColor, true) }
    }
}

@Composable
fun DiceSelectionPanel() {
    var isExpanded by remember { mutableStateOf(true) }
    Column {
        Text(text = "Dice Drawn", modifier = Modifier.clickable { isExpanded = !isExpanded })
        if (isExpanded) {
            DiceSelectionPanelRow(5, Color.Black)
            DiceSelectionPanelRow(5, Color.White)
            DiceSelectionPanelRow(5, Color.Red)
            DiceSelectionPanelRow(5, Color.Green)
            DiceSelectionPanelRow(5, Color.Blue)
            DiceSelectionPanelRow(5, Color.Yellow)
            DiceSelectionPanelRow(5, Color.Magenta)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DiceSelectionRowPreview() {
    Stuffed_fable_composeTheme {
        DiceSelectionPanelRow(selectedBlackDice)
    }
}

@Preview(showBackground = true)
@Composable
fun DieButtonPreview() {
    Stuffed_fable_composeTheme {
        Column {
            DieButton(3, Color.Black, true)
            DieButton(3, Color.White, false)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StuffedFablesHeaderPreview() {
    Stuffed_fable_composeTheme {
        StuffedFablesHeader()
    }
}

@Preview(showBackground = true)
@Composable
fun DiceSelectionPanelPreview() {
    Stuffed_fable_composeTheme {
        DiceSelectionPanel()
    }
}
