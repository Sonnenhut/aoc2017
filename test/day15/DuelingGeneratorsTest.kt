package day15

import org.junit.Assert.*
import org.junit.Test

class DuelingGeneratorsTest {

    val challengeInitials = Challenge.read(15).lines().map { it.split(" ").last().toLong() }

    val genAChallengeOne = GeneratorAOne(challengeInitials[0])
    val genBChallengeOne = GeneratorBOne(challengeInitials[1])
    val genAChallengeTwo = GeneratorATwo(challengeInitials[0])
    val genBChallengeTwo = GeneratorBTwo(challengeInitials[1])

    val genAExampleOne = GeneratorAOne(65)
    val genBExampleOne = GeneratorBOne(8921)
    val genAExampleTwo = GeneratorATwo(65)
    val genBExampleTwo = GeneratorBTwo(8921)

    @Test
    fun `example generator A`() {
        val gen = genAExampleOne
        assertEquals(1092455, gen.next())
        assertEquals(1181022009, gen.next())
        assertEquals(245556042, gen.next())
        assertEquals(1744312007, gen.next())
        assertEquals(1352636452, gen.next())
    }
    @Test
    fun `example generator B`() {
        val gen = genBExampleOne
        assertEquals(430625591, gen.next())
        assertEquals(1233683848, gen.next())
        assertEquals(1431495498, gen.next())
        assertEquals(137874439, gen.next())
        assertEquals(285222916, gen.next())
    }
    @Test
    fun `example binary string`() {
        assertEquals("00000000000100001010101101100111", (1092455L).toBinaryStr())
        assertEquals("00011001101010101101001100110111", (430625591L).toBinaryStr())
    }

    @Test
    fun `part1 example`() {
        assertEquals(588, Judge.compare(genAExampleOne, genBExampleOne))
    }

    @Test
    fun `part1 challenge`() {
        assertEquals(567, Judge.compare(genAChallengeOne, genBChallengeOne))
    }

    @Test
    fun `part2 example`() {
        assertEquals(309, Judge.compare(genAExampleTwo, genBExampleTwo, 5000000L))
    }
    @Test
    fun `part2 challenge`() {
        assertEquals(323, Judge.compare(genAChallengeTwo, genBChallengeTwo, 5000000L))
    }
}