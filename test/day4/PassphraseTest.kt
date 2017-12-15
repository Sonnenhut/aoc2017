package day4

import org.junit.Test

import org.junit.Assert.*

class PassphraseTest {

    private val challenge = Challenge.read(4).lines()
    
    @Test
    fun `part1 aa is valid`() {
        assertTrue(PassphrasePartOne("aa").valid())
    }

    @Test
    fun `part1 aa bb cc dd aa is not valid`() {
        assertFalse(PassphrasePartOne("aa bb cc dd aa").valid())
    }

    @Test
    fun `part1 aa bb cc dd aaa is  valid`() {
        assertTrue(PassphrasePartOne("aa bb cc dd aaa").valid())
    }

    @Test
    fun `part1 count valid aa bb, aa aa is 1`() {
        val input = listOf(PassphrasePartOne("aa bb"), PassphrasePartOne("aa aa"))
        assertEquals(1, PassphraseBase.countValidPassphrase(input))
    }

    @Test
    fun `part1 challenge count`() {
        val input = challenge.map { PassphrasePartOne(it) }
        assertEquals(337, PassphraseBase.countValidPassphrase(input))
    }

    @Test
    fun `part2 oi is anagram of io`() {
        assertTrue("oi".isAnagramOf("io"))
        assertTrue("io".isAnagramOf("oi"))
    }

    @Test
    fun `part2 oiii is no anagram of oooi`() {
        assertFalse("oiii".isAnagramOf("oooi"))
        assertFalse("oooi".isAnagramOf("oiii"))
    }

    @Test
    fun `part2 abcde is anagram of ecdab`() {
        assertTrue("abcde".isAnagramOf("ecdab"))
        assertTrue("ecdab".isAnagramOf("abcde"))
    }

    @Test
    fun `part2 a is no anagram of aa`() {
        assertFalse("a".isAnagramOf("aa"))
        assertFalse("aa".isAnagramOf("a"))
    }

    @Test
    fun `part2 abcde fghij is valid`() {
        assertTrue(PassphrasePartTwo("abcde fghij").valid())
    }

    @Test
    fun `part2 abcde xyz ecdab is not valid`() {
        assertFalse(PassphrasePartTwo("abcde xyz ecdab").valid())
    }

    @Test
    fun `part2 a ab abc abd abf abj is valid`() {
        assertTrue(PassphrasePartTwo("a ab abc abd abf abj").valid())
    }

    @Test
    fun `part2 aa aa aa is not valid`() {
        assertFalse(PassphrasePartTwo("aa aa aa").valid())
    }

    @Test
    fun `part2 aa aa ab is not valid`() {
        assertFalse(PassphrasePartTwo("aa aa aa").valid())
    }

    @Test
    fun `part2 example`() {
        assertTrue(PassphrasePartTwo("abcde fghij").valid())
        assertFalse(PassphrasePartTwo("abcde xyz ecdab").valid())
        assertTrue(PassphrasePartTwo("a ab abc abd abf abj").valid())
        assertTrue(PassphrasePartTwo("iiii oiii ooii oooi oooo").valid())
        assertFalse(PassphrasePartTwo("oiii ioii iioi iiio").valid())
    }

    @Test
    fun `part2 challenge`() {
        val input = challenge.map { PassphrasePartTwo(it) }
        assertEquals(231, PassphraseBase.countValidPassphrase(input))
    }

}