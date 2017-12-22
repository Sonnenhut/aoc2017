package day22

import org.junit.Assert.*
import org.junit.Test

class SportificaVirusTest {

    private val challenge = Challenge.read(22)
    private val example ="""
                |..#
                |#..
                |...
                """
    private val exampleGrid = example.toInfiniteGrid()
    private val exampleVirusOne = example.toVirus1()
    private val exampleVirusTwo = example.toVirus2()

    @Test
    fun `center is always is 0-0`() {
        assertEquals(Point(0,0), InfiniteGrid.valueOf(listOf("333")).center)
    }
    @Test
    fun `checkInfected works`() {
        val grid = exampleGrid
        // use positions that are relative to the center position
        assertFalse(grid.checkInfected(Point(-1,1)))
        assertFalse(grid.checkInfected(Point(0,1)))
        assertTrue(grid.checkInfected(Point(1,1))) // upper right
        assertTrue(grid.checkInfected(Point(-1,0))) // middle left
        assertFalse(grid.checkInfected(Point(0,0)))
        assertFalse(grid.checkInfected(Point(1,0)))
        assertFalse(grid.checkInfected(Point(-1,-1)))
        assertFalse(grid.checkInfected(Point(0,-1)))
        assertFalse(grid.checkInfected(Point(1,-1)))
    }
    @Test
    fun `change grid works`() {
        val grid = exampleGrid
        val expected = """
                    |123
                    |456
                    |789
                    """.toInfiniteGrid()
        grid.change(Point(-1,1),'1')
        grid.change(Point(0,1),'2')
        grid.change(Point(1,1),'3')
        grid.change(Point(-1,0),'4')
        grid.change(Point(0,0),'5')
        grid.change(Point(1,0),'6')
        grid.change(Point(-1,-1),'7')
        grid.change(Point(0,-1),'8')
        grid.change(Point(1,-1),'9')
        assertEquals(expected, grid)
    }
    @Test
    fun `VirusOne one burst`() {
        val grid = exampleGrid
        val expected = """
                    |..#
                    |##.
                    |...
                    """.toInfiniteGrid()
        val virus = VirusOne(grid)
        virus.burst()
        assertEquals(expected, grid)
        assertEquals(Point(-1, 0), virus.currPos)
        assertEquals(Point::west, virus.facing)
    }

    @Test
    fun `infinite grid can insert values outside of original grid`() {
        val grid = exampleGrid
        val expected = """
                    |.....
                    |...#.
                    |.#..X
                    |.....
                    |.....
                    """.toInfiniteGrid()
        grid.change(Point(2,0), 'X')
        assertEquals(expected, grid)
    }
    @Test
    fun `part1 example grid changes after 7 bursts accordingly`() {
        val grid = exampleGrid
        val expected = """
                    |.....
                    |#..#.
                    |###..
                    |.....
                    |.....
                    """.toInfiniteGrid()
        val virus = VirusOne(grid)
        virus.burst(times = 7)
        assertEquals(expected, grid)
    }
    @Test
    fun `part1 example 70 bursts`() {
        val grid = exampleGrid
        val expected = """
                    |.....##..
                    |....#..#.
                    |...#....#
                    |..#.#...#
                    |..#.#..#.
                    |.....##..
                    |.........
                    |.........
                    |.........
                    """.toInfiniteGrid()
        val virus = VirusOne(grid)
        virus.burst(times = 70)
        assertEquals(expected, grid)
    }
    @Test
    fun `part1 example of 70 bursts 41 were infections`() {
        val virus = exampleVirusOne
        virus.burst(times = 70)
        assertEquals(41, virus.infectionCount)
    }
    @Test
    fun `part1 example of 10000 bursts 5587 were infections`() {
        val grid = exampleGrid
        val virus = VirusOne(grid)
        virus.burst(times = 10000)
        assertEquals(5587, virus.infectionCount)
    }
    @Test
    fun `part1 challenge of 10000 bursts`() {
        val grid = challenge.toInfiniteGrid()
        val virus = VirusOne(grid)
        virus.burst(times = 10000)
        assertEquals(5399, virus.infectionCount)
    }
    @Test
    fun `part2 example 1 burst`() {
        val grid = exampleGrid
        val expected = """
                    |..#
                    |#W.
                    |...
                    """.toInfiniteGrid()
        val virus = VirusTwo(grid)
        virus.burst()
        assertEquals(expected, grid)
    }
    @Test
    fun `part2 example 2 bursts`() {
        val grid = exampleGrid
        val expected = """
                    |..#
                    |FW.
                    |...
                    """.toInfiniteGrid()
        val virus = VirusTwo(grid)
        virus.burst(times = 2)
        assertEquals(expected, grid)
    }
    @Test
    fun `part2 example 5 bursts`() {
        val grid = exampleGrid
        val expected = """
                    |.....
                    |WW.#.
                    |WFW..
                    |.....
                    |.....
                    """.toInfiniteGrid()
        val virus = VirusTwo(grid)
        virus.burst(times = 5)
        assertEquals(expected, grid)
    }
    @Test
    fun `part2 example 6 bursts`() {
        val grid = exampleGrid
        val expected = """
                    |.....
                    |WW.#.
                    |W.W..
                    |.....
                    |.....
                    """.toInfiniteGrid()
        val virus = VirusTwo(grid)
        virus.burst(times = 6)
        assertEquals(expected, grid)
    }
    @Test
    fun `part2 example 7 bursts`() {
        val grid = exampleGrid
        val expected = """
                    |.....
                    |WW.#.
                    |#.W..
                    |.....
                    |.....
                    """.toInfiniteGrid()
        val virus = VirusTwo(grid)
        virus.burst(times = 7)
        assertEquals(expected, grid)
    }
    @Test
    fun `part2 example 100 bursts result in 26 infections`() {
        val virus = exampleVirusTwo
        virus.burst(times = 100)
        assertEquals(26, virus.infectionCount)
    }
    @Test
    fun `part2 example 10000000 bursts result 2511944 infections`() {
        val virus = exampleVirusTwo
        virus.burst(times = 10000000)
        assertEquals(2511944, virus.infectionCount)
    }
    @Test
    fun `part2 challenge 10000000 bursts result X infections`() {
        val virus = challenge.toVirus2()
        virus.burst(times = 10000000)
        assertEquals(2511776, virus.infectionCount)
    }
}