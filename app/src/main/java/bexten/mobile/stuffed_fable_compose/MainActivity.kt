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

var diceBag = DiceBag(
    diceSelectionList = listOf(
        DiceSelection(blackDie, 0, 0, 5),
        DiceSelection(whiteDie, 0, 0, 5),
        DiceSelection(redDie, 0, 0, 5),
        DiceSelection(greenDie, 0, 0, 5),
        DiceSelection(yellowDie, 0, 0, 5),
        DiceSelection(blueDie, 0, 0, 5),
        DiceSelection(purpleDie, 0, 0, 5),
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Stuffed_fable_composeTheme {
                StuffedFablesApp()
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
            val textColor = if (buttonColor == Color.Black || buttonColor == Color.Blue) Color.White else Color.Black
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
fun DiceSelectionPanel(diceBag: DiceBag, updateDiceSelectionCount: (Int, Int) -> Unit ) {
    var isExpanded by remember { mutableStateOf(true) }

    Column {
        Text(text = "Dice Drawn", modifier = Modifier.clickable { isExpanded = !isExpanded })
        if (isExpanded) {
            diceBag.diceSelectionList.mapIndexed { index, diceSelection ->
                DiceSelectionPanelRow(diceSelection) { x -> updateDiceSelectionCount(index, x) }
            }
        }
    }
}

@Composable
fun DiceSelectionDrawPanel(diceSelection: DiceSelection, updateDiceToDrawCount: (Int) -> Unit) {
    val diceToChooseFrom = diceSelection.maxDice - diceSelection.selectedCount
    val buttonColor = getDieUIColor(diceSelection.die.dieColor)
    Row {
        LazyRow {
            items(diceToChooseFrom) {
                    dice -> DieButton(dice + 1, buttonColor,
                dice < diceSelection.diceToDraw
            ) { x: Int -> updateDiceToDrawCount(x) }
            }
        }
        OutlinedButton(onClick = { updateDiceToDrawCount(0) }) {
            Text(text = "X")
        }
    }
}

@Composable
fun DiceDrawPanel(diceBag: DiceBag, updateDiceToDrawCount: (Int, Int) -> Unit ) {
    var isExpanded by remember { mutableStateOf(true) }

    Column {
        Text(text = "Dice to Draw", modifier = Modifier.clickable { isExpanded = !isExpanded })
        if (isExpanded) {
            diceBag.diceSelectionList.mapIndexed { index, diceSelection ->
                DiceSelectionDrawPanel(diceSelection) { x: Int -> updateDiceToDrawCount(index, x) }
            }
        }
    }
}

@Composable
fun StuffedFablesApp() {
    var diceBag by remember { mutableStateOf(diceBag) }
    val updateDiceSelectionCount: (Int, Int) -> Unit = { x, y ->
        val newDiceBag = updateDiceSelectionDiceCount(diceBag, x, y).getOrNull()
        if (newDiceBag != null) {
            diceBag = newDiceBag
        }
    }
    val updateDrawCountCount: (Int, Int) -> Unit = { x, y ->
        val newDiceBag = updateDiceSelectionToDrawCount(diceBag, x, y).getOrNull()
        if (newDiceBag != null) {
            diceBag = newDiceBag
        }
    }
    Stuffed_fable_composeTheme {
        Column {
            StuffedFablesHeader()
            DiceSelectionPanel(diceBag, updateDiceSelectionCount)
            DiceDrawPanel(diceBag, updateDrawCountCount)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DiceSelectionRowPreview() {
    Stuffed_fable_composeTheme {
        val selectedBlackDice = DiceSelection(blackDie, 3, 0, 5)
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
fun StuffedFablesAppPreview() {
    Stuffed_fable_composeTheme {
        StuffedFablesApp()
    }
}


@Preview(showBackground = true)
@Composable
fun DiceSelectionPanelPreview() {
    var diceBag by remember { mutableStateOf(diceBag) }
    val updateDiceSelectionCount: (Int, Int) -> Unit = { x, y ->
        val newDiceBag = updateDiceSelectionDiceCount(diceBag, x, y).getOrNull()
        if (newDiceBag != null) {
            diceBag = newDiceBag
        }
    }

    Stuffed_fable_composeTheme {
        DiceSelectionPanel(diceBag, updateDiceSelectionCount)
    }
}

@Preview(showBackground = true)
@Composable
fun DiceSelectionDrawPanelPreview() {
    val diceSelection = DiceSelection(blackDie, 3, 0, 5)
    Stuffed_fable_composeTheme {
        DiceSelectionDrawPanel(diceSelection) { x: Int -> println(x) }
    }
}

@Preview(showBackground = true)
@Composable
fun DiceDrawPanelPreview() {
    var diceBag by remember { mutableStateOf(diceBag) }
    val updateDrawCountCount: (Int, Int) -> Unit = { x, y ->
        val newDiceBag = updateDiceSelectionToDrawCount(diceBag, x, y).getOrNull()
        if (newDiceBag != null) {
            diceBag = newDiceBag
        }
    }
    Stuffed_fable_composeTheme {
        DiceDrawPanel(diceBag, updateDrawCountCount)
    }
}
