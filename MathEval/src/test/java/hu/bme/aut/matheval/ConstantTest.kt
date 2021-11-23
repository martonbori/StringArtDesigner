package hu.bme.aut.matheval

import org.junit.Assert
import org.junit.Test

class ConstantTest {
    @Test
    fun constructor_test() {
        val c = ConstantNode(5.0)
        Assert.assertEquals(5.0, c.eval(), 0.01)
    }
}