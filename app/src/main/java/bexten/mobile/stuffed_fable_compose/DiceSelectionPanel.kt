package bexten.mobile.stuffed_fable_compose

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
import androidx.compose.ui.tooling.preview.Preview
import bexten.mobile.stuffed_fable_compose.ui.theme.Stuffed_fable_composeTheme

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
    Column {
        diceBag.diceSelectionList.mapIndexed { index, diceSelection ->
            DiceSelectionPanelRow(diceSelection) { x -> updateDiceSelectionCount(index, x) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DiceSelectionRowPreview() {
    Stuffed_fable_composeTheme {
        val selectedBlackDice = DiceSelection(blackDie, 3, 0, 5, ComparisonOperator.NONE)
        DiceSelectionPanelRow(selectedBlackDice, { x: Int -> println(x) })
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
