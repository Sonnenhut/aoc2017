package day9

// this can also be done with a fold/reduce
class Stream {
    companion object {
        fun score(stream: String) : Int {
            return process(stream).first
        }
        fun garbage(stream: String) : Int {
            return process(stream).second
        }
        fun process(stream: String) : Pair<Int,Int> {
            val instance = Stream()
            for(char in stream) {
                when(char) {
                    '{' -> { instance.open() }
                    '}' -> { instance.close() }
                    '!' -> { instance.ignoreNext() }
                    '<' -> { instance.startGarbage() }
                    '>' -> { instance.endGarbage() }
                    else -> instance.otherValue()
                }
            }
            return instance.score to instance.garbageCnt
        }
    }

    private var isOpen = false
    private var score = 0
    private var depth = 0
    private var ignoreNext = false
    private var garbage = false
    private var garbageCnt = 0

    fun otherValue() {
        if(removeIgnore()) return
        // a value that has no effect counts as garbage
        if(garbage) garbageCnt++
    }

    private fun close() {
        if(removeIgnore()) return
        if(garbage) return otherValue()
        isOpen = false
        score += depth
        depth--
    }
    private fun open() {
        if(removeIgnore()) return
        if(garbage) return otherValue()
        depth += 1
        isOpen = true
    }
    private fun ignoreNext() {
        if(removeIgnore()) return
        ignoreNext = true
    }
    private fun startGarbage() {
        // The leading and trailing < and > don't count as garbage
        if(removeIgnore()) return
        if(garbage) otherValue() // count startGarbage as garbage when garbage is already on
        else garbage = true
    }
    private fun endGarbage() {
        // The leading and trailing < and > don't count as garbage
        if(removeIgnore()) return
        garbage = false
    }
    private fun removeIgnore() : Boolean {
        val res = ignoreNext
        ignoreNext = false
        return res
    }
}