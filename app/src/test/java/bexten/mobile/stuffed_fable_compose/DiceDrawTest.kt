package bexten.mobile.stuffed_fable_compose

import org.junit.Test
import org.junit.Assert.*

class DiceDrawTest {
    @Test
    fun shouldGetTotalDiceFromFullBag() {
        // Arrange
        val blackDie = createD6Die(DieColor.BLACK)
        val greenDie = createD6Die(DieColor.GREEN)
        val diceBag = DiceBag(
            diceSelectionList = listOf(
                DiceSelection(blackDie, 0, 0,5, ComparisonOperator.NONE),
                DiceSelection(greenDie, 0, 0,5, ComparisonOperator.NONE),
            )
        )

        // Act
        val diceDraw = getDiceDraw(diceBag, 5)

        // Assert
        assertEquals(10, diceDraw.totalDiceInBag)
        assertEquals(5, diceDraw.totalDiceToDraw)
    }

    @Test
    fun shouldGetTotalDiceFromBagWithSelectedDice() {
        // Arrange
        val blackDie = createD6Die(DieColor.BLACK)
        val greenDie = createD6Die(DieColor.GREEN)
        val diceBag = DiceBag(
            diceSelectionList = listOf(
                DiceSelection(blackDie, 2, 0,5, ComparisonOperator.NONE),
                DiceSelection(greenDie, 3, 0,5, ComparisonOperator.NONE),
            )
        )

        // Act
        val diceDraw = getDiceDraw(diceBag, 5)

        // Assert
        assertEquals(5, diceDraw.totalDiceInBag)
        assertEquals(5, diceDraw.totalDiceToDraw)
    }

    @Test
    fun shouldHaveDiceToDrawBeLessThanOrEqualToTotalDiceFromBag() {
        // Arrange
        val blackDie = createD6Die(DieColor.BLACK)
        val greenDie = createD6Die(DieColor.GREEN)
        val diceBag = DiceBag(
            diceSelectionList = listOf(
                DiceSelection(blackDie, 4, 0,5, ComparisonOperator.NONE),
                DiceSelection(greenDie, 3, 0,5, ComparisonOperator.NONE),
            )
        )

        // Act
        val diceDraw = getDiceDraw(diceBag, 5)

        // Assert
        assertEquals(3, diceDraw.totalDiceInBag)
        assertEquals(3, diceDraw.totalDiceToDraw)
    }
}