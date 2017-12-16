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


// Archive
/**
 * Same as List.drop, but only drops the first element that matches the predicate. The rest of the list is unchanged
 */
fun <T> List<T>.dropOnce(predicate: (T) -> Boolean) : List<T> {
    var drop = true
    return this.dropWhile {
        if(drop && predicate(it)) {
            drop = false
            true
        } else {
            false
        }
    }
}
/**
 * Same as List.map, but only maps the first element that matches the predicate. The rest of the list is unchanged
 */
fun <T> List<T>.mapOnce(predicate: (T) -> Boolean, transform: (T) -> T) : List<T> {
    var map = true
    return this.map {
        if(map && predicate(it)) {
            map = false; transform(it)
        }
        else it
    }
}

fun String.opposite() : String {
    val oppOneWay = listOf('n' to 's', 'w' to 'e')
    val opps = oppOneWay + oppOneWay.map { Pair(it.second,it.first) }

    return this.map { chr ->
        val oppRule = opps.first { it.first == chr}
        oppRule.second
    }.joinToString("")
}

fun calcShortcutOld2(pathStr: String? = null, original: List<String> = listOf()) : List<String> {
    val path = pathStr?.split(",") ?: original

    return path.fold(listOf()) { acc, s ->
        // direct shorting - i.e. n,s -> <nothing>
        val directShortened = acc.dropOnce { it.opposite() == s }
        // substitute - i.e. se,n -> ne
        if(directShortened.size == acc.size) acc + s else directShortened
    }
}
fun calcShortcutOld1(pathStr: String? = null, original: List<String> = listOf()) : List<String> {
    val path = pathStr?.split(",") ?: original
    var n = 0
    var s = 0
    var nw = 0
    var se = 0
    var sw = 0
    var ne = 0

    for(dir in path) {
        when(dir) {
            "n" -> if(s > 0) s-- else n++
            "s" -> if(n > 0) n-- else s++
            "nw" -> if(se > 0) se-- else nw++
            "se" -> if(nw > 0) nw-- else se++
            "sw" -> if(ne > 0) ne-- else sw++
            "ne" -> if(sw > 0) sw-- else ne++
        }
    }
    var nStr = (0 until n).toList().map { "n" }
    var sStr = (0 until s).toList().map { "s" }
    var seStr = (0 until se).toList().map { "se" }
    var nwStr = (0 until nw).toList().map { "nw" }
    var neStr = (0 until ne).toList().map { "ne" }
    var swStr = (0 until sw).toList().map { "sw" }
    return nStr + sStr + seStr + nwStr + neStr + swStr
}