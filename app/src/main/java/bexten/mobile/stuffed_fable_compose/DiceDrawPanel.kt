package bexten.mobile.stuffed_fable_compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
fun OperatorDropdown(comparisonOperator: ComparisonOperator) {
    var mExpanded by remember { mutableStateOf(false) }
    var mSelectedText by remember { mutableStateOf(getComparisonOperatorText(comparisonOperator)) }

    val operatorList = ComparisonOperator.entries.toList()

    Column {
        OutlinedButton(onClick = { mExpanded = !mExpanded }) {
            Text(text = mSelectedText)
        }
        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false }
        ) {
            operatorList.forEach { operator ->
                DropdownMenuItem(onClick = {
                    mSelectedText = getComparisonOperatorText(operator)
                    mExpanded = false
                },
                text = { Text(text = getComparisonOperatorText(operator)) })
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
        OperatorDropdown(ComparisonOperator.NONE)
        OutlinedButton(onClick = { updateDiceToDrawCount(0) }) {
            Text(text = "X")
        }
    }
}

@Composable
fun DiceDrawPanel(diceBag: DiceBag, updateDiceToDrawCount: (Int, Int) -> Unit ) {
    var diceDrawProbability by remember { mutableStateOf(0.0) }
    Column {
        diceBag.diceSelectionList.mapIndexed { index, diceSelection ->
            DiceSelectionDrawPanel(diceSelection) { x: Int -> updateDiceToDrawCount(index, x) }
        }
        OutlinedButton(onClick = { diceDrawProbability = calculateDiceDrawProbability(diceBag) }) {
            Text(text = "Calculate")
        }
        Text(text = "Result: $diceDrawProbability")
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
fun OperatorDropdownPreview() {
    Stuffed_fable_composeTheme {
        OperatorDropdown(comparisonOperator = ComparisonOperator.NONE)
    }
}

@Preview(showBackground = true)
@Composable
fun DiceSelectionDrawPanelPreview() {
    val diceSelection = DiceSelection(blackDie, 3, 0, 5, ComparisonOperator.NONE)
    Stuffed_fable_composeTheme {
        DiceSelectionDrawPanel(diceSelection) { x: Int -> println(x) }
    }
}
