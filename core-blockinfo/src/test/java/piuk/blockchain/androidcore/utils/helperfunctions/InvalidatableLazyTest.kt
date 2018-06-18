package piuk.blockchain.androidcore.utils.helperfunctions

import org.amshove.kluent.`should equal to`
import org.junit.Test

class InvalidatableLazyTest {

    private val firstString = "FIRST_STRING"
    private val secondString = "SECOND_STRING"
    private var stringToReturn = firstString

    @Test
    fun testInvalidatableLazy() {
        // Arrange - locally scoped functions are dope
        fun getValue(): String = stringToReturn

        val invalidatableLazy = InvalidatableLazy { getValue() }
        val invalidatableValue by invalidatableLazy
        // Returned value should be first String
        invalidatableValue `should equal to` firstString
        // Change string returned by function, first value should still be returned as result lazy-loaded
        stringToReturn = secondString
        invalidatableValue `should equal to` firstString
        // Invalidate delegate
        invalidatableLazy.invalidate()
        // Return value should be re-evaluated and return second String
        invalidatableValue `should equal to` secondString
    }
}