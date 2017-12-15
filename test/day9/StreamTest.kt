package day9

import org.junit.Test

import org.junit.Assert.*

class StreamTest {

    val challenge = Challenge.read(9)

    @Test
    fun `score of empty group`() {
        assertEquals(1, Stream.score("{}"))
    }
    @Test
    fun `score one inner groups`() {
        assertEquals(3, Stream.score("{{}}"))
    }
    @Test
    fun `score three cascading groups`() {
        assertEquals(6, Stream.score("{{{}}}"))
    }
    @Test
    fun `score two inner groups`() {
        assertEquals(5, Stream.score("{{},{}}"))
    }
    @Test
    fun `score complex groups`() {
        assertEquals(16, Stream.score("{{{},{},{{}}}}"))
    }
    @Test
    fun `score one group with garbage`() {
        assertEquals(1, Stream.score("{<a>,<a>,<a>,<a>}"))
    }
    @Test
    fun `score four inner groups with garbage`() {
        assertEquals(9, Stream.score("{{<ab>},{<ab>},{<ab>},{<ab>}}"))
    }
    @Test
    fun `score one group with simple garbage `() {
        assertEquals(1, Stream.score("{<{}>}"))
    }
    @Test
    fun `score one group with garbage and ignore`() {
        assertEquals(1, Stream.score("{<{!>{}<}>}"))
    }
    @Test
    fun `score group cancel cancel in garbage`() {
        assertEquals(3, Stream.score("{<{!!>{}<}>}"))
    }
    @Test
    fun `score ignore invalid characters`() {
        assertEquals(3, Stream.score("{!a{!a}}"))
    }
    @Test
    fun `score start multiple garbage has no effect`() {
        assertEquals(1, Stream.score("{<<<<<<<>}"))
    }
    @Test
    fun `score ignore before openclose garbage`() {
        assertEquals(1, Stream.score("{<!<>}"))
    }
    @Test
    fun `part1 example`() {
        assertEquals(1, Stream.score("{}"))
        assertEquals(6, Stream.score("{{{}}}"))
        assertEquals(5, Stream.score("{{},{}}"))
        assertEquals(16, Stream.score("{{{},{},{{}}}}"))
        assertEquals(1, Stream.score("{<a>,<a>,<a>,<a>}"))
        assertEquals(9, Stream.score("{{<ab>},{<ab>},{<ab>},{<ab>}}"))
        assertEquals(9, Stream.score("{{<!!>},{<!!>},{<!!>},{<!!>}}"))
        assertEquals(3, Stream.score("{{<a!>},{<a!>},{<a!>},{<ab>}}"))
    }
    @Test
    fun `part1 example two times next to each other is double the original result`() {
        assertEquals(2, Stream.score("{}{}"))
        assertEquals(12, Stream.score("{{{}}}{{{}}}"))
        assertEquals(10, Stream.score("{{},{}}{{},{}}"))
        assertEquals(32, Stream.score("{{{},{},{{}}}}{{{},{},{{}}}}"))
        assertEquals(2, Stream.score("{<a>,<a>,<a>,<a>}{<a>,<a>,<a>,<a>}"))
        assertEquals(18, Stream.score("{{<ab>},{<ab>},{<ab>},{<ab>}}{{<ab>},{<ab>},{<ab>},{<ab>}}"))
        assertEquals(18, Stream.score("{{<!!>},{<!!>},{<!!>},{<!!>}}{{<!!>},{<!!>},{<!!>},{<!!>}}"))
        assertEquals(6, Stream.score("{{<a!>},{<a!>},{<a!>},{<ab>}}{{<a!>},{<a!>},{<a!>},{<ab>}}"))
    }
    @Test
    fun `part1 challenge`() {
        assertEquals(14212, Stream.score(challenge))
    }
    @Test
    fun `garbage simple`() {
        assertEquals(1, Stream.garbage("{<a>}"))
    }
    @Test
    fun `garbage leading and trailing does not count`() {
        assertEquals(0, Stream.garbage("{<>}"))
    }
    @Test
    fun `garbage open tag inside garbage counts as garbage`() {
        assertEquals(1, Stream.garbage("{<<>}"))
    }
    @Test
    fun `garbage cancel and cancelled does not count`() {
        assertEquals(0, Stream.garbage("{<!<>}"))
    }
    @Test
    fun `garbage characters outside garbage does not count as garbage`() {
        assertEquals(0, Stream.garbage("{{},{}}"))
    }
    @Test
    fun `part2 example`() {
        assertEquals(0, Stream.garbage("{<>}"))
        assertEquals(17, Stream.garbage("{<random characters>}"))
        assertEquals(3, Stream.garbage("{<<<<>}"))
        assertEquals(2, Stream.garbage("{<{!>}>}"))
        assertEquals(0, Stream.garbage("{<!!>}"))
        assertEquals(0, Stream.garbage("{<!!!>>}"))
        assertEquals(10, Stream.garbage("""{<{o"i!a,<{i<a>}"""))
    }
    @Test
    fun `part2 challenge`() {
        assertEquals(6569, Stream.garbage(challenge))
    }

}