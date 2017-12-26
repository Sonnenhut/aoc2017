package day06

class MemoryReallocation(private val memory: List<Int>) {
    companion object {

        fun redistribute(pos: Int, memory: List<Int>) : List<Int> {
            var res = memory.toMutableList()
            var distribute = res.set(pos, 0)

            var currentPos = pos + 1

            while(distribute > 0) {
                val allocatePos = currentPos % memory.size
                res[allocatePos]++

                currentPos++
                distribute--
            }
            return res
        }
    }

    private val states = mutableMapOf<List<Int>, Int>()

    private fun calcCycles() : Pair<Int,Int> {
        var lastMemory = memory
        var res = 0

        while(!states.contains(lastMemory)) {
            // add the last known memory to a cache
            states[lastMemory] = res
            // redistribute memory
            var highest = lastMemory.indexOfFirst { it == lastMemory.max() }
            lastMemory = redistribute(highest, lastMemory)
            res++
        }
        return res to res - states[lastMemory]!!
    }

    fun calcRedistributionCycles() : Int = calcCycles().first
    fun calcDistanceSinceFromOccurrence() : Int = calcCycles().second
}