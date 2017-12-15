package day10

import kotlin.math.min

class KnotHash(baseLen: Int = 256) {
    // salt for input challenge #2
    var salt = listOf(17, 31, 73, 47, 23)

    // ascii representation of input
    private var state = (0 until baseLen).toList()

    // variables for hashing
    var posIdx = 0
        private set
    var skipSize = 0
        private set

    init {
        printState()
    }

    fun knot1(single: Int? = null, multiple: List<Int> = listOf(), idx: Int = posIdx) : List<Int> {
        val instructions = if(single != null) listOf(single) else multiple
        instructions.forEach { this.oneKnot(it,idx) }
        return state
    }

    fun knot2(baseInstr: String) : List<Int> {
        val instrASCII = baseInstr.map { it.toASCII() } + salt
        (0 until 64).forEach {
            instrASCII.forEach { oneKnot(it) }
        }
        return state
    }

    private fun oneKnot(instruction: Int, idx: Int = posIdx) : List<Int> {
        state = state.substitueAtIdx(idx, state.subListCascading(idx, instruction).asReversed())
        posIdx = (skipSize + instruction + posIdx) % state.size
        skipSize += 1
        printState()
        return state
    }

    fun toIntList() : List<Int> = state

    fun toHex() : String = state.dense16().toHex()

    private fun printState() {
        println(state.joinToString(" "))
    }
}

fun List<Int>.toHex() : String {
    return this.map { Integer.toHexString(it) }.joinToString("") { it.padStart(2,'0') }
}

/**
 * takes blocks of 16 elements from the list and denses them
 */
fun List<Int>.dense16() : List<Int> {
    val power = 16
    // the list must be a power of 16..
    if(this.size % power != 0)
        IllegalStateException("Unable to convert list to dense,it's size '$size' is not a power of '$power'")
    return this.windowed(power,power).map { it.dense() }
}

/**
 * use bitwise XOR to flatten a list of Bytes
 */
fun List<Int>.dense() : Int {
    return this.reduce { acc, i -> acc.xor(i) }
}

/**
 * Utility function for better readability
 */
fun Char.toASCII() : Int{
    return this.toInt()
}

/**
 * same as List.subList, but starts taking elements from the front, when the end is reached
 */
fun <T> List<T>.subListCascading(idxStart: Int, amount: Int) : List<T>{
    var res = listOf<T>()
    for(idx in idxStart..min(idxStart+amount-1,lastIndex)) {
        res += this[idx]
    }

    res += this.take(amount - res.size)
    return res
}

/**
 * creates a new list where elements from another list are inserted at
 * a given index, wrap to the front if not all items could be substituted at the back
 */
fun <T> List<T>.substitueAtIdx(idx: Int, other: List<T>): List<T> {
    val rangeToReplace = (idx..(idx+other.lastIndex))
    var substituted = listOf<T>()

    // distribute items at the rear
    var res = this.mapIndexed { thisIdx, item ->
        if(thisIdx in rangeToReplace) {
            val toSubstitute = other[thisIdx - idx]
            substituted += toSubstitute
            toSubstitute
        }
        else item
    }
    // distribute items at start (if still something to distribute is left)
    val notDistributed = other - substituted
    res = if(notDistributed.isNotEmpty()) res.mapIndexed { thisIdx, item ->
        if(thisIdx in 0..notDistributed.lastIndex) notDistributed[thisIdx] else item
    } else res
    return res
}