package bexten.mobile.stuffed_fable_compose

import org.junit.Test
import org.junit.Assert.*

class DiceBagTest {
    @Test
    fun shouldUpdateSelectedCountForDiceBag() {
        // Arrange
        val blackDie = createD6Die(DieColor.BLACK)
        var diceBag = DiceBag(
            diceSelectionList = listOf(
                DiceSelection(blackDie, 0, 0,5),
            )
        )

        // Act
        val newBag = updateDiceSelection(diceBag, 0, 3).getOrNull()

        // Assert
        assertNotNull(newBag)
        assertEquals(newBag?.diceSelectionList?.size, 1)
        assertEquals(newBag?.diceSelectionList?.get(0)?.selectedCount, 3)
    }
}