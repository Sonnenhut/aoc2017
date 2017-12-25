package day25

class TuringMachine(val beginState : Char, val steps : Int, val states : Map<Char, State>) {
    companion object {
        fun valueOf(raw : String) : TuringMachine {
            val lines = raw.trimMargin().lines()
            val beginState = lines[0][15]
            val steps = lines[1].replace(" steps.","").split(" ").last().toInt()
            val states = (lines - lines.take(3)).chunked(9).map { it ->
                val stateChr = it[0][9]
                val falseWrite = it[2][22] - '0'
                val falseDirection = if("left." == it[3].takeLast(5)) -1 else +1
                val falseNextState = it[4][26]
                val falseInstruction = Instruction(falseWrite,falseDirection,falseNextState)
                val trueWrite = it[6][22] - '0'
                val trueDirection = if("left." == it[7].takeLast(5)) -1 else +1
                val trueNextState = it[8][26]
                val trueInstruction = Instruction(trueWrite,trueDirection,trueNextState)
                stateChr to State(mapOf(0 to falseInstruction, 1 to trueInstruction))
            }.toMap()
            return TuringMachine(beginState,steps, states)
        }
    }
    private val tape = mutableMapOf<Int, Int>().withDefault { 0 }

    fun run() {
        var stateCode = beginState
        var cursor = 0
        for(idx in (0 until steps)) {
            val state = states[stateCode]!!
            val tapeVal = tape.getOrDefault(cursor, 0)
            // execute instruction
            val instruction = state.instructions[tapeVal]!!
            tape[cursor] = instruction.write

            cursor += instruction.direction
            stateCode = instruction.nextState
        }
    }
    fun chksum() : Int = tape.values.filter { it == 1 }.sum()
}
data class Instruction(val write: Int, val direction : Int, val nextState: Char)
data class State(val instructions : Map<Int,Instruction>)