package day22

import day22.Constants.CLEAN
import day22.Constants.FLAGGED
import day22.Constants.INFECTED
import day22.Constants.RULE_1
import day22.Constants.RULE_2
import day22.Constants.WEAKENED

object Constants {
    val INFECTED = '#'
    val CLEAN = '.'
    val FLAGGED = 'F'
    val WEAKENED = 'W'
    // Rulesets
    val RULE_1 = mapOf(CLEAN to INFECTED, INFECTED to CLEAN)
    val RULE_2 = mapOf(CLEAN to WEAKENED, WEAKENED to INFECTED, INFECTED to FLAGGED, FLAGGED to CLEAN)
}

class VirusOne(grid: InfiniteGrid) : VirusCarrier(grid, RULE_1)
class VirusTwo(grid: InfiniteGrid) : VirusCarrier(grid, RULE_2)

open class VirusCarrier(private val grid : InfiniteGrid, private val ruleSet: Map<Char,Char>) {
    var currPos = grid.center
    var facing: (Point) -> Point = Point::north
    var infectionCount = 0

    fun burst(times : Int = 1) {
        (0 until times).forEach {
            burstInternal()
            if(it > 0 && it % 1000000 == 0)
                println("burst no. $it")
        }
    }

    private fun burstInternal() {
        // what does the position look like
        val peek = grid.peek(currPos)

        facing = when (peek) {
            CLEAN -> currPos.leftDirection(facing)
            WEAKENED -> facing
            INFECTED -> currPos.rightDirection(facing)
            FLAGGED -> currPos.backDirection(facing)
            else -> throw IllegalStateException("Unable to find new direction with '$peek' at current position '$currPos'")
        }
        val mutated = ruleSet[peek]!!
        if(INFECTED == mutated) infectionCount++
        grid.change(currPos, mutated)
        // The virus carrier moves forward one node in the direction it is facing.
        currPos = facing(currPos)

    }
}

class InfiniteGrid(private val fakeGrid : MutableMap<Point, Char>) {
    companion object {
        fun valueOf(list : List<String>) : InfiniteGrid {
            val gridPositionsOriginal = list.mapIndexed { idxRow, str ->
                str.mapIndexed { idxCol, c -> Point(idxCol,idxRow) to c
                }
            }.flatMap { it }//.toMap()
            val gridInterpretation = gridPositionsOriginal.map { convertListPointToInfinite(list.size, it.first) to it.second }.toMap().toMutableMap()
            return InfiniteGrid(gridInterpretation)
        }
        private fun convertListPointToInfinite(dimension: Int, iPoint: Point) : Point {
            val center = Point(dimension / 2, dimension / 2)
            var res = Point(iPoint.x - center.x, center.y - iPoint.y)
            return res
        }
    }
    val center = Point(0,0)
    fun peek(point: Point) : Char {
        return fakeGrid.getOrPut(point, { CLEAN } )
    }
    fun checkInfected(point: Point) : Boolean = '#' == peek(point)
    fun change(iPoint: Point, newVal : Char) {
        fakeGrid[iPoint] = newVal
    }
    override fun equals(other: Any?): Boolean {
        if(other !is InfiniteGrid) return false
        // compare all elements that are not CLEAN
        val thisEntries = fakeGrid.filter { it.value != CLEAN }
        val otherEntries = other.fakeGrid.filter { it.value != CLEAN }
        return thisEntries == otherEntries
    }
    override fun toString(): String {
        return fakeGrid.toString()
    }
}

fun List<String>.toInfiniteGrid() = InfiniteGrid.valueOf(this)
fun String.toInfiniteGrid() = this.trimMargin().lines().toInfiniteGrid()
fun List<String>.toVirus1() = VirusOne(InfiniteGrid.valueOf(this))
fun List<String>.toVirus2() = VirusTwo(InfiniteGrid.valueOf(this))
fun String.toVirus1() = this.trimMargin().lines().toVirus1()
fun String.toVirus2() = this.trimMargin().lines().toVirus2()

data class Point(val x: Int, val y: Int) {
    fun west() = Point(x-1,y)
    fun east() = Point(x+1,y)
    fun north() = Point(x,y+1)
    fun south() = Point(x,y-1)

    fun leftDirection(facing : (Point) -> Point) : (Point) -> Point = when(facing) {
        Point::north -> Point::west
        Point::west -> Point::south
        Point::south -> Point::east
        Point::east -> Point::north
        else -> throw IllegalStateException("Cannot move left if not facing in a direction")
    }
    fun rightDirection(facing : (Point) -> Point) : (Point) -> Point = when(facing) {
        Point::north -> Point::east
        Point::east -> Point::south
        Point::south -> Point::west
        Point::west -> Point::north
        else -> throw IllegalStateException("Cannot move left if not facing in a direction")
    }
    fun backDirection(facing : (Point) -> Point) : (Point) -> Point = when(facing) {
        Point::north -> Point::south
        Point::east -> Point::west
        Point::south -> Point::north
        Point::west -> Point::east
        else -> throw IllegalStateException("Cannot move back if not facing in a direction")
    }
}