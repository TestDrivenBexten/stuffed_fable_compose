package bexten.mobile.stuffed_fable_compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import bexten.mobile.stuffed_fable_compose.ui.theme.Stuffed_fable_composeTheme

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

@Preview(showBackground = true)
@Composable
fun DiceSelectionDrawPanelPreview() {
    val diceSelection = DiceSelection(blackDie, 3, 0, 5)
    Stuffed_fable_composeTheme {
        DiceSelectionDrawPanel(diceSelection) { x: Int -> println(x) }
    }
}