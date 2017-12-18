package day14

import day10.KnotHash
import java.util.*

/**
 * Highvalue, meaning a square is used
 */
val HI = '#'
/**
 * Lowvalue, meaning a square is not used
 */
val LO = '.'

class DiskFragmentation {
    companion object {
        fun toBinaryStr(hex: String): String {
            val hexes = if (hex.length == 1) listOf(hex) else hex.toList().map { it + "" }
            val bytes = hexes.map { it.toInt(16).toByte() }.toByteArray()
            val bitsets = bytes.map { BitSet.valueOf(byteArrayOf(it)) }
            return bitsets.fold("") { acc, bitSet ->
                acc + bitSet.toHiLoString()
            }
        }
    

        fun diskRow(key: String): String {
            val hasher = KnotHash()
            hasher.knot2(key)
            return toBinaryStr(hasher.toHex())
        }

        fun diskRows(baseKey: String): List<String> {
            return (0..127).map { diskRow(baseKey + "-" + it) }
        }

        fun usedSquares(baseKey: String): Int {
            return diskRows(baseKey).fold(0) { acc: Int, s: String ->
                acc + s.count { it == HI }
            }
        }

        fun markRegionRec(map: MutableList<String>, coord: Pair<Int, Int>, region: Char): MutableList<String> {
            var newMap = map
            newMap[coord.first] = newMap[coord.first].changeChar(coord.second, region)
            val other = listOf(coord.left(), coord.right(), coord.up(), coord.down())
                    .filter { map.safeGet(it) == HI }
            other.forEach { markRegionRec(newMap, it, region) }
            return newMap
        }

        fun countRegions(baseKey: String): Int {
            var map = diskRows(baseKey).toMutableList()
            var regions = listOf('0')

            // go through all elements in the map, set the regions
            for (row in 0..map.lastIndex) {
                for (col in 0..map[row].lastIndex) {
                    val pos = Pair(row, col)
                    val posVal = map.safeGet(pos)
                    if (posVal == HI) {
                        regions += regions.last() + 1
                        markRegionRec(map, pos, regions.last())
                    }
                }
            }
            return (regions - '0').size
        }

    }
}


fun List<String>.safeGet(coord: Pair<Int, Int>): Char {
    var res = "."
    var (row, col) = coord
    return if (0 <= row && row <= this.lastIndex && 0 <= col && col <= this[0].lastIndex) {
        this[row][col]
    } else '.'
}

fun String.changeChar(idx: Int, replacement: Char): String {
    return this.substring(0 until idx) + replacement + this.substring(idx + 1 until length)
}

fun BitSet.toHiLoString(): String = (3 downTo 0).joinToString("") {
    "" + if (this[it]) HI else LO
}

fun Pair<Int, Int>.left() = Pair(this.first, this.second - 1)
fun Pair<Int, Int>.right() = Pair(this.first, this.second + 1)
fun Pair<Int, Int>.up() = Pair(this.first - 1, this.second)
fun Pair<Int, Int>.down() = Pair(this.first + 1, this.second)