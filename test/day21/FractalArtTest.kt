package day21

import org.junit.Assert.*
import org.junit.Test

class FractalArtTest {

    val challenge = Challenge.read(21).lines()
    val example = listOf("../.# => ##./#../...",".#./..#/### => #..#/..../..../#..#")

    @Test
    fun `2x2 square matches rule with exact rotation`() {
        val rule = Rule("../.# => ##./#../...")
        val input = """
                    |..
                    |.#
                    """.trimMargin().lines()
        assertTrue(rule.matches(input))
    }
    @Test
    fun `3x3 square not matches because of mismatching dimension`() {
        val rule = Rule("../.# => ##./#../...")
        val input = """
                    |..
                    |.#
                    |..
                    """.trimMargin().lines()
        assertFalse(rule.matches(input))
    }
    @Test
    fun `2x2 convert to 3x3 square`() {
        val rule = Rule("../.# => ##./#../...")
        val input = """
                    |..
                    |.#
                    """.trimMargin().lines()
        val expected = """
                    |##.
                    |#..
                    |...
                    """.trimMargin().lines()
        assertEquals(expected, rule.invoke(input))
    }
    @Test
    fun `2x2 matches rotated 90°`() {
        val rule = Rule("../.# => ##./#../...")
        val input = """
                    |..
                    |#.
                    """.trimMargin().lines()
        assertTrue(rule.matches(input))
    }
    @Test
    fun `2x2 matches rotated -90°`() {
        val rule = Rule("../.# => ##./#../...")
        val input = """
                    |.#
                    |..
                    """.trimMargin().lines()
        assertTrue(rule.matches(input))
    }
    @Test
    fun `2x2 matches rotated 180°`() {
        val rule = Rule("../.# => ##./#../...")
        val input = """
                    |#.
                    |..
                    """.trimMargin().lines()
        assertTrue(rule.matches(input))
    }

    @Test
    fun `split 4x4 to 4 2x2 squares`() {
        val input = """
                    |#..#
                    |....
                    |....
                    |#..#
                    """.trimMargin().lines()
        val expected = listOf("""
                    |#.
                    |..
                    ""","""
                    |.#
                    |..
                    ""","""
                    |..
                    |#.
                    ""","""
                    |..
                    |.#
                    """).map { it.trimMargin().lines() }
        assertEquals(expected, splitSquares(input))
    }
    @Test
    fun `split 6x6 to 9 2x2 squares`() {
        val input = """
                    |001122
                    |001122
                    |334455
                    |334455
                    |667788
                    |667788
                    """.trimMargin().lines()
        val expected = (0..8).map { listOf("$it$it","$it$it") }
        assertEquals(expected, splitSquares(input))
    }
    @Test
    fun `split 9x9 to 9 2x2 squares`() {
        val input = """
                    |0A01A12A2
                    |0B01B12B2
                    |0C01C12C2
                    |3A34A45A5
                    |3B34B45B5
                    |3C34C45C5
                    |6A67A78A8
                    |6B67B78B8
                    |6C67C78C8
                    """.trimMargin().lines()
        val expected = (0..8).map { listOf("${it}A$it","${it}B$it","${it}C$it") }
        assertEquals(expected, splitSquares(input))
    }
    @Test
    fun `split 12x12 to 2x2 squares`() {
        val input = """
                    |001122334455
                    |001122334455
                    |66778899aabb
                    |66778899aabb
                    |ccddeeffgghh
                    |ccddeeffgghh
                    |iijjkkllmmnn
                    |iijjkkllmmnn
                    |ooppqqrrsstt
                    |ooppqqrrsstt
                    |uuvvwwxxyyzz
                    |uuvvwwxxyyzz
                    """.trimMargin().lines()
        val expected = ((0..9) + ('a'..'z')).map { listOf("$it$it","$it$it") }
        assertEquals(expected, splitSquares(input))
    }
    @Test
    fun `join 12x12 works`() {
        val input = ((0..9) + ('a'..'z')).map { listOf("$it$it","$it$it") }
        val expected = """
                    |001122334455
                    |001122334455
                    |66778899aabb
                    |66778899aabb
                    |ccddeeffgghh
                    |ccddeeffgghh
                    |iijjkkllmmnn
                    |iijjkkllmmnn
                    |ooppqqrrsstt
                    |ooppqqrrsstt
                    |uuvvwwxxyyzz
                    |uuvvwwxxyyzz
                    """.trimMargin().lines()
        assertEquals(expected, input.joinSquares())
    }
    @Test
    fun `join 9 4x4 to 12x12 works`() {
        val input = ((0..9) + ('a'..'b')).map { listOf("$it$it$it$it","$it$it$it$it","$it$it$it$it","$it$it$it$it") }
        val expected = """
                    |000011112222
                    |000011112222
                    |000011112222
                    |000011112222
                    |333344445555
                    |333344445555
                    |333344445555
                    |333344445555
                    |666677778888
                    |666677778888
                    |666677778888
                    |666677778888
                    |9999aaaabbbb
                    |9999aaaabbbb
                    |9999aaaabbbb
                    |9999aaaabbbb
                    """.trimMargin().lines()
        assertEquals(expected, input.joinSquares())
    }
    @Test
    fun `join 9 squares works`() {
        val input = (0..8).map { listOf("$it$it$it","$it$it$it","$it$it$it") }
        val expected = """
                    |000111222
                    |000111222
                    |000111222
                    |333444555
                    |333444555
                    |333444555
                    |666777888
                    |666777888
                    |666777888
                    """.trimMargin().lines()
        assertEquals(expected, input.joinSquares())
    }
    @Test
    fun `join 6x6 squares works`() {
        val input = (0..8).map { listOf("$it$it","$it$it") }
        val expected = """
                    |001122
                    |001122
                    |334455
                    |334455
                    |667788
                    |667788
                    """.trimMargin().lines()
        assertEquals(expected, input.joinSquares())
    }
    @Test
    fun `split 2x2 nothing splitted`() {
        val input = """
                    |01
                    |23
                    """.trimMargin().lines()
        assertEquals(listOf(input), splitSquares(input))
    }

    @Test
    fun `example 4x4 enhance to 6x6`() {
        val rule = "../.# => ##./#../..."
        val input = """
                    |#..#
                    |....
                    |....
                    |#..#
                    """.trimMargin().lines()
        val expected = """
                    |##.##.
                    |#..#..
                    |......
                    |##.##.
                    |#..#..
                    |......
                    """.trimMargin().lines()
        val art = FractalArt(listOf(rule))
        assertEquals(expected, art.enhance(input))
    }
    @Test
    fun `part1 example pixel art`() {
        val expectedIter1 = """
                    |#..#
                    |....
                    |....
                    |#..#
                    """.trimMargin().lines()
        val expectedIter2 = """
                    |##.##.
                    |#..#..
                    |......
                    |##.##.
                    |#..#..
                    |......
                    """.trimMargin().lines()
        val art = FractalArt(example)
        assertEquals(expectedIter1, art.enhance(iterations = 1))
        assertEquals(expectedIter2, art.enhance(iterations = 2))
    }
    @Test
    fun `part1 example # count`() {
        val art = FractalArt(example)
        assertEquals(12, art.enhancePixelCnt(iterations = 2))
    }
    @Test
    fun `part1 challenge # count`() {
        // 30 not right
        val art = FractalArt(challenge)
        assertEquals(136, art.enhancePixelCnt(iterations = 5))
    }
    @Test
    fun `part2 challenge # count`() {
        val art = FractalArt(challenge)
        assertEquals(1911767, art.enhancePixelCnt(iterations = 18))
    }
    @Test
    fun `part1 any challenge input matches start pixels`() {
        val rules = challenge.map { Rule(it) }.filter { it.fromSq.countPixels() == startPattern.countPixels() }
        val anyMatches = rules.firstOrNull { it.matches(startPattern)}
        assertNotNull(anyMatches)
    }

    @Test
    fun `rot90 works`() {
        val input = """
                    |013
                    |456
                    |789
                    """.trimMargin().lines()
        val expected = """
                    |740
                    |851
                    |963
                    """.trimMargin().lines()
        assertEquals(expected, input.rot90())
    }
    @Test
    fun `rot180 works`() {
        val input = """
                    |013
                    |456
                    |789
                    """.trimMargin().lines()
        val expected = """
                    |987
                    |654
                    |310
                    """.trimMargin().lines()
        assertEquals(expected, input.rot90().rot90())
    }
    @Test
    fun `rot270 works`() {
        val input = """
                    |013
                    |456
                    |789
                    """.trimMargin().lines()
        val expected = """
                    |369
                    |158
                    |047
                    """.trimMargin().lines()
        assertEquals(expected, input.rot90().rot90().rot90())
    }
    @Test
    fun `rot360 works`() {
        val input = """
                    |013
                    |456
                    |789
                    """.trimMargin().lines()
        val expected = """
                    |013
                    |456
                    |789
                    """.trimMargin().lines()
        assertEquals(expected, input.rot90().rot90().rot90().rot90())
    }
    @Test
    fun `flipV works`() {
        val input = """
                    |013
                    |456
                    |789
                    """.trimMargin().lines()
        val expected = """
                    |310
                    |654
                    |987
                    """.trimMargin().lines()
        assertEquals(expected, input.flipV())
    }
    @Test
    fun `flipH works`() {
        val input = """
                    |013
                    |456
                    |789
                    """.trimMargin().lines()
        val expected = """
                    |789
                    |456
                    |013
                    """.trimMargin().lines()
        assertEquals(expected, input.flipH())
    }
}