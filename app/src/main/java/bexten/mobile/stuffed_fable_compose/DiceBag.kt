package bexten.mobile.stuffed_fable_compose

import arrow.core.Either
import arrow.core.right

enum class DieColor {
    BLACK, WHITE, RED, GREEN, YELLOW, BLUE, PURPLE
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

data class InvalidValue(val errorMessage: String)
fun updateDiceSelection(diceBag: DiceBag, selectionIndex: Int, selectedCount: Int): Either<InvalidValue, DiceBag> {
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
