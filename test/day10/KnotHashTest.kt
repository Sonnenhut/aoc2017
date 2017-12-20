package day10

import org.junit.Test

import org.junit.Assert.*

class KnotHashTest {

    val challenge = Challenge.read(10)
    val challengeIntList = challenge.split(',').map{ it.toInt() }

    @Test
    fun `cascading sublist for {0,1,2}, two elements at idx 2 results {2,0}`() {
        val original = listOf(0,1,2)
        assertEquals(listOf(2,0), original.subListCascading(2,2))
    }
    @Test
    fun `cascading sublist for {0,1,2}, three elements at idx 0 results {0,1,2}`() {
        val original = listOf(0,1,2)
        assertEquals(original, original.subListCascading(0,3))
    }
    @Test
    fun `substitute first two items in a list of three elements`() {
        val original = listOf(0,1,2)
        val toSubstitute = listOf(3,4)
        assertEquals(listOf(3,4,2), original.substitueAtIdx(0, toSubstitute))
    }

    @Test
    fun `substitute last two items in a list of three elements`() {
        val original = listOf(0,1,2)
        val toSubstitute = listOf(3,4)
        assertEquals(listOf(0,3,4), original.substitueAtIdx(1, toSubstitute))
    }
    @Test
    fun `substitute at last element in list and continue at the start`() {
        val original = listOf(0,1,2)
        val toSubstitute = listOf(3,4)
        assertEquals(listOf(4,1,3), original.substitueAtIdx(2, toSubstitute))
    }
    @Test
    fun `substitute through the end and start from beginning`() {
        val original = listOf(0,1,2,3,4,5,6,7,8,9)
        val toSubstitute = listOf(17,18,19,20,21,22)
        assertEquals(listOf(20,21,22,3,4,5,6,17,18,19), original.substitueAtIdx(7, toSubstitute))
    }

    @Test
    fun `knot 2 for {(0 1) 2 3}`() {
        assertEquals(listOf(1,0,2,3),KnotHash(4).knot1(2))
    }
    @Test
    fun `knot 3 for {(0 1 2) 3}`() {
        assertEquals(listOf(2,1,0,3),KnotHash(4).knot1(3))
    }
    @Test
    fun `knot 3 for {0 1) 2 (3}`() {
        assertEquals(listOf(0,3,2,1),KnotHash(4).knot1(3, idx = 3))
    }

    @Test
    fun `knot 3,2 for {(0 1 2) 3} to {2) 1 (0 3} to {3 1 0 2}`() {
        val hash = KnotHash(4)
        hash.knot1(multiple = listOf(3,3))
        assertEquals(listOf(0,1,2,3), hash.toIntList())
    }

    @Test
    fun `part1 example`() {
        val hash = KnotHash(5)
        assertEquals(listOf((0),1,2,3,4), hash.toIntList())
        hash.knot1(3)
        assertEquals(listOf(2,1,0,(3),4), hash.toIntList())
        assertEquals(3, hash.posIdx)
        assertEquals(1, hash.skipSize)
        hash.knot1(4)
        assertEquals(listOf(4,3,0,(1),2), hash.toIntList())
        assertEquals(3, hash.posIdx)
        assertEquals(2, hash.skipSize)
        hash.knot1(1)
        assertEquals(listOf(4,(3),0,1,2), hash.toIntList())
        assertEquals(1, hash.posIdx)
        assertEquals(3, hash.skipSize)
        hash.knot1(5)
        assertEquals(listOf(3,4,2,1,(0)), hash.toIntList())
        assertEquals(4, hash.posIdx)
        assertEquals(4, hash.skipSize)

    }
    @Test
    fun `part1 challenge`() {
        val hash = KnotHash()
        challengeIntList.forEach { hash.knot1(it) }
        val resList = hash.toIntList()
        assertEquals(52070, resList[0] * resList[1])
    }

    @Test
    fun `toASCII works`() {
        assertEquals(44, ','.toASCII())
        assertEquals(49, '1'.toASCII())
        assertEquals(50, '2'.toASCII())
        assertEquals(51, '3'.toASCII())
    }
    @Test
    fun `dense example works`() {
        val sixteenBytes = listOf(65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22)
        assertEquals(64, sixteenBytes.dense())
        assertEquals(listOf(64,64), (sixteenBytes + sixteenBytes).dense16())
    }
    @Test
    fun `hex example works`() {
        val threeBytes = listOf(64,7,255)
        assertEquals("4007ff", threeBytes.toHex())
    }
    @Test
    fun `kotlin split into blocks works`() {
        val fourElements = listOf(0,1,2,3)
        assertEquals(listOf(listOf(0,1), listOf(2,3)), fourElements.windowed(2,2))
        val fiveElements = listOf(0,1,2,3,4)
        assertEquals(listOf(listOf(0,1), listOf(2,3)), fiveElements.windowed(2,2))
    }
    @Test
    fun `part2 example empty String`() {
        var hash = KnotHash()
        hash.knot2("")
        assertEquals("a2582a3a0e66e6e86e3812dcb672a272", hash.toHex())

        hash = KnotHash()
        hash.knot2("AoC 2017")
        assertEquals("33efeb34ea91902bb2f59c9920caa6cd", hash.toHex())

        hash = KnotHash()
        hash.knot2("1,2,3")
        assertEquals("3efbe78a8d82f29979031a4aa0b16a9d", hash.toHex())

        hash = KnotHash()
        hash.knot2("1,2,4")
        assertEquals("63960835bcdc130f0b66d7ff4f6a5a8e", hash.toHex())
    }
    @Test
    fun `part2 challenge`() {
        var hash = KnotHash()
        hash.knot2(challenge)
        assertEquals("7f94112db4e32e19cf6502073c66f9bb", hash.toHex())
    }
}