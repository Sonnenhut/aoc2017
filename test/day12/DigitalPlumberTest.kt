package day12

import day12.DigitalPlumber.Companion.groupCount
import day12.DigitalPlumber.Companion.groupPipes
import day12.DigitalPlumber.Companion.groupSize
import org.junit.Assert.*
import org.junit.Test

class DigitalPlumberTest {

    val challenge = Challenge.read(12).lines()

    @Test
    fun `one group 0 to 0`() {
        val input = "0 <-> 0".lines()
        val expected = "0".asGroup()
        assertEquals(setOf(expected), groupPipes(input))
    }

    @Test
    fun `one group 0 to 1`() {
        val input = "0 <-> 1".lines()
        val expected = "0,1".asGroup()
        assertEquals(setOf(expected), groupPipes(input))
    }

    @Test
    fun `one group 0 to 1,2`() {
        val input = "0 <-> 1, 2".lines()
        val expected = "0,1,2".asGroup()
        assertEquals(setOf(expected), groupPipes(input))
    }
    @Test
    fun `one group joined by 0   0 to 1,2  and  3 to 0`() {
        val input = "0 <-> 1, 2\n3 <-> 0".lines()
        val expected = "0,1,2,3".asGroup()
        assertEquals(setOf(expected), groupPipes(input))
    }

    @Test
    fun `part1 example groups`(){
        val input =
                """0 <-> 2
1 <-> 1
2 <-> 0, 3, 4
3 <-> 2, 4
4 <-> 2, 3, 6
5 <-> 6
6 <-> 4, 5""".lines()
        val expected = setOf("0,2,3,4,5,6".asGroup(), "1".asGroup())
        assertEquals(expected, groupPipes(input))
    }
    @Test
    fun `part1 example size`(){
        val input =
                """0 <-> 2
1 <-> 1
2 <-> 0, 3, 4
3 <-> 2, 4
4 <-> 2, 3, 6
5 <-> 6
6 <-> 4, 5""".lines()
        assertEquals(6, groupSize(0, input))
    }
    @Test
    fun `part1 multiple groups will be connected through one common pipe`(){
        val input =
                """0 <-> 1,2,3,4,5,99
1 <-> 6,7,8,9,99
10 <-> 11,12,13,99""".lines()
        assertEquals(15, groupSize(0, input))
    }

    @Test
    fun `part1 challenge`(){
        // not 21
        assertEquals(175, groupSize(0, challenge))
    }
    @Test
    fun `part2 challenge`() {
        assertEquals(213, groupCount( challenge))
    }


    private fun String.asGroup() = this.split(",").map { it.toInt() }.toSet()
}