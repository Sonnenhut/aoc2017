package day24

import org.junit.Assert.*
import org.junit.Test

class ElectromagneticMoatTest {

    val challenge = Challenge.read(24).lines().toComponents()
    val example = """
                    |0/2
                    |2/2
                    |2/3
                    |3/4
                    |3/5
                    |0/1
                    |10/1
                    |9/10""".trimMargin().lines().toComponents()

    @Test
    fun `create bridge not connecting 21 with 12`() {
        val input = listOf("0/9","9/21","12/8").toComponents()
        val expected = listOf(input.take(1), input.take(2)).map { Bridge(it) }.toSet()
        assertEquals(expected,input.bridges())
    }
    @Test
    fun `create 1 bridge`() {
        val input = listOf("0/1").toComponents()
        val expected = setOf(Bridge(input))
        assertEquals(expected,input.bridges())
    }
    @Test
    fun `create 2 bridges`() {
        val input = listOf("0/1", "1/2").toComponents()
        val expected = listOf(input.take(1),input).map { Bridge(it) }.toSet()
        assertEquals(expected,input.bridges())
    }
    @Test
    fun `create 3 possible bridges`() {
        val input = listOf("0/1", "1/2", "2/3").toComponents()
        val expected = setOf(input.take(1), input.take(2), input).map { Bridge(it)}.toSet()
        assertEquals(expected,input.bridges())
    }
    @Test
    fun `create possible bridges with non-fitting elements`() {
        val input = listOf("0/1", "1/2", "5/9").toComponents()
        val expected = setOf(input.take(1), input.take(2)).map { Bridge(it)}.toSet()
        assertEquals(expected,input.bridges())
    }
    @Test
    fun `create bridges with different elements`() {
        val input = listOf("0/1", "1/2", "1/3").toComponents()
        val expected = setOf(
                input.take(1),
                input.take(2),
                listOf(input[0], input[2])).map { Bridge(it)}.toSet()
        assertEquals(expected,input.bridges())
    }
    @Test
    fun `part1 example possible bridges`() {
        val input = example
        val c02 = Component(0,2)
        val c22 = Component(2,2)
        val c23 = Component(2,3)
        val c34 = Component(3,4)
        val c35 = Component(3,5)
        val c01 = Component(0,1)
        val c101 = Component(10,1)
        val c910 = Component(9,10)
        val expected = setOf(
                listOf(c01),
                listOf(c01,c101.flip()),
                listOf(c01,c101.flip(),c910.flip()),
                listOf(c02),
                listOf(c02,c23),
                listOf(c02,c23, c34),
                listOf(c02,c23, c35),
                listOf(c02,c22),
                listOf(c02,c22,c23),
                listOf(c02,c22,c23,c34),
                listOf(c02,c22,c23,c35)
        ).map { Bridge(it) }.toSet()
        val res = input.bridges(0)
        assertEquals(expected,res)
    }
    @Test
    fun `part1 strongest bridge example`() {
        val input = example
        assertEquals(31,input.strongestBridge())
    }
    @Test
    fun `part1 challenge`() {
        val input = challenge
        assertEquals(1940,input.strongestBridge())
    }
    @Test
    fun `part2 longest bridge that is strongest`() {
        val input = example
        assertEquals(19,input.longestBridgeStrength())
    }
    @Test
    fun `part2 longest bridge strength`() {
        val input = challenge
        assertEquals(1928,input.longestBridgeStrength())
    }
}