package day12

class DigitalPlumber {
    companion object {

        fun groupSize(search: Int, pipes: List<String>) : Int {
            return groupPipes(pipes).first { set: Set<Int> -> set.contains(search) }.size
        }
        fun groupCount(pipes: List<String>) : Int {
            return groupPipes(pipes).size
        }

        fun groupPipes(pipes : List<String>) : Set<Set<Int>> {
            val flatGroups = pipes.map {
                val itSimple = it.split("<->")
                val caller = itSimple[0].trim()
                val callees = itSimple[1].split(",").map { it.trim() }
                (callees + caller).map { it.toInt() }.toSet()
            }

            val joinedGroups = flatGroups.fold(listOf<Set<Int>>()) { acc, group ->
                //reduce { acc, set -> acc + set }
                val toCombine: List<Set<Int>> = acc.filter { it.containsAnyOf(group) }
                var res = acc
                // remove all elements that can be combined
                toCombine.forEach { elem: Set<Int> -> res = res.minus<Set<Int>>(elem) }
                // combine everything that can be combined
                val combined = toCombine.fold (group) { acc, other -> acc + other}
                res = res.plus<Set<Int>>(combined)
                res
            }.toSet()

            return joinedGroups
        }

        private fun Set<Int>.containsAnyOf(other: Set<Int>): Boolean = this.any { other.contains(it) }
    }
}