package day02

import day02.CorruptionChecksum.checksumPartOne
import day02.CorruptionChecksum.checksumPartTwo
import day02.CorruptionChecksum.findEvenDivision
import org.junit.Test

import org.junit.Assert.*

class CorruptionChecksumTest {

    private val challenge = Challenge.read(2).lines()
                .map { it.split('\t') }
                .map { list -> list.map{ it.toInt() } }

    @Test
    fun `part1 checksum of 5 1 9 5 is 8`() {
        assertEquals(8, checksumPartOne(listOf(listOf(5,1,9,5))))
    }
    @Test
    fun `part1 checksum of two dimensional 5 1 9 5 and 5 1 9 5 is 16`() {
        assertEquals(16, checksumPartOne(listOf(listOf(5,1,9,5),listOf(5,1,9,5))))
    }
    @Test
    fun `part1 checksum of example is 18`() {
        assertEquals(18, checksumPartOne(listOf(listOf(5,1,9,5),listOf(7,5,3),listOf(2,4,6,8))))
    }

    @Test
    fun `part1 challenge`() {
        assertEquals(43074, checksumPartOne(challenge))
    }


    @Test
    fun `part2 find only even division`() {
        assertEquals(4, findEvenDivision(listOf(5,9,2,8)))
        assertEquals(3, findEvenDivision(listOf(9,4,7,3)))
        assertEquals(2, findEvenDivision(listOf(3,8,6,5)))
    }
    @Test
    fun `part2 example`() {
        assertEquals(9, checksumPartTwo(listOf(listOf(5,9,2,8), listOf(9,4,7,3), listOf(3,8,6,5))))
    }

    @Test
    fun `part2 challenge`() {
        assertEquals(280, checksumPartTwo(challenge))
    }
}