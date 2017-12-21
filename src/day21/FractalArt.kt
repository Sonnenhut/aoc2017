package day21

import kotlin.math.sqrt

// Worst. Code. Ever.

val startPattern = ".#./..#/###".split('/').asSquare()

fun String.toRule() : Rule {
    //The artist explains that sometimes, one must rotate *or* flip the input pattern to find a match.
    val rawSplit = this.split(" => ").map { it.split('/') }
    return Rule(Square(rawSplit[0]), Square(rawSplit[1]))
}
class Rule(val fromSq: Square, val toSq: Square) {
    // combine rotations and flips
    private val rotations = listOf(fromSq,fromSq.rot90(), fromSq.rot90(2), fromSq.rot90(3))
    private val allToRotations = rotations + rotations.flatMap { listOf(it.flipH(), it.flipV()) }

    fun matches(square: Square) : Boolean {
        var res = false
        if(square.size == fromSq.size &&
                allToRotations.any { it == square })
            res = true
        return res
    }

    fun apply(square : Square) : Square =
            if(matches(square)) toSq
            else square
}

// Alias for a List<String> just that I don't get insane! :(
open class Square(input: List<String>) : ArrayList<String>(input) {
    fun plusRight(other: Square) : Square = this.mapIndexed { idx, str -> str + other[idx] }.asSquare()
    fun plusBottom(other: Square) : Square = (this + other).asSquare()

    fun countPixels() : Int = this.fold(0) {
        acc, str -> acc + str.count { it == '#' }
    }
    fun flipH() = (this.lastIndex downTo 0).map { this[it] }.asSquare()
    fun flipV() = this.map { it.reversed() }.asSquare()
    fun rot90(times: Int = 1) : Square {
        val res = (0 ..this.lastIndex).map { col ->
            (this.lastIndex downTo 0).map { row ->
                this[row][col]
            }.joinToString("")
        }.asSquare()
        return if(times > 1) res.rot90(times - 1)
        else res
    }

}
fun List<String>.asSquare() = Square(this)
fun List<List<String>>.asSquareList() = this.map { it.asSquare() }
fun String.toSquare() = this.trimMargin().lines().asSquare()

class FractalArt(rawRules: List<String>) {
    private val rules = rawRules.map { it.toRule() }

    fun enhance(input: Square = startPattern, iterations : Int = 1) : Square {
        val splitted = splitSquares(input)
        val enhanced = splitted.map { subSquare ->
            // find a rule and invoke it
            val rule = rules.first { it.matches(subSquare) }
            rule.apply(subSquare)
        }
        val res = enhanced.joinSquares()

        return if(iterations > 1)
            enhance(res, iterations - 1)
        else
            res
    }

    fun enhancePixelCnt(input: Square = startPattern, iterations : Int) : Int {
        val enhanced =  enhance(input, iterations)
        return enhanced.countPixels()
    }
}
fun List<Square>.joinSquares() : Square {
    val chunkSize = sqrt(size.toDouble()).toInt()

    return chunked(chunkSize).fold(listOf()) { acc: List<Square>, squareChunk: List<Square> ->
        // merge multiple squares in the same row
        val rectangle: Square = squareChunk.reduce { sqAcc, square -> sqAcc.plusRight(square)}
        acc + listOf(rectangle)
    }.reduce { acc, square ->
        // merge rows of rectangules to one square
        acc.plusBottom(square)
    }
}

/**
 * split into following order:
 * 0|1|2..
 * -+-+-..
 * 3|4|5..
 * -+-+-..
 * 6|7|8..
 */
fun splitSquares(original: Square) : List<Square> {
    val splitSize = if(original.size % 2 == 0) 2 else 3
    /* chunk the rows
     * ......
     * ......
     * ------
     * ......
     * ......
     * ------
     * ......
     * ......
     */
    val rowChunks = original.chunked(splitSize).asSquareList()

    /* also chunk the cols:
     *    col
     * 0  1  2
     * ..|..|.. 0
     * ..|..|..
     * --+--+--
     * ..|..|.. 1  row
     * ..|..|..
     * --+--+--
     * ..|..|.. 2
     * ..|..|..
     */
    val chunked = rowChunks.map { it.map { str -> str.chunked(splitSize) }.asSquareList() }

    // now extract the col chunks
    val dimension = (0 until (original.size / splitSize)) // i.e 0 until 3 (for a 6x6 grid)
    val res : List<Square> = chunked.fold(listOf()) { acc, rowGroup ->
        // now take only a specific col at a time for each rowGroup
        // i.e. rowGroup(0,1) take only col 0 then take only col 1.. and so on
        acc + dimension.map { col ->
            rowGroup.map { row ->
                row[col]
            }.asSquare()
        }
    }
    // assert that everything went well...
    if(res.joinSquares() != original)
        throw IllegalStateException("splitted values do not match input: $original")
    // return a square
    return res
}