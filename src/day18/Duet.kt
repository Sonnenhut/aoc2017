package day18

import java.util.*

// part1 only receive/recover when the value of the register is not 0
class DanceMachinePart1(instrs: List<String>) : DanceMachine(instrs, receivePredicate = { it != 0L })
// part2 always receive and initialize register 'p' with id
class DanceMachinePart2(instrs: List<String>, id: Long) : DanceMachine(instrs, mapOf('p' to id), { true })

open class DanceMachine(private val instructions: List<String>, initialRegisters: Map<Char,Long> = mapOf(), private val receivePredicate: (Long) -> Boolean) {
    private var idx = 0
    private var toSend = listOf<Long>()
    private var registers = initialRegisters.toMutableMap()
    private val receiveQueue = ArrayDeque<Long>()
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
        do { val receive = dance(instructions[idx])
        } while (!receive)
    }

    private fun dance(instr: String) : Boolean {
        var receiveMore = false
        // split values from instruction and initialize register
        val (operation, reg, regVal, operand) = Instruction(instr, registers)
        when(operation) {
        // operations affecting loop
            "jgz" -> if(regVal > 0L) idx += operand!!.toInt() else idx++
            "rcv" -> if(receivePredicate(regVal)) {
                val receivedVal = receiveQueue.poll()
                if(receivedVal == null) {
                    receiveMore = true // we need more input, halt
                } else {
                    registers[reg!!] = receivedVal
                    idx++
                }
            } else idx++
            "snd" -> { toSend += regVal; idx++ }
            else -> {
                // operations affection register
                changeReg(registers, reg!!, operation, operand)
                idx++
            }
        }
        return receiveMore
    }
}
class Instruction(raw : String, private val registers: MutableMap<Char, Long>) {
    val split = raw.split(" ")
    private val regStr = split[1]
    /**operation*/operator fun component1(): String = split[0]
    /**reg      */operator fun component2(): Char? = if(regStr[0] in ('a'..'z')) regStr[0] else null
    /**regVal   */operator fun component3(): Long = if(component2() != null) registers.getOrPut(component2()!!, { 0L }) else split[1].toLong()
    /**operator */operator fun component4(): Long? = if(split.lastIndex == 2) registers[split[2][0]]?:split[2].toLong() else null
}
private fun changeReg(reg: MutableMap<Char, Long>, register: Char, operation: String, operand: Long?) {
    // ensure nothing sneaky happens
    if(register !in 'a'..'z' || operand == null)
        throw IllegalStateException("Register name has to be alpha and operand must be non-null: register '$register'; operand '$operand'")

    when (operation) { // mutate
        "set" -> reg[register] = operand
        "add" -> reg[register] = reg[register]!! + operand
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