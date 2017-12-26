package day25

data class Instruction(val write: Boolean, val direction : Int, val nextState: Char)
data class State(val instructions : Map<Boolean,Instruction>)

class TuringMachine(val beginState : Char, val steps : Int, val states : Map<Char, State>) {
    companion object {
        fun valueOf(raw : String) : TuringMachine {
            val lines = raw.trimMargin().lines()
            val beginState = lines[0][15]
            val steps = lines[1].split(" ")[5].toInt()
            val states = (lines - lines.take(3)).chunked(9).map {
                val falseInstruction = Instruction(it[2][22] == '1', // value
                        if("left." == it[3].takeLast(5)) -1 else +1, // cursor move
                        it[4][26]) // next state
                val trueInstruction = Instruction(it[6][22] == '1', // value
                        if("left." == it[7].takeLast(5)) -1 else +1, // cursor move
                        it[8][26]) // next state
                it[0][9] to State(mapOf(false to falseInstruction, true to trueInstruction))
            }.toMap()
            return TuringMachine(beginState, steps, states)
        }
    }

    private val tape = mutableSetOf<Int>()

    fun run() {
        var stateCode = beginState
        var cursor = 0
        repeat(steps) {
            val state = states[stateCode]!!
            val tapeVal = cursor in tape
            // execute instruction
            val instruction = state.instructions[tapeVal]!!
            if(instruction.write) tape.add(cursor) else tape.remove(cursor)

            // move the cursor
            cursor += instruction.direction
            stateCode = instruction.nextState
        }
    }
    fun chksum() : Int = tape.size
}