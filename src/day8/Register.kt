package day8

class Register {
    private val regex = """(.*) (.*) (.*) if (.*) (.*) (.*)""".toRegex()
    private val _register = mutableMapOf<String,Int>()
    var highestKnown = 0
        private set

    fun exec(instruction: String = "", instructions: List<String> = listOf()) {
        if(instruction.isNotEmpty()) execInternal(instruction)
        else instructions.forEach { execInternal(it) }
    }

    private fun execInternal(instruction: String) {
        val match = regex.matchEntire(instruction)
        if(match == null || match.groupValues.size < 7)
            throw IllegalStateException("Unparseable instruction: $instruction")

        val (_, regTarget, op, operandStr, regToChk, check, toChkStr) = match.groupValues
        val operand = operandStr.toInt()
        val toChk = toChkStr.toInt()

        val condition = when(check) {
            "==" -> this[regToChk] == toChk
            "!=" -> this[regToChk] != toChk
            ">=" -> this[regToChk] >= toChk
            "<=" -> this[regToChk] <= toChk
            ">" -> this[regToChk] > toChk
            "<" -> this[regToChk] < toChk
            else -> throw IllegalStateException("unknown check: '$regToChk $check $toChk'")
        }

        if(condition) {
            when (op) {
                "inc" -> this[regTarget] += operand
                "dec" -> this[regTarget] -= operand
            }
        }
    }

    fun max() : Int {
        return _register.values.max()?:0
    }
    operator fun get(name: String) : Int =  _register.getOrPut(name, {0})
    private operator fun set(name: String, new : Int): Int? {
        highestKnown = kotlin.math.max(highestKnown, new)
        return _register.put(name, new)
    }
    private operator fun List<String>.component6() = this[5] // needed for destructing a List
    private operator fun List<String>.component7() = this[6] // needed for destructing a List
}