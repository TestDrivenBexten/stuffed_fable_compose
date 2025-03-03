package bexten.mobile.stuffed_fable_compose

import androidx.compose.ui.graphics.Color
import arrow.core.Either
import arrow.core.right

enum class DieColor {
    BLACK, WHITE, RED, GREEN, YELLOW, BLUE, PURPLE
}

enum class ComparisonOperator {
    NONE,
    LESS_THAN,
    LESS_THAN_OR_EQUAL,
    EQUAL,
    GREATER_THAN_OR_EQUAL,
    GREATER_THAN
}

fun getComparisonOperatorText(comparisonOperator: ComparisonOperator): String {
    return when (comparisonOperator) {
        ComparisonOperator.NONE -> ""
        ComparisonOperator.LESS_THAN -> "<"
        ComparisonOperator.LESS_THAN_OR_EQUAL -> "<="
        ComparisonOperator.EQUAL -> "="
        ComparisonOperator.GREATER_THAN_OR_EQUAL -> ">="
        ComparisonOperator.GREATER_THAN -> ">"
    }
}

data class Die(val faceValues: List<Int>, val dieColor: DieColor)
data class DiceSelection(val die: Die, val selectedCount: Int, val diceToDraw: Int, val maxDice: Int, val comparisonOperator: ComparisonOperator)
data class DiceBag(val diceSelectionList: List<DiceSelection>)

fun createD6Die(dieColor: DieColor): Die {
    return Die(listOf(1, 2, 3, 4, 5, 6), dieColor)
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

fun getAvailableDiceCount(diceSelection: DiceSelection): Int {
    return diceSelection.maxDice - diceSelection.selectedCount
}

val blackDie = createD6Die(DieColor.BLACK)
val whiteDie = createD6Die(DieColor.WHITE)
val redDie = createD6Die(DieColor.RED)
val greenDie = createD6Die(DieColor.GREEN)
val yellowDie = createD6Die(DieColor.YELLOW)
val blueDie = createD6Die(DieColor.BLUE)
val purpleDie = createD6Die(DieColor.PURPLE)

data class InvalidValue(val errorMessage: String)
fun updateDiceSelectionDiceCount(diceBag: DiceBag, selectionIndex: Int, selectedCount: Int): Either<InvalidValue, DiceBag> {
    val updateSelectedCount = { index: Int, diceSelection: DiceSelection ->
        if (index == selectionIndex) {
            diceSelection.copy(selectedCount = selectedCount)
        } else {
            diceSelection
        }
    }
    val newList = diceBag.diceSelectionList.mapIndexed { x, y -> updateSelectedCount(x, y) }
    return diceBag.copy(diceSelectionList = newList).right()
}

fun updateDiceSelectionToDrawCount(diceBag: DiceBag, selectionIndex: Int, toDrawCount: Int): Either<InvalidValue, DiceBag> {
    val updateSelectedCount = { index: Int, diceSelection: DiceSelection ->
        if (index == selectionIndex) {
            diceSelection.copy(diceToDraw = toDrawCount)
        } else {
            diceSelection
        }
    }
    val newList = diceBag.diceSelectionList.mapIndexed { x, y -> updateSelectedCount(x, y) }
    return diceBag.copy(diceSelectionList = newList).right()
}

fun calculateDiceDrawProbability(diceBag: DiceBag): Double {
    return 1.0
}
