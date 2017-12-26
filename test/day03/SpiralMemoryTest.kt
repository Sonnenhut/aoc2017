package day03

import org.junit.Test

import org.junit.Assert.*

class SpiralMemoryTest {

    @Test
    fun `part1 create spiral 2`() {
        val spiral = SpiralMemoryPartOne().addBlock(2)
        val expected = createExpectedRes(
"""
0 0 0
0 1 2
0 0 0""")
        assertEquals(expected, spiral.spiral)
    }

    @Test
    fun `part1 create spiral 3`() {
        val spiral = SpiralMemoryPartOne().addBlock(3)
        val expected = createExpectedRes(
                """
0 0 3
0 1 2
0 0 0""")
        assertEquals(expected, spiral.spiral)
    }

    @Test
    fun `part1 create spiral 4`() {
        val spiral = SpiralMemoryPartOne().addBlock(4)
        val expected = createExpectedRes(
                """
0 4 3
0 1 2
0 0 0""")
        assertEquals(expected, spiral.spiral)
    }

    @Test
    fun `part1 create spiral 5`() {
        val spiral = SpiralMemoryPartOne().addBlock(5)
        val expected = createExpectedRes(
                """
5 4 3
0 1 2
0 0 0""")
        assertEquals(expected, spiral.spiral)
    }
    @Test
    fun `part1 create spiral 6`() {
        val spiral = SpiralMemoryPartOne().addBlock(6)
        val expected = createExpectedRes(
                """
5 4 3
6 1 2
0 0 0""")
        assertEquals(expected, spiral.spiral)
    }
    @Test
    fun `part1 create spiral 7`() {
        val spiral = SpiralMemoryPartOne().addBlock(7)
        val expected = createExpectedRes(
                """
5 4 3
6 1 2
7 0 0""")
        assertEquals(expected, spiral.spiral)
    }
    @Test
    fun `part1 create spiral 8`() {
        val spiral = SpiralMemoryPartOne().addBlock(8)
        val expected = createExpectedRes(
                """
5 4 3
6 1 2
7 8 0""")
        assertEquals(expected, spiral.spiral)
    }
    @Test
    fun `part1 create spiral 9`() {
        val spiral = SpiralMemoryPartOne().addBlock(9)
        val expected = createExpectedRes(
                """
5 4 3
6 1 2
7 8 9""")
        assertEquals(expected, spiral.spiral)
    }
    @Test
    fun `part1 create spiral 10`() {
        val spiral = SpiralMemoryPartOne().addBlock(10)
        val expected = createExpectedRes(
                """
0 0 0 0 0
0 5 4 3 0
0 6 1 2 0
0 7 8 9 10
0 0 0 0 0""")
        assertEquals(expected, spiral.spiral)
    }
    @Test
    fun `part1 create spiral 25`() {
        val spiral = SpiralMemoryPartOne().addBlock(25)
        val expected = createExpectedRes(
                """
17 16 15 14 13
18 5 4 3 12
19 6 1 2 11
20 7 8 9 10
21 22 23 24 25""")
        assertEquals(expected, spiral.spiral)
    }


    @Test
    fun `part1 spiral 9 find pos 9`() {
        val spiral = SpiralMemoryPartOne().addBlock(9)
        assertEquals(Pair(2,2), spiral.findPosOfNum(9))
    }
    @Test
    fun `part1 find to middle from 9 in spiral9`() {
        val spiral = SpiralMemoryPartOne().addBlock(9)
        assertEquals(2, spiral.countStepsFromTo(9,1))
    }
    @Test
    fun `part1 find to middle from 3 in spiral9`() {
        val spiral = SpiralMemoryPartOne().addBlock(9)
        assertEquals(2, spiral.countStepsFromTo(3,1))
    }

    @Test
    fun `part1 example`() {
        val spiral = SpiralMemoryPartOne().addBlock(1024)
        assertEquals(0, spiral.countStepsFromTo(1,1))
        assertEquals(3, spiral.countStepsFromTo(12,1))
        assertEquals(2, spiral.countStepsFromTo(23,1))
        assertEquals(31, spiral.countStepsFromTo(1024,1))
    }

    @Test
    fun `part1 challenge`() {
        val spiral = SpiralMemoryPartOne().addBlock(277678)
        assertEquals(475, spiral.countStepsFromTo(277678,1))
    }

    @Test
    fun `part2 create two blocks`() {
        val spiral = SpiralMemoryPartTwo().addBlock(2)
        val expected = createExpectedRes(
                """
0 0 0
0 1 1
0 0 0""")
        assertEquals(expected, spiral.spiral)
    }
    @Test
    fun `part2 create three blocks`() {
        val spiral = SpiralMemoryPartTwo().addBlock(3)
        val expected = createExpectedRes(
                """
0 0 2
0 1 1
0 0 0""")
        assertEquals(expected, spiral.spiral)
    }

    @Test
    fun `part2 create nine blocks`() {
        val spiral = SpiralMemoryPartTwo().addBlock(9)
        val expected = createExpectedRes(
                """
5 4 2
10 1 1
11 23 25""")
        assertEquals(expected, spiral.spiral)
    }
    @Test
    fun `part2 challenge`() {
        val spiral = SpiralMemoryPartTwo().addBlock(untilValueFits = 277678)
        assertEquals(279138, spiral.lookAtCurrentBlock())
    }

    private fun createExpectedRes(str: String): List<List<Int>> {
        return str.trimMargin().split('\n').map { it.split(" ").map { it.toInt() } }
    }
}