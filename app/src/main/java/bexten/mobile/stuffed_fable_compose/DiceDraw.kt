package bexten.mobile.stuffed_fable_compose

import kotlin.math.min

data class DiceDraw(val totalDiceInBag: Int, val totalDiceToDraw: Int)

fun getDiceDraw(diceBag: DiceBag, totalDiceToDraw: Int): DiceDraw {
    val totalDiceInBag = diceBag.diceSelectionList.sumOf(::getAvailableDiceCount)
    val toDrawCount = min(totalDiceToDraw, totalDiceInBag)
    return DiceDraw(totalDiceInBag, toDrawCount)
}

data class DiceSubsetDraw(val dieColor: DieColor, val exactDrawCount: Int)
data class DiceDrawCombination(val diceSubsetDraws: List<DiceSubsetDraw>, val otherCount: Int)

fun determineDiceDrawCombinations(diceBag: DiceBag, diceToDraw: Int): List<DiceDrawCombination> {
    val otherDiceCount = diceBag.diceSelectionList
        .filter { it.comparisonOperator == ComparisonOperator.NONE }
        .sumOf(::getAvailableDiceCount)
    val equalDiceSubsetDrawList = diceBag.diceSelectionList
        .filter { it.comparisonOperator == ComparisonOperator.EQUAL }
        .map { DiceSubsetDraw(it.die.dieColor, it.diceToDraw) }
    val equalDiceDrawCombination = DiceDrawCombination(equalDiceSubsetDrawList, diceToDraw - equalDiceSubsetDrawList.sumOf { it.exactDrawCount })
    val equalDiceDrawCombinationList = listOf(equalDiceDrawCombination)

    // Add <= subset draws
    val greaterThanOrEqualDiceSubsetDrawList = diceBag.diceSelectionList
        .filter { it.comparisonOperator == ComparisonOperator.GREATER_THAN_OR_EQUAL }
        .flatMap { diceSelection ->
            val countRange = diceSelection.diceToDraw..getAvailableDiceCount(diceSelection)
            countRange.map { toDraw -> DiceSubsetDraw(diceSelection.die.dieColor, toDraw) }
        }
    val greaterThanOrEqualCombinationList = addDiceSubsetDrawsToCombinations(equalDiceDrawCombinationList, greaterThanOrEqualDiceSubsetDrawList, diceToDraw)
    return greaterThanOrEqualCombinationList
}

private fun addDiceSubsetDrawsToCombinations(combinationList: List<DiceDrawCombination>,
                                       subsetDrawList: List<DiceSubsetDraw>,
                                           diceToDraw: Int): List<DiceDrawCombination> {
    if(subsetDrawList.isEmpty()) {
        return combinationList
    }
    return combinationList.map { combination ->
        subsetDrawList.map { diceSubsetDraw ->
            val newSubsetList = combination.diceSubsetDraws.plus(diceSubsetDraw)
            val otherCount = diceToDraw - newSubsetList.sumOf { it.exactDrawCount }
            DiceDrawCombination(newSubsetList, otherCount)
        }
    }
    .flatten()
}
