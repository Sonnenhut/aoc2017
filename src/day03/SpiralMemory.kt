package day03

fun Pair<Int, Int>.addFirst(i: Int) = Pair(this.first + i, this.second)
fun Pair<Int, Int>.addSecond(i: Int) = Pair(this.first, this.second + i)
fun Pair<Int, Int>.left() = this.addSecond(-1)
fun Pair<Int, Int>.right() = this.addSecond(1)
fun Pair<Int, Int>.top() = this.addFirst(-1)
fun Pair<Int, Int>.down() = this.addFirst(1)


abstract class SpiralMemoryBase {

    val spiral = mutableListOf(mutableListOf<Int?>())
    var currPos = Pair(0, 0)

    open fun calcNextVal(position: Pair<Int, Int>) = 0

    fun countStepsFromTo(fromInt: Int, toInt: Int): Int {
        val fromPos = findPosOfNum(fromInt)
        val toPos = findPosOfNum(toInt)
        return subtract(fromPos.first, toPos.first) + subtract(fromPos.second, toPos.second)
    }

    fun subtract(first: Int, second: Int): Int = if (first > second) first - second else second - first

    fun addBlock(times: Int = 1, untilValueFits: Int = -1): SpiralMemoryBase {
        if(untilValueFits > -1) {
            while(untilValueFits >= lookAtCurrentBlock()) {
                addBlockInternal()
            }
        } else {
            for (time in 0 until times) {
                addBlockInternal()
            }
        }
        return this
    }

    private fun addBlockInternal(): SpiralMemoryBase {
        if (currPos.first == spiral.lastIndex && currPos.second == spiral[currPos.first].lastIndex) {
            // append a new 'ring' around memory when we filled everything
            val newSize = spiral.size + 2
            // extend existing lists at the front and back
            spiral.forEach { it.add(0, 0); it.add(0) }
            // add at front at back
            spiral.add(0, createNullList(newSize))
            spiral.add(createNullList(newSize))
            // adjust pos
            currPos = Pair(currPos.first + 1, currPos.second + 1)
        }
        val right = currPos.right()
        val left = currPos.left()
        val top = currPos.top()
        val down = currPos.down()
        // move to the next free spot
        if (spiral[0].isEmpty()) {
            // don't move
            spiral[currPos.first].add(0)
        } else if (currPos.second != spiral[0].lastIndex && lookAt(right) == 0) {
            // right
            currPos = right
        } else if (currPos.first != 0 && lookAt(top) == 0) {
            // top
            currPos = top
        } else if (currPos.second != 0 && lookAt(left) == 0) {
            // left
            currPos = left
        } else if (currPos.first != spiral.lastIndex && lookAt(down) == 0) {
            // bottom
            currPos = down
        }
        // set new value
        spiral[currPos.first][currPos.second] = calcNextVal(position = currPos)

        return this
    }

    fun lookAt(pos: Pair<Int, Int>): Int {
        // look at the position. if it does not exist return 0
        return if (pos.first in 0..spiral.lastIndex && pos.second in 0..spiral[0].lastIndex)
            spiral[pos.first][pos.second]!!
        else
            0
    }

    fun lookAtCurrentBlock(): Int {
        return lookAt(currPos)
    }

    fun createNullList(size: Int): MutableList<Int?> {
        return (0 until size).map { 0 }.toMutableList()
    }

    fun findPosOfNum(toFind: Int): Pair<Int, Int> {
        // first look at the current position
        val first = spiral.indexOfFirst { it.contains(toFind) }
        val second = spiral[first].indexOfFirst { it == toFind }
        return Pair(first, second)
    }
}

/**
 * Spiral memory wich counts up an integer for each next block / value
 */
class SpiralMemoryPartOne : SpiralMemoryBase() {
    private var nextVal = 1
    override fun calcNextVal(position: Pair<Int, Int>): Int {
        val res = nextVal!!
        nextVal++
        return res
    }
}

/**
 * Spiral memory wich counts up an integer for each next block / value
 */
class SpiralMemoryPartTwo : SpiralMemoryBase() {

    override fun calcNextVal(position: Pair<Int, Int>): Int {
        val sumOfSurroundings = calcSurroundingPos(position).map { lookAt(it) }.sum()

        return if (sumOfSurroundings == 0) 1 else sumOfSurroundings
    }

    private fun calcSurroundingPos(position: Pair<Int, Int>): List<Pair<Int, Int>> {
        // look at all surrounding coordinates
        // left, right, top, down (NWSE)
        // but also: top.left, top.right, down.left, down.right (NW, NE, SW, SE)

        return listOf(position).flatMap {
            listOf(it.left(), it.right(), it.top(), it.down(), it.top().left(), it.top().right(), it.down().left(), it.down().right())
        }

    }
}


object SpiralMemoryBeforeRefactoringPartOne {
    val possibleBases = (9999 downTo 1).filter { it -> it % 2 != 0 }

    fun countStepsToMiddle(from: Int): Int {
        val lenOfSide = findLengthOfSide(from)
        val middlePos = findMiddleOfMemory(lenOfSide)
        val fromPos = findPosOfNum(from)
        return subtract(fromPos.first, middlePos.first) + subtract(fromPos.second, middlePos.second)
    }

    fun subtract(first: Int, second: Int): Int = if (first > second) first - second else second - first

    fun findLengthOfSide(numToSearch: Int): Int {
        val sideIdx = when (numToSearch) {
            1 -> possibleBases.lastIndex
        // find the side in which the number fits
            else -> possibleBases.map { it * it }.indexOfFirst { numToSearch > it } - 1
        }
        return possibleBases[sideIdx]
    }

    fun findMiddleOfMemory(lengthOfSide: Int): Pair<Int, Int> {
        var res = lengthOfSide / 2
        return Pair(res, res)
    }

    fun findPosOfNum(toSearch: Int): Pair<Int, Int> {
        val sideLength = findLengthOfSide(toSearch)
        val sideLengthIdx = sideLength - 1
        val highest = sideLength * sideLength
        return when (toSearch) {
        // exactly the highest value
            highest -> Pair(sideLength - 1, sideLength - 1)
        // .. on lower side, the number is between highest and highest minus one side
            in (highest - sideLengthIdx * 1)..highest -> Pair(sideLengthIdx, highest - toSearch)
        // .. on left side, the number is between highest and highest minus two sides
            in (highest - sideLengthIdx * 2)..highest -> Pair(highest - sideLengthIdx - toSearch, 0)
        // .. on top side, the number is between highest and highest minus three sides
            in (highest - sideLengthIdx * 3)..highest -> Pair(0, highest - sideLengthIdx * 2 - toSearch)
        // .. on right side, the number is between highest and highest minus all 4 sides
            in (highest - sideLengthIdx * 4)..highest -> Pair(highest - sideLengthIdx * 3 - toSearch, sideLengthIdx)
            else -> Pair(-1, -1)
        }
    }
}