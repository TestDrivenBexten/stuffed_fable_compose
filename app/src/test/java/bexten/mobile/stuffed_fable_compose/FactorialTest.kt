package bexten.mobile.stuffed_fable_compose

import org.junit.Test
import org.junit.Assert.*
import java.math.BigInteger

class FactorialTest {
    @Test
    fun shouldHave5CombinationsWhenChoosing1From5() {
        // Arrange
        val n = 5
        val k = 1

        // Act
        val combinations = chooseKFromN(k, n)

        // Assert
        assertEquals(combinations, BigInteger("5"))
    }

    @Test
    fun shouldHave142506CombinationsWhenChoosing5From30() {
        // Arrange
        val n = 30
        val k = 5

        // Act
        val combinations = chooseKFromN(k, n)

        // Assert
        assertEquals(combinations, BigInteger("142506"))
    }

    @Test
    fun shouldHave324632CombinationsWhenChoosing5From35() {
        // Arrange
        val n = 35
        val k = 5

        // Act
        val combinations = chooseKFromN(k, n)

        // Assert
        assertEquals(combinations, BigInteger("324632"))
    }
}
