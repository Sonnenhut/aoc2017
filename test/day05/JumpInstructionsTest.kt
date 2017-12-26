package day05

import org.junit.Test

import org.junit.Assert.*

class JumpInstructionsTest {

    val challenge = Challenge.read(5).lines().map { it.toInt() }

    @Test
    fun `part1 execute simple instruction {0} takes 2 steps to escape`() {
        assertEquals(2, JumpInstructionsPartOne(listOf(0)).executeInstructions())
    }
    @Test
    fun `part1 execute simple instruction {0 3 0 1 -3} takes 5 steps to escape`() {
        assertEquals(5, JumpInstructionsPartOne(listOf(0,3,0,1,-3)).executeInstructions())
    }
    @Test
    fun `part1 challenge`() {
        assertEquals(372139, JumpInstructionsPartOne(challenge).executeInstructions(print = false))
    }
    @Test
    fun `part2 example`() {
        assertEquals(10, JumpInstructionsPartTwo(listOf(0,3,0,1,-3)).executeInstructions())
    }
    @Test
    fun `part2 challenge`() {
        assertEquals(29629538, JumpInstructionsPartTwo(challenge).executeInstructions(print = false))
    }


}