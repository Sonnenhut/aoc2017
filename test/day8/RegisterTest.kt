package day8

import org.junit.Test

import org.junit.Assert.*

class RegisterTest {

    val example =
"""b inc 5 if a > 1
a inc 1 if b < 5
c dec -10 if a >= 1
c inc -20 if c == 10""".lines()

    val challenge = Challenge.read(8).lines()

    @Test
    fun `inc a by 1`() {
        val reg = Register()
        reg.exec("a inc 1 if a == 0")
        assertEquals(1, reg["a"])
    }
    @Test
    fun `dec a by 1`() {
        val reg = Register()
        reg.exec("a dec 1 if a == 0")
        assertEquals(-1, reg["a"])
    }

    @Test
    fun `condition ==`() {
        val reg = Register()
        reg.exec("a inc 1 if a == 0")
        assertEquals(1, reg["a"])
    }

    @Test
    fun `condition !=`() {
        val reg = Register()
        reg.exec("a inc 1 if a != 0")
        assertEquals(0, reg["a"])
    }
    @Test
    fun `condition ge (greater or equal)`() {
        val reg = Register()
        reg.exec("a inc 1 if a >= 0")
        assertEquals(1, reg["a"])
    }
    @Test
    fun `condition le (less or equal)`() {
        val reg = Register()
        reg.exec("a inc 1 if a <= 0")
        assertEquals(1, reg["a"])
    }
    @Test
    fun `condition greater`() {
        val reg = Register()
        reg.exec("a inc 1 if a > 0")
        assertEquals(0, reg["a"])
    }
    @Test
    fun `condition less`() {
        val reg = Register()
        reg.exec("a inc 1 if a < 0")
        assertEquals(0, reg["a"])
    }
    @Test
    fun `example max value`() {
        val reg = Register()
        reg.exec(instructions = example)
        assertEquals(1, reg.max())
    }
    @Test
    fun `max value with only changed negative and unchanged register is 0`() {
        val reg = Register()
        val input = "a inc 100 if a != 0\nb dec 100 if a == 0".lines()
        reg.exec(instructions = input)
        assertEquals(0, reg.max()) // a should be 0, which is max
    }
    @Test
    fun `part1 challenge max value`() {
        val reg = Register()
        reg.exec(instructions = challenge)
        assertEquals(6828, reg.max())
    }
    @Test
    fun `part2 challenge max value`() {
        val reg = Register()
        reg.exec(instructions = challenge)
        assertEquals(7234, reg.highestKnown)
    }
}