package day05

abstract class JumpInstructionsBase(val instructions : List<Int>) {
    fun executeInstructions(print: Boolean = true) : Int{
        val ins = instructions.toMutableList()
        var pos = 0
        var count = 0
        while (pos in 0..ins.lastIndex) {
            // inc offset
            val offset = ins[pos]
            ins[pos] = calcNewOffset(offset)
            // set new position according to offset
            pos += offset
            // print
            if(print) printIns(pos, ins)
            // next iteration
            count++
        }
        return count
    }

    abstract fun calcNewOffset(current : Int): Int

    fun printIns(pos: Int, ins: Iterable<Int>) {
        val toPrint = ins.mapIndexed { idx, it -> if(idx == pos) "($it)" else "$it" }
                            .joinToString(separator = " ")
        println(toPrint)
    }
}

class JumpInstructionsPartOne(instructions : List<Int>) : JumpInstructionsBase(instructions) {
    override fun calcNewOffset(current: Int): Int = current + 1

}

class JumpInstructionsPartTwo(instructions : List<Int>) : JumpInstructionsBase(instructions) {
    override fun calcNewOffset(current: Int) : Int {
        return if (current >= 3) current - 1 else current + 1
    }

}