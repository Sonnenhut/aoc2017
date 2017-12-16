package day12

class DigitalPlumber {
    companion object {
        // part1
        fun groupSize(search: Int, pipes: List<String>) : Int {
            return groupPipes(pipes).first { set: Set<Int> -> set.contains(search) }.size
        }

        // part2
        fun groupCount(pipes: List<String>) : Int {
            return groupPipes(pipes).size
        }

        fun groupPipes(pipes : List<String>) : Set<Set<Int>> {
            val flatGroups = flatGroups(pipes)

            return flatGroups.fold(listOf<Set<Int>>()) { acc, group ->
                var newAcc = acc
                val toCombine: List<Set<Int>> = acc.filter { it.containsAnyOf(group) }
                // remove all elements that can be combined
                toCombine.forEach { elem: Set<Int> -> newAcc = newAcc.minus<Set<Int>>(elem) }
                // combine everything that can be combined
                val combined = toCombine.fold (group) { acc, other -> acc + other}
                newAcc = newAcc.plus<Set<Int>>(combined)
                newAcc
            }.toSet()
        }

        private fun flatGroups(pipes: List<String>): List<Set<Int>> {
            return pipes.map {
                val split = it.split("<->")
                val caller = split[0].trim().toInt()
                val callees = split[1].split(",").map { it.trim().toInt() }
                (callees + caller).toSet()
            }
        }

        private fun Set<Int>.containsAnyOf(other: Set<Int>): Boolean = this.any { other.contains(it) }
    }
}