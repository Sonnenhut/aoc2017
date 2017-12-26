package day01

import org.junit.Assert.*
import org.junit.Test

class InverseCaptchaTest {

    @Test
    fun `part1 11 is 2`() {
        assertEquals(2,InverseCaptcha.solvePartOne(11))
    }
    @Test
    fun `part1 two equal digits are summed`() {
        assertEquals(2,InverseCaptcha.solvePartOne(11))
        assertEquals(4,InverseCaptcha.solvePartOne(22))
        assertEquals(6,InverseCaptcha.solvePartOne(33))
    }
    @Test
    fun `part1 three with two equal digits are known as one`() {
        assertEquals(1,InverseCaptcha.solvePartOne(119))
        assertEquals(2,InverseCaptcha.solvePartOne(229))
        assertEquals(3,InverseCaptcha.solvePartOne(339))
    }
    @Test
    fun `part1 three equal digits are summed`() {
        assertEquals(3,InverseCaptcha.solvePartOne(111))
    }
    @Test
    fun `part1 example`() {
        assertEquals(3,InverseCaptcha.solvePartOne(1122))
        assertEquals(4,InverseCaptcha.solvePartOne(1111))
        assertEquals(0,InverseCaptcha.solvePartOne(1234))
        assertEquals(9,InverseCaptcha.solvePartOne(91212129))
    }
    @Test
    fun `part1 challenge`() {
        val challengeInput = Challenge.read(1)
        assertEquals(1253,InverseCaptcha.solvePartOne(inputStr = challengeInput))
    }

    @Test
    fun `part2 1212 is 6`() {
        assertEquals(6,InverseCaptcha.solvePartTwo(1212))
    }
    @Test
    fun `part2 examples`() {
        assertEquals(6,InverseCaptcha.solvePartTwo(1212))
        assertEquals(0,InverseCaptcha.solvePartTwo(1221))
        assertEquals(4,InverseCaptcha.solvePartTwo(123425))
        assertEquals(12,InverseCaptcha.solvePartTwo(123123))
        assertEquals(4,InverseCaptcha.solvePartTwo(12131415))
    }
    @Test
    fun `part2 challenge`() {
        val challengeInput = Challenge.read(1)
        assertEquals(1278,InverseCaptcha.solvePartTwo(inputStr = challengeInput))
    }

}