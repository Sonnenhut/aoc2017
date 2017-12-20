package day19

class TubeMaze(val maze: List<String>) {
    var pos = Pair(0, maze[0].indexOf('|'))
    var steps = listOf<Pair<Int, Int>>()
    val totalSteps: Int get() = steps.size // minus starting point

    fun solve(): String {
        var res = ""
        var run = true
        var lastPos = pos
        var nextDirection: (Pair<Int, Int>) -> Pair<Int, Int> = Pair<Int, Int>::down

        while (run) {
            val currVal = maze.safeGet(pos)

            if (currVal in 'A'..'Z') res += currVal // store sweet letter
            // maintain direction if we meet something familiar
            nextDirection = if (steps.contains(pos)) nextDirection
            else {
                val dirFun = findNextDirection(pos, lastPos, nextDirection)
                if (dirFun == null) {
                    run = false
                    { Pair(lastPos.first, lastPos.second) }
                } else dirFun
            }

            steps += pos
            lastPos = pos
            pos = nextDirection(pos)
        }
        return res
    }

    private fun findNextDirection(position: Pair<Int, Int>, ignore: Pair<Int, Int>, preferred: (Pair<Int, Int>) -> Pair<Int, Int>): ((Pair<Int, Int>) -> Pair<Int, Int>)? {
        val directions = listOf(preferred, Pair<Int, Int>::right, Pair<Int, Int>::left, Pair<Int, Int>::down, Pair<Int, Int>::up)
        return directions.firstOrNull {
            val next = it(position)
            next != ignore && maze.safeGet(next) != ' '
        }
    }
}

// copied / modified from Day 14
fun List<String>.safeGet(coord: Pair<Int, Int>): Char {
    var (row, col) = coord
    return if (0 <= row && row <= this.lastIndex && 0 <= col && col <= this[0].lastIndex) {
        this[row][col]
    } else ' '
}

fun Pair<Int, Int>.left() = Pair(this.first, this.second - 1)
fun Pair<Int, Int>.right() = Pair(this.first, this.second + 1)
fun Pair<Int, Int>.up() = Pair(this.first - 1, this.second)
fun Pair<Int, Int>.down() = Pair(this.first + 1, this.second)