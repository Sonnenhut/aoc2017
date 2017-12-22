package day11

import kotlin.math.max

val directions = listOf("n","ne","se","s","sw","nw")
val directionsArranged : Map<String, List<String>> by lazy {
    // ne to ne,se,s,sw,nw,n
    // se to se,s,sw,nw,n,ne
    // etc..
    directions.mapIndexed { index, dir ->
        val withoutDir = directions.subList(0, index)
        val withDir = directions.subList(index, directions.size)
        dir to withDir + withoutDir // swap it that the current dir is at the front
    }.toMap()
}

//   \ n  /
// nw +--+ ne
//   /    \
// -+      +-
//   \    /
// sw +--+ se
//   / s  \
class HexGrid {
    companion object {
        fun calcShortestSteps(pathStr: String) : Int {
            return calcShortcut(pathStr).size
        }

        fun calcFurthestDistance(pathStr: String? = null, toShorten : List<String> = listOf()) : Int {
            val path = pathStr?.split(",") ?: toShorten

            // go through all combinations progressively, shorten after every step
            // this will take a loong time...
            var maxDistance = 0
            var progressivePath = listOf<String>()
            (0..path.lastIndex).forEach {
                progressivePath += path[it]
                progressivePath = calcShortcut(toShorten = progressivePath)
                maxDistance = max(maxDistance, progressivePath.size)
            }
            return maxDistance
        }

        fun calcShortcut(pathStr: String? = null, toShorten : List<String> = listOf()) : List<String> {
            val path = pathStr?.split(",")?: toShorten

            var lastFolded = foldPathOnce(path).toPath()
            var folded = foldPathOnce(lastFolded).toPath()

            // fold as long as fold has an effect
            while(folded.size != lastFolded.size) {
                lastFolded = folded
                folded = foldPathOnce(lastFolded).toPath()
            }
            return folded
        }

        // Optimization: store accumulator as map instead of flat list
        // part2 before: 1min, now 7s
        private fun foldPathOnce(path : List<String>) : Map<String,Int> {
            val accStart = directions.map { it to 0 }.toMap().toMutableMap()
            return path.fold(accStart) { acc, current ->

                var accKeys = acc.filter { it.value > 0 }.keys

                // see what directions we got
                var newAcc = acc.toMutableMap()

                for(key in accKeys) {
                    val substitution = findSubstitution(current, key)
                    if(substitution == "") {
                        newAcc.dec(key)
                        break
                    } else if(substitution != current) {
                        // no recursion here, would take too long
                        newAcc.dec(key)
                        newAcc.inc(substitution)
                        break
                    }
                }
                if(newAcc != acc) newAcc else { acc.put(current, acc[current]!! +1); acc }
            }
        }

        /**
         * find a substitution between first and second.
         * Possible return values
         *  - first element if nothing could be substituted
         *  - new substitution if first and second can be substituted
         *  - empty string if first and second cancel each other out
         */
        private fun findSubstitution(first: String, second : String) : String {
            val viewFromFirst = directionsArranged[first]!!
            return when (viewFromFirst.indexOf(second)) {
            // drop, when 3 steps apart from each other
            // for example: [n],ne,se,[s],sw,nw -> <remove>
                3 -> ""
            // combine it if 2 steps apart from each other
            // for example: [n],ne,[se],s,sw,nw -> ne
                2 -> viewFromFirst[1]
            // combine it if 4 steps apart from each other
            // for example: [n],ne,se,s,[sw],nw -> nw
                4 -> viewFromFirst[5]
                else -> first
            }
        }

        private fun MutableMap<String, Int>.inc(key: String) {
            this[key] = this[key]!! + 1
        }
        private fun MutableMap<String, Int>.dec(key: String) {
            this[key] = this[key]!! - 1
        }

        private fun Map<String,Int>.toPath() : List<String> {
            return this.flatMap { mapElement -> (0 until mapElement.value).map { mapElement.key } }
        }
    }
}
