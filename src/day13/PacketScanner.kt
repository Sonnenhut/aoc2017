package day13

data class Scanner(val depth: Int, val range: Int) {
    companion object {
        fun from(text: String) : Scanner {
            val (depth:String, range: String) = text.split(": ")
            return Scanner(depth.toInt(), range.toInt())
        }
    }
    private val positions : List<Int> by lazy {
        var res = listOf<Int>()
        var reverse = false
        var pos = -1

        while (res.size < range || res.last() != 0) {
            // move one forward/backward
            if (reverse) pos-- else pos++
            // toggle turn switch at startend
            if (pos == range - 1) reverse = true
            res += pos
        }
        res
    }

    fun posAt(tick: Int) : Int {
        val check = when {
            positions.size == 1 -> 0
            tick > positions.lastIndex -> tick % positions.lastIndex
            else -> tick
        }
        return positions[check]
    }

    fun scan(tick: Int): Pair<Boolean, Int> {
        val pos = posAt(tick)
        val caught =  pos == 0
        val severity = if(caught) depth * range else 0
        return caught to severity
    }
}

class PacketScanner {
    companion object {
        fun hitchCaught(layers: List<Scanner> = listOf(), wait : Int = 0) : Boolean {
            return layers.fold (false) { acc: Boolean, scanner: Scanner ->
                acc || scanner.scan(scanner.depth + wait).first
            }
        }

        fun hitchScore(layers: List<Scanner> = listOf()) : Int {
            return layers.fold (0) { acc: Int, scanner: Scanner ->
                val (_, severity) = scanner.scan(scanner.depth)
                acc + severity
            }
        }

        fun perfectStartingTime(layers: List<Scanner>): Int {
            // brute force!
            var res = -1
            for(wait in 0..Int.MAX_VALUE) {
                val caught = hitchCaught(layers = layers, wait = wait)
                if(!caught) {
                    res = wait
                    break
                }
            }
            return res
        }
    }
}