package day19

import org.junit.Assert.*
import org.junit.Test
import kotlin.math.max

class TubeMazeTest {

    // intellij messes with the input (removes trailing spaces in file...)
    // added x before and after one line to ensure the challenge is not changed...
    val challenge = Challenge.read(19).toMaze()

    @Test
    fun `start pos`() {
        val maze = """
            x   |
            """.trimMargin("x").lines()
        assertEquals(Pair(0,3), TubeMaze(maze).pos)
    }

    @Test
    fun `down`() {
        val maze = """
            x   | x
            x   | x
            x   | x
            x     x
            """.toMaze()
        val solver = TubeMaze(maze)
        solver.solve()
        assertEquals(Pair(2,3), solver.pos)
        assertEquals(3, solver.totalSteps)
    }

    @Test
    fun `right`() {
        val maze = """
            x   |  x
            x   |  x
            x   +- x
            """.toMaze()
        val solver = TubeMaze(maze)
        solver.solve()
        assertEquals(Pair(2,4), solver.pos)
        assertEquals(4, solver.totalSteps)
    }
    @Test
    fun `left`() {
        val maze = """
            x   |  x
            x   |  x
            x  -+ x
            """.toMaze()
        val solver = TubeMaze(maze)
        solver.solve()
        assertEquals(Pair(2,2), solver.pos)
        assertEquals(4, solver.totalSteps)
    }
    @Test
    fun `up`() {
        val maze = """
            x   |  x
            x | |  x
            x +-+  x
            """.toMaze()
        val solver = TubeMaze(maze)
        solver.solve()
        assertEquals(Pair(1,1), solver.pos)
        assertEquals(6, solver.totalSteps)
    }
    @Test
    fun `change direction at second pipe`() {
        val maze = """
            x   |  x
            x   +- x
            """.toMaze()
        val solver = TubeMaze(maze)
        solver.solve()
        assertEquals(Pair(1,4), solver.pos)
        assertEquals(3, solver.totalSteps)
    }
    @Test
    fun `bug stop also when last position is letter`() {
        val maze = """
            x   |  x
            x   A x
            """.toMaze()
        val solver = TubeMaze(maze)
        assertEquals("A", solver.solve())
        assertEquals(2, solver.totalSteps)
        assertEquals(Pair(1, 3), solver.pos)
    }
    @Test
    fun `change direction directly at letter`() {
        val maze = """
            x   |   x
            x   +A- x
            """.toMaze()
        val solver = TubeMaze(maze)
        solver.solve()
        assertEquals(Pair(1,5), solver.pos)
        assertEquals(4, solver.totalSteps)
    }

    @Test
    fun `pick up line`() {
        val maze = """
            x         |  x
            x +--+ +IH+  x
            x I  N C     x
            x D  +A+     x
            x O          x
            x +MESTIC+   x
            x        A   x
            x  -UOYET+   x
            x            x
            """.toMaze()
        val solver = TubeMaze(maze)
        val expected = "HI"/*, */+"CAN"+"I"+"DOMESTICATE"+"YOU"/*?*/
        assertEquals(expected, solver.solve())
        assertEquals(13 + expected.length, solver.totalSteps)
    }
    @Test
    fun `part1 example`() {
        val maze = """
            x     |          x
            x     |  +--+    x
            x     A  |  C    x
            x F---|----E|--+ x
            x     |  |  |  D x
            x     +B-+  +--+ x
            """.toMaze()
        val solver = TubeMaze(maze)
        assertEquals("ABCDEF", solver.solve())
    }
    @Test
    fun `part1 and part2 challenge`() {
        val solver = TubeMaze(challenge)
        //part1
        assertEquals("VEBTPXCHLI", solver.solve())
        //part2
        assertEquals(18702, solver.totalSteps)
    }

    @Test
    fun `part2 example`() {
        val maze = """
            x     |          x
            x     |  +--+    x
            x     A  |  C    x
            x F---|----E|--+ x
            x     |  |  |  D x
            x     +B-+  +--+ x
            """.toMaze()
        val solver = TubeMaze(maze)
        solver.solve()
        assertEquals(38, solver.totalSteps)
    }

    private fun String.toMaze() : List<String> = // remove x from front and back
            this.trimMargin("x")
                    .replace('x',' ')
                    .lines()
}