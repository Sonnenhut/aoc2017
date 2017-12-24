package day23

import day23.CoprocessorConflagation.countMulInvoked
import day23.CoprocessorConflagation.executeAssembly

import org.junit.Assert.*
import org.junit.Test

class CoprocessorConflagationTest {

    val challenge = Challenge.read(23).lines()//Challenge.read(232).lines()

    @Test
    fun `count mul invoked one time`() {
        val input = """
                    |mul a 1
                    """.trimMargin().lines()
        assertEquals(1, countMulInvoked(input))
    }
    @Test
    fun `part1 challenge`() {
        assertEquals(9409, countMulInvoked(challenge))
    }
    @Test
    fun `part2 challenge`() {
        // not 46341
        assertEquals(913, executeAssembly())
    }
}