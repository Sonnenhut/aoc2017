package day16

import org.junit.Assert.*
import org.junit.Test

class PermutationPromenadeTest {

    val example = "s1,x3/4,pe/b".split(',')
    val challenge = Challenge.read(16).split(',')

    @Test
    fun `spin one`() {
        val actions = listOf("s1")
        val expected = (listOf('p') + ('a'..'o')).joinToString("")
        assertEquals(expected, dance(actions))
    }
    @Test
    fun `spin 4`() {
        val actions = listOf("s4")
        val expected = (('m'..'p') + ('a'..'l')).joinToString("")
        assertEquals(expected, dance(actions))
    }
    @Test
    fun `partner a and p`() {
        val actions = listOf("pa/p")
        val expected = 'p' + ('b'..'o').joinToString("") +'a'
        assertEquals(expected, dance(actions))
    }
    @Test
    fun `exchange two digit value 14 and 15`() {
        val actions = listOf("x14/15")
        val expected = ('a'..'n').joinToString("") +"po"
        assertEquals(expected, dance(actions))
    }
    @Test
    fun `exchange idx 0 and 15`() {
        val actions = listOf("x0/15")
        val expected = 'p' + ('b'..'o').joinToString("") +'a'
        assertEquals(expected, dance(actions))
    }
    @Test
    fun `part1 example`() {
        assertEquals("baedc", dance(example, pgmRange = ('a'..'e')))
    }
    @Test
    fun `part1 challenge`() {
        assertEquals("ionlbkfeajgdmphc", dance(challenge))
    }
    @Test
    fun `part2 challenge`() {
        assertEquals("fdnphiegakolcmjb", dance(challenge, loop = 1000000000))
    }
}