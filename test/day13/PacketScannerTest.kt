package day13

import day13.PacketScanner.Companion.hitchCaught
import day13.PacketScanner.Companion.hitchScore
import day13.PacketScanner.Companion.perfectStartingTime
import org.junit.Assert.*
import org.junit.Test

class PacketScannerTest {

    val challenge = Challenge.read(13).lines().map { Scanner.from(it) }

    @Test
    fun `posAt works 0to3 for 3 is 1`() {
        val scanner = Scanner.from("0: 3")
        assertEquals(0, scanner.posAt(0))
        assertEquals(1, scanner.posAt(1))
        assertEquals(2, scanner.posAt(2))
        assertEquals(1, scanner.posAt(3))
    }
    @Test
    fun `posAt works 0to3 for bigger than 3`() {
        val scanner = Scanner.from("0: 3")
        assertEquals(0, scanner.posAt(4))
        assertEquals(1, scanner.posAt(5))
        assertEquals(2, scanner.posAt(6))
        assertEquals(1, scanner.posAt(7))
        assertEquals(0, scanner.posAt(8))
        assertEquals(1, scanner.posAt(9))
        assertEquals(2, scanner.posAt(10))
        assertEquals(1, scanner.posAt(11))
        assertEquals(0, scanner.posAt(12))
    }

    @Test
    fun `scanner with range 1 does not move at all`() {
        val scanner = Scanner.from("0: 1")
        assertEquals(0, scanner.posAt(0))
        assertEquals(0, scanner.posAt(1))
        assertEquals(0, scanner.posAt(2))
        assertEquals(0, scanner.posAt(3))
        assertEquals(0, scanner.posAt(4))
    }
    @Test
    fun `scanner 2to4 caught at 0, 5, 10`() {
        val scanner = Scanner.from("2: 4")
        assertEquals(true to 8, scanner.scan(0))
        assertEquals(true to 8, scanner.scan(6))
        assertEquals(true to 8, scanner.scan(12))
    }

    @Test
    fun `part1 severity of 0to1,1to1,2to1`() {
        var scanners = listOf("0: 1", "1: 1","2: 1").map{ Scanner.from(it) }
        assertEquals(3, hitchScore(scanners))
    }
    @Test
    fun `part1 severity of 0to1,1to1,2to2`() {
        val scanners = listOf("0: 1", "1: 1","2: 2").map{ Scanner.from(it) }
        assertEquals(5, hitchScore(scanners))
    }
    @Test
    fun `part1 example`() {
        val input = """
        |0: 3
        |1: 2
        |4: 4
        |6: 4""".trimMargin().lines().map { Scanner.from(it) }
        assertEquals(24, hitchScore(input))
    }
    @Test
    fun `part1 example isCaught`() {
        val input = """
        |0: 3
        |1: 2
        |4: 4
        |6: 4""".trimMargin().lines().map { Scanner.from(it) }
        assertEquals(true, hitchCaught(input))
    }
    @Test
    fun `part2 example`() {
        val input = """
        |0: 3
        |1: 2
        |4: 4
        |6: 4""".trimMargin().lines().map { Scanner.from(it) }
        assertEquals(10, perfectStartingTime(input))
    }
    @Test
    fun `part1 challenge`() {
        assertEquals(632, hitchScore(challenge))
    }
    @Test
    fun `part2 challenge`() {
        assertEquals(3849742, perfectStartingTime(challenge))
    }
}
