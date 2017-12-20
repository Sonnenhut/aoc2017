package day18

import org.junit.Assert.*
import org.junit.Test

class DuetTest {

    val challenge = Challenge.read(18).lines()

    fun expected(vararg pairs: Pair<Char, Int>) : Map<Char,Long> =
            pairs.map { Pair(it.first,it.second.toLong()) }.toMap()

    @Test
    fun `simple set`() {
        val operations = """
            |set a 100
            |set b a
            |snd a
            |rcv a
        """.trimMargin().lines()
        val machine = DanceMachinePart1(operations)
        machine.danceUntilReceive()
        assertEquals(expected('a' to 100, 'b' to 100), machine.state)
    }

    @Test
    fun `simple add`() {
        val operations = """
            |add a 100
            |add b a
            |snd a
            |rcv a
        """.trimMargin().lines()
        val machine = DanceMachinePart1(operations)
        machine.danceUntilReceive()
        assertEquals(expected('a' to 100, 'b' to 100), machine.state)
    }

    @Test
    fun `simple multiply`() {
        val operations = """
            |set a 100
            |set b 2
            |mul a b
            |mul a 2
            |snd a
            |rcv a
        """.trimMargin().lines()
        val machine = DanceMachinePart1(operations)
        machine.danceUntilReceive()
        assertEquals(expected('a' to 400, 'b' to 2), machine.state)
    }
    @Test
    fun `bug only jump when value is greater than zero`() {
        val operations = """
            |set a -1
            |jgz a 2
            |set b 1
            |snd a
            |rcv a
        """.trimMargin().lines()
        val machine = DanceMachinePart1(operations)
        machine.danceUntilReceive()
        assertEquals(expected('a' to -1, 'b' to 1), machine.state)
    }

    @Test
    fun `bug jump can take a value or register`() {
        val operations = """
            |set a 1
            |jgz 1 2
            |set b 1
            |jgz a 2
            |set c 1
            |snd a
            |rcv a
        """.trimMargin().lines()
        val machine = DanceMachinePart1(operations)
        machine.danceUntilReceive()
        assertEquals(expected('a' to 1), machine.state)
    }
    @Test
    fun `simple recover played sound`() {
        val operations = """
            |set a 1
            |snd a
            |rcv a
        """.trimMargin().lines()
        val machine = DanceMachinePart1(operations)
        machine.danceUntilReceive()
        assertEquals(1L, machine.lastSound)
    }
    @Test
    fun `simple jump until value is played`() {
        val operations = """
            |snd a
            |rcv a
            |set a 99
            |jgz a -3
        """.trimMargin().lines()
        val machine = DanceMachinePart1(operations)
        machine.danceUntilReceive()
        assertEquals(99L, machine.lastSound)
    }
    @Test
    fun `part1 example`() {
        val operations = """
            |set a 1
            |add a 2
            |mul a a
            |mod a 5
            |snd a
            |set a 0
            |rcv a
            |jgz a -1
            |set a 1
            |jgz a -2
        """.trimMargin().lines()

        val machine = DanceMachinePart1(operations)
        machine.danceUntilReceive()
        assertEquals(4L, machine.lastSound)
    }
    @Test
    fun `part1 challenge`() {
        val machine = DanceMachinePart1(challenge)
        machine.danceUntilReceive()
        assertEquals(7071L, machine.lastSound)
    }
    @Test
    fun `part2 example`() {
        val operations = """
            |snd 1
            |snd 2
            |snd p
            |rcv a
            |rcv b
            |rcv c
            |rcv d
        """.trimMargin().lines()

        val instructor = DanceInstructor(operations)
        instructor.duet()
        assertEquals(expected('a' to 1, 'b' to 2, 'c' to 1, 'd' to 0, 'p' to 0), instructor.zero.state)
        assertEquals(expected('a' to 1, 'b' to 2, 'c' to 0, 'd' to 0, 'p' to 1), instructor.one.state)
        assertEquals(3, instructor.totalSends(0))
        assertEquals(3, instructor.totalSends(1))
    }
    @Test
    fun `part2 challenge`() {
        // 508 too low
        val instructor = DanceInstructor(challenge)
        instructor.duet()
        assertEquals(8001, instructor.totalSends(1))
        assertEquals(8128, instructor.totalSends(0))
    }
}