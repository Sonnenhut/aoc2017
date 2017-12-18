package day14

import day14.DiskFragmentation.Companion.countRegions
import day14.DiskFragmentation.Companion.diskRow
import day14.DiskFragmentation.Companion.diskRows
import day14.DiskFragmentation.Companion.markRegionRec
import day14.DiskFragmentation.Companion.toBinaryStr
import day14.DiskFragmentation.Companion.usedSquares
import org.junit.Assert.*
import org.junit.Test

class DiskFragmentationTest {

    val challenge = Challenge.read(14)
    val example = "flqrgnkx"

    @Test
    fun `binary of 0 is 0000`() {
        assertEquals("....", toBinaryStr("0"))
    }
    @Test
    fun `binary of 1 is 0001`() {
        assertEquals("...#", toBinaryStr("1"))
    }
    @Test
    fun `binary of a is 1010`() {
        assertEquals("#.#.", toBinaryStr("a"))
    }
    @Test
    fun `binary of a9 is 10101001`() {
        assertEquals("#.#.#..#", toBinaryStr("a9"))
    }
    @Test
    fun `binary example`() {
        assertEquals("#.#.....##....#........#.###....", toBinaryStr("a0c20170"))
    }
    @Test
    fun `example disk row`() {
        val expected = "##.#.#.."
        assertEquals(expected, diskRow("flqrgnkx-0").substring(0,expected.length))
    }
    @Test
    fun `example disk rows`() {
        val expected = """
            |##.#.#..
            |.#.#.#.#
            |....#.#.
            |#.#.##.#
            |.##.#...
            |##..#..#
            |.#...#..
            |##.#.##.""".trimMargin()
                .lines()
        assertEquals(expected, diskRows("flqrgnkx").map { it.substring(0,8) }.subList(0,8))
    }
    @Test
    fun `part1 example`() {
        assertEquals(8108, usedSquares(example))
    }
    @Test
    fun `part1 challenge`() {
        assertEquals(8148, usedSquares(challenge))
    }
    @Test
    fun `part2 simple region rec left and right`() {
        val input = """
            |###""".trimMargin()
                .lines().toMutableList()
        val expected= """
            |111""".trimMargin()
                .lines()
        assertEquals(expected, markRegionRec(input,Pair(0,1),'1'))
    }
    @Test
    fun `part2 simple region rec up and down`() {
        val input = """
            |.#.
            |.#.""".trimMargin()
                .lines().toMutableList()
        val expected= """
            |.1.
            |.1.""".trimMargin()
                .lines()
        assertEquals(expected, markRegionRec(input,Pair(0,1),'1'))
    }
    @Test
    fun `part2 simple region rec left, right, up, down`() {
        val input = """
            |###
            |###""".trimMargin()
                .lines().toMutableList()
        val expected= """
            |111
            |111""".trimMargin()
                .lines()
        assertEquals(expected, markRegionRec(input,Pair(0,1),'1'))
    }

    @Test
    fun `change char works`() {
        assertEquals("aca","aaa".changeChar(1,'c'))
        assertEquals("caa","aaa".changeChar(0,'c'))
        assertEquals("aac","aaa".changeChar(2,'c'))
    }

    @Test
    fun `part2 example region cnt`() {
        assertEquals(1242, countRegions(example))
    }
    @Test
    fun `part2 challenge region cnt`() {
        assertEquals(1180, countRegions(challenge))
    }
}


