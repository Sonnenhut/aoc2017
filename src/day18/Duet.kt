package day18

import java.util.*

// part1 only receive/recover when the value of the register is not 0
class DanceMachinePart1(instrs: List<String>) : DanceMachine(instrs, receivePredicate = { it != 0L })
// part2 always receive and initialize register 'p' with id
class DanceMachinePart2(instrs: List<String>, id: Long) : DanceMachine(instrs, mapOf('p' to id), receivePredicate = { true })

open class DanceMachine(private val instructions: List<String>,
                        initialRegisters: Map<Char,Long> = mapOf(),
                        private val receivePredicate: (Long) -> Boolean) {
    var idx = 0
    private var toSend = listOf<Long>()
    private val receiveQueue = ArrayDeque<Long>()
    var registers = initialRegisters.toMutableMap()
    var instructionsExecuted = listOf<Instruction>()
    var totalSends : Int = 0
        private set
    val state:Map<Char,Long> get() = registers.toMap()
    val lastSound: Long get() = toSend.last()

    fun send(receiver : DanceMachine) : Boolean {
        return if(toSend.isNotEmpty()) {
            totalSends += toSend.size
            receiver.receiveQueue.addAll(toSend)
            toSend = listOf()
            true
        } else
            false
    }

    fun danceUntilReceive() {
        do {
            // split values from instruction and initialize register
            val instruction = Instruction(instructions[idx], registers)
            val idxOffset = execInstruction(instruction)
            idx += idxOffset
            // Continuing (or jumping) off either end of the program terminates it.
        } while (!shouldTerminate(instrSize = instructions.size, index = idx, indexOffSet = idxOffset))
    }

    private fun execInstruction(instruction: Instruction) : Int {
        var nextIdxOffset = +1

        val (operation, reg, regVal, operand) = instruction
        when(operation) {
        // operations affecting loop
            "jnz" -> if(regVal != 0L) nextIdxOffset = operand!!.toInt()
            "jgz" -> if(regVal > 0L) nextIdxOffset = operand!!.toInt()
            "rcv" -> if(receivePredicate(regVal)) {
                val receivedVal = receiveQueue.poll()
                if(receivedVal == null) {
                    nextIdxOffset = 0 // we need more input, halt
                } else {
                    registers[reg!!] = receivedVal
                }
            }
            "snd" -> { toSend += regVal }
            else -> {
                // operations affection register
                changeReg(registers, reg!!, operation, operand)
            }
        }
        instructionsExecuted += instruction
        return nextIdxOffset
    }

    private fun shouldTerminate(instrSize: Int, index : Int, indexOffSet : Int): Boolean =
            indexOffSet == 0 || index !in (0 until instrSize)
}
class Instruction(val raw : String, private val registers: MutableMap<Char, Long> = mutableMapOf<Char,Long>()) {
    val split = raw.split(" ")
    val regStr = split[1]
    val operation = split[0]
    val reg = if(regStr[0] in ('a'..'z')) regStr[0] else null
    val regVal = if(reg != null) registers.getOrPut(reg, { 0L }) else regStr.toLong()
    val operator = if(split.lastIndex == 2) registers[split[2][0]]?:split[2].toLong() else null
    /**operation*/operator fun component1(): String = operation
    /**reg      */operator fun component2(): Char? = reg
    /**regVal   */operator fun component3(): Long = regVal
    /**operator */operator fun component4(): Long? = operator
    override fun equals(other: Any?): Boolean {
        if(other !is Instruction) return false
        return raw == other.raw
    }
    override fun hashCode(): Int = raw.hashCode()
    override fun toString(): String = raw
}
private fun changeReg(reg: MutableMap<Char, Long>, register: Char, operation: String, operand: Long?) {
    // ensure nothing sneaky happens
    if(register !in 'a'..'z' || operand == null)
        throw IllegalStateException("Register name has to be alpha and operand must be non-null: register '$register'; operand '$operand'")

    when (operation) { // mutate
        "set" -> reg[register] = operand
        "add" -> reg[register] = reg[register]!! + operand
        "sub" -> reg[register] = reg[register]!! - operand
        "mul" -> reg[register] = reg[register]!! * operand
        "mod" -> reg[register] = reg[register]!! % operand
    }
}
//part2
class DanceInstructor(instruction: List<String>) {
    val zero = DanceMachinePart2(instruction, 0L)
    val one = DanceMachinePart2(instruction, 1L)
    fun duet() {
        var dataSent: Boolean
        do {
            zero.danceUntilReceive()
            zero.send(one)

            one.danceUntilReceive()
            dataSent = one.send(zero)
        } while(dataSent)
    }
    fun totalSends(id: Int) : Int = when(id) {
        0 -> zero.totalSends
        1 -> one.totalSends
        else -> -1
    }
}