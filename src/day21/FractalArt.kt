package day21

import kotlin.math.sqrt

// Worst. Code. Ever.

val startPattern ="""
                    |.#.
                    |..#
                    |###
                    """.trimMargin().lines()

class Rule(raw : String) {
    //The artist explains that sometimes, one must rotate *or* flip the input pattern to find a match.
    private val rawSplit = raw.split(" => ").map { it.split('/') }
    val fromSq = rawSplit[0]
    val toSq = rawSplit[1]

    // possible rotations
    private val fromSq90 = fromSq.rot90()
    private val fromSq180 = fromSq90.rot90()
    private val fromSq270 = fromSq180.rot90()

    // combine rotations and flips
    private val rotations = listOf(fromSq, fromSq90, fromSq180, fromSq270)
    private val allToRotations = rotations + rotations.flatMap{ listOf(it.flipH(), it.flipV()) }

    fun matches(square: List<String>) : Boolean {
        var res = false
        if(square.size == fromSq.size &&
                allToRotations.any { it == square })
            res = true
        return res
    }

    operator fun invoke(square : List<String>) : List<String> =
            if(matches(square)) toSq
            else square
}

class FractalArt(rawRules: List<String>) {
    private val rules = rawRules.map { Rule(it) }

    fun enhance(input: List<String> = startPattern, iterations : Int = 1) : List<String>{
        val splitted = splitSquares(input)
        val enhanced = splitted.map { subSquare ->
            // find a rule and invoke it
            val convert = rules.first { it.matches(subSquare) }
            convert(subSquare)
        }
        val res = enhanced.joinSquares()

        return if(iterations > 1)
            enhance(res, iterations - 1)
        else
            res
    }

    fun enhancePixelCnt(input: List<String> = startPattern, iterations : Int) : Int {
        val enhanced =  enhance(input, iterations)
        return enhanced.countPixels()
    }
}
fun List<String>.countPixels() : Int = this.fold(0) {
    acc, str -> acc + str.count { it == '#' }
}
fun List<String>.flipH() = (this.lastIndex downTo 0).map { this[it] }
fun List<String>.flipV() = this.map { it.reversed() }
fun List<String>.rot90() = (0 ..this.lastIndex).map { col ->
    (this.lastIndex downTo 0).map { row ->
        this[row][col]
    }.joinToString("")
}

fun List<List<String>>.joinSquares() : List<String> {
    val splitSize = this[0].size
    val dimension = (0 until splitSize)
    val chunkSize = sqrt(size.toDouble()).toInt()

    return this.chunked(chunkSize).flatMap { rowOfSubsquares ->
        // merge all lines of subsquares that are in the same row (chunk)
        dimension.map { dim ->
            rowOfSubsquares.joinToString("") { subSquare ->
                subSquare[dim]
            }
        }
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
fun splitSquares(original: List<String>) : List<List<String>> {
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
    val rowChunks = original.chunked(splitSize)

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
    val chunked = rowChunks.map { it.map { str -> str.chunked(splitSize) } }

    // now extract the col chunks
    val dimension = (0 until (original.size / splitSize)) // i.e 0 until 3 (for a 6x6 grid)
    val res : List<List<String>> = chunked.fold(listOf()) { acc, rowGroup ->
        // now take only a specific col at a time for each rowGroup
        // i.e. rowGroup(0,1) take only col 0 then take only col 1.. and so on
        acc + dimension.map { col ->
            rowGroup.map { row ->
                row[col]
            }
        }
    }
    // assert that everything went well...
    if(res.joinSquares() != original)
        throw IllegalStateException("splitted values do not match input: $original")
    return res
}