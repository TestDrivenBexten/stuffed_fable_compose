package bexten.mobile.stuffed_fable_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
data class DiceBag(val diceSelectionList: List<DiceSelection>)

fun createD6Die(dieColor: DieColor): Die {
    return Die(listOf(1, 2, 3, 4, 5, 6), dieColor)
}

val blackDie = createD6Die(DieColor.BLACK)
val whiteDie = createD6Die(DieColor.WHITE)
val redDie = createD6Die(DieColor.RED)
val greenDie = createD6Die(DieColor.GREEN)
val yellowDie = createD6Die(DieColor.YELLOW)
val blueDie = createD6Die(DieColor.BLUE)
val purpleDie = createD6Die(DieColor.PURPLE)

var diceBag = DiceBag(
    diceSelectionList = listOf(
        DiceSelection(blackDie, 0, 5),
        DiceSelection(whiteDie, 0, 5),
        DiceSelection(redDie, 0, 5),
        DiceSelection(greenDie, 0, 5),
        DiceSelection(yellowDie, 0, 5),
        DiceSelection(blueDie, 0, 5),
        DiceSelection(purpleDie, 0, 5),
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Stuffed_fable_composeTheme {
                DiceSelectionPanel()
            }
        }
    }
}

@Composable
fun StuffedFablesHeader() {
    Text(text = "Stuffed Fables Dice Draw Calculator")
}

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
            val textColor = if (buttonColor == Color.Black) Color.White else Color.Black
            Text(text = dieOrdinal.toString(), color = textColor)
        }
    }
}

@Composable
fun DiceSelectionPanelRow(diceSelection: DiceSelection, updateDiceSelectionCount: (Int) -> Unit) {
    val diceToChooseFrom = diceSelection.maxDice
    val buttonColor = getDieUIColor(diceSelection.die.dieColor)
    Row {
        LazyRow {
            items(diceToChooseFrom) {
                    dice -> DieButton(dice + 1, buttonColor,
                    dice < diceSelection.selectedCount, updateDiceSelectionCount)
            }
        }
        OutlinedButton(onClick = { updateDiceSelectionCount(0) }) {
            Text(text = "X")
        }
    }
}

@Composable
fun DiceSelectionPanel() {
    var isExpanded by remember { mutableStateOf(true) }
    val updateBlackDiceSelection: (Int) -> Unit =
        { x: Int -> {  } }

    val updateDiceSelectionCount = { x: Int -> println(x) }
    Column {
        Text(text = "Dice Drawn", modifier = Modifier.clickable { isExpanded = !isExpanded })
        if (isExpanded) {
            diceBag.diceSelectionList.map { diceSelection ->
                DiceSelectionPanelRow(diceSelection, updateDiceSelectionCount)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DiceSelectionRowPreview() {
    Stuffed_fable_composeTheme {
        val selectedBlackDice = DiceSelection(blackDie, 3, 5)
        DiceSelectionPanelRow(selectedBlackDice, { x: Int -> println(x) })
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
