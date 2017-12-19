package day16

/**
 * "premature optimization is the root of all evil"
 */

// Optimization 1: Use MutableList instead of String
fun dance(actions: List<String>, loop : Int = 1, pgmRange : CharRange = ('a'..'p') ) : String {
    val res = pgmRange.toMutableList()

    // Optimization 2: Create operations once. Execute them multiple times.
    val ops = actions.map { it.toOperation() }

    // Optimization 3: Dance only as long as there is a recurring sequence
    var danceOrders = sortedSetOf<String>()
    do {
        ops.forEach { it(res) }
        val danceOrder = res.joinToString("")

    } while(!danceOrders.add(danceOrder))
    // Optimization 3: Dance only as long as there is a recurring sequence
    return danceOrders.toList()[(loop-1) % danceOrders.size]
}

private fun String.toOperation(): (MutableList<Char>) -> Unit {
    val action = this
    val opInfo = action.substring(1)
    return when (action.first()) {
        's' -> { // Spin
            val cnt = opInfo.toInt()
            val op = { operand: MutableList<Char> -> operand.spin(cnt) }
            op
        }
        'p' -> { // Partner
            val (one, other) = opInfo.split('/').map { it[0] }
            val op = { operand: MutableList<Char> -> operand.exchange(one, other) }
            op
        }
        'x' -> { // Exchange
            val (oneIdx, otherIdx) = opInfo.split('/').map { it.toInt() }
            val op = { operand: MutableList<Char> -> operand.partner(oneIdx, otherIdx) }
            op
        }
        else -> {
            { Unit }
        }
    }
}

private fun MutableList<Char>.exchange(one:Char, other:Char) {
    var oneIdx = this.indexOf(one)
    var otherIdx = this.indexOf(other)
    this.partner(oneIdx,otherIdx)
}

private fun MutableList<Char>.partner(oneIdx:Int, otherIdx:Int) {
    val oneTmp = this[oneIdx]
    this[oneIdx] = this[otherIdx]
    this[otherIdx] = oneTmp
}

private fun MutableList<Char>.spin(cnt: Int) {
    val toSpin = this.takeLast(cnt)
    this.removeAll(toSpin)
    this.addAll(0,toSpin)
}