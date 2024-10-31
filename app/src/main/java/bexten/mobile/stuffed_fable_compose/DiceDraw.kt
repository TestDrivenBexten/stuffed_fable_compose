package bexten.mobile.stuffed_fable_compose

data class DiceDraw(val totalDiceInBag: Int, val totalDiceToDraw: Int)

fun getDiceDraw(diceBag: DiceBag, totalDiceToDraw: Int): DiceDraw {
    val totalDiceInBag = diceBag.diceSelectionList.sumOf { it.maxDice - it.selectedCount }
    return DiceDraw(totalDiceInBag, totalDiceToDraw)
}
