package day25

import org.junit.Assert.*
import org.junit.Test

class HaltingProblemTest {

    val challenge = Challenge.read(25)

    @Test
    fun `initialize turing machine`() {
        val raw = """
            |Begin in state A.
            |Perform a diagnostic checksum after 6 steps.
            |
            |In state A:
            |  If the current value is 0:
            |    - Write the value 1.
            |    - Move one slot to the right.
            |    - Continue with state B.
            |  If the current value is 1:
            |    - Write the value 0.
            |    - Move one slot to the left.
            |    - Continue with state B.
            |
            |In state B:
            |  If the current value is 0:
            |    - Write the value 1.
            |    - Move one slot to the left.
            |    - Continue with state A.
            |  If the current value is 1:
            |    - Write the value 1.
            |    - Move one slot to the right.
            |    - Continue with state A.
            """
        val tm = TuringMachine.valueOf(raw)
        val expectedStateA = State(mapOf(0 to Instruction(1,+1,'B'), 1 to Instruction(0,-1,'B')))
        val expectedStateB = State(mapOf(0 to Instruction(1,-1,'A'),1 to Instruction(1,+1,'A')))
        assertEquals('A',tm.beginState)
        assertEquals(6,tm.steps)
        assertEquals(expectedStateA,tm.states['A'])
        assertEquals(expectedStateB,tm.states['B'])
    }
    @Test
    fun `part1 example checksum`() {
        val raw = """
            |Begin in state A.
            |Perform a diagnostic checksum after 6 steps.
            |
            |In state A:
            |  If the current value is 0:
            |    - Write the value 1.
            |    - Move one slot to the right.
            |    - Continue with state B.
            |  If the current value is 1:
            |    - Write the value 0.
            |    - Move one slot to the left.
            |    - Continue with state B.
            |
            |In state B:
            |  If the current value is 0:
            |    - Write the value 1.
            |    - Move one slot to the left.
            |    - Continue with state A.
            |  If the current value is 1:
            |    - Write the value 1.
            |    - Move one slot to the right.
            |    - Continue with state A.
            """
        val tm = TuringMachine.valueOf(raw)
        tm.run()
        assertEquals(3,tm.chksum())
    }
    @Test
    fun `part1 challenge`() {
        val tm = TuringMachine.valueOf(challenge)
        tm.run()
        assertEquals(4387,tm.chksum())
    }
}