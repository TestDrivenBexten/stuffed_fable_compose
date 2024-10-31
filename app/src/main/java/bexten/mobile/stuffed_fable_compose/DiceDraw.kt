package bexten.mobile.stuffed_fable_compose

import kotlin.math.min

data class DiceDraw(val totalDiceInBag: Int, val totalDiceToDraw: Int)

fun getDiceDraw(diceBag: DiceBag, totalDiceToDraw: Int): DiceDraw {
    val totalDiceInBag = diceBag.diceSelectionList.sumOf { it.maxDice - it.selectedCount }
    val toDrawCount = min(totalDiceToDraw, totalDiceInBag)
    return DiceDraw(totalDiceInBag, toDrawCount)
}
