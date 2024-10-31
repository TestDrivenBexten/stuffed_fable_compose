package bexten.mobile.stuffed_fable_compose

import org.junit.Test
import org.junit.Assert.*

class DiceBagTest {
    @Test
    fun shouldUpdateSelectedCountForDiceBag() {
        // Arrange
        val blackDie = createD6Die(DieColor.BLACK)
        val diceBag = DiceBag(
            diceSelectionList = listOf(
                DiceSelection(blackDie, 0, 0,5, ComparisonOperator.NONE),
            )
        )

        // Act
        val newBag = updateDiceSelectionDiceCount(diceBag, 0, 3).getOrNull()

        // Assert
        assertNotNull(newBag)
        assertEquals(newBag?.diceSelectionList?.size, 1)
        assertEquals(newBag?.diceSelectionList?.get(0)?.selectedCount, 3)
    }

    @Test
    fun shouldUpdateDrawCountForDiceBag() {
        // Arrange
        val blackDie = createD6Die(DieColor.BLACK)
        val diceBag = DiceBag(
            diceSelectionList = listOf(
                DiceSelection(blackDie, 0, 0,5, ComparisonOperator.NONE),
            )
        )

        // Act
        val newBag = updateDiceSelectionToDrawCount(diceBag, 0, 3).getOrNull()

        // Assert
        assertNotNull(newBag)
        assertEquals(newBag?.diceSelectionList?.size, 1)
        assertEquals(newBag?.diceSelectionList?.get(0)?.diceToDraw, 3)
    }
}