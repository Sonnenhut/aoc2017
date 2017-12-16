package day11

import day11.HexGrid.Companion.calcFurthestDistance
import day11.HexGrid.Companion.calcShortcut
import day11.HexGrid.Companion.calcShortestSteps
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class HexGridTest {
    private val none = listOf<String>()
    private val challenge = Challenge.read(11)

    @Test
    fun `no shortcut for {n}`() {
        assertEquals("n".toPath(), calcShortcut("n"))
    }
    @Test
    fun `no shortcut for {n,n}`() {
        assertEquals("n,n".toPath(), calcShortcut("n,n"))
    }
    @Test
    fun `shortcut for {n,s} is {}`() {
        assertEquals(none, calcShortcut("n,s"))
        assertEquals(none, calcShortcut("n,n,s,s"))
        assertEquals(none, calcShortcut("n,s,n,s"))
    }
    @Test
    fun `shortcut for {s,n} is {}`() {
        assertEquals(none, calcShortcut("s,n"))
        assertEquals(none, calcShortcut("s,s,n,n"))
        assertEquals(none, calcShortcut("s,n,s,n"))
    }

    @Test
    fun `shortcut for {nw,se} is {}`() {
        assertEquals(none, calcShortcut("nw,se"))
        assertEquals(none, calcShortcut("nw,nw,se,se"))
        assertEquals(none, calcShortcut("nw,se,nw,se"))
    }
    @Test
    fun `shortcut for {se,nw} is {}`() {
        assertEquals(none, calcShortcut("se,nw"))
        assertEquals(none, calcShortcut("se,se,nw,nw"))
        assertEquals(none, calcShortcut("se,nw,se,nw"))
    }
    @Test
    fun `shortcut for {ne,sw} is {}`() {
        assertEquals(none, calcShortcut("ne,sw"))
        assertEquals(none, calcShortcut("ne,ne,sw,sw"))
        assertEquals(none, calcShortcut("ne,sw,ne,sw"))
    }
    @Test
    fun `shortcut for {nw,s} is {sw}`() {
        assertEquals("sw".toPath(), calcShortcut("nw,s"))
    }
    @Test
    fun `short as much as possible`() {
        assertEquals("n,n,n,ne".toPath(), calcShortcut("n,n,n,n,n,n,ne,s,s,s"))
    }

    @Test
    fun `drop once works`() {
        assertEquals(listOf(1,0,2,0,3),listOf(0,1,0,2,0,3).dropOnce { it == 0 })
    }
    @Test
    fun `map once works`() {
        assertEquals(listOf(9,1,0,2,0,3), listOf(0,1,0,2,0,3).mapOnce({true},{9}))
        assertEquals(listOf(0,1,0,2,0,9), listOf(0,1,0,2,0,3).mapOnce({it == 3},{9}))
    }
    @Test
    fun `directions are correctly arranged`() {
        assertEquals("n,ne,se,s,sw,nw".toPath(), directionsArranged["n"])
        assertEquals("ne,se,s,sw,nw,n".toPath(), directionsArranged["ne"])
        assertEquals("se,s,sw,nw,n,ne".toPath(), directionsArranged["se"])
        assertEquals("s,sw,nw,n,ne,se".toPath(), directionsArranged["s"])
        assertEquals("sw,nw,n,ne,se,s".toPath(), directionsArranged["sw"])
        assertEquals("nw,n,ne,se,s,sw".toPath(), directionsArranged["nw"])
    }
    @Test
    fun `opposite strings n,s s,n ne,sw se,nw`() {
        assertEquals("s", "n".opposite())
        assertEquals("n", "s".opposite())
        assertEquals("se", "nw".opposite())
        assertEquals("nw", "se".opposite())
        assertEquals("ne", "sw".opposite())
        assertEquals("sw", "ne".opposite())
    }
    @Test
    fun `shortcut for {ne,s} is {se}`() {
        assertEquals("se".toPath(), calcShortcut("ne,s"))
    }
    @Test
    fun `part1 example`() {
        assertEquals(3, calcShortestSteps("ne,ne,ne"))
        assertEquals(0, calcShortestSteps("ne,ne,sw,sw"))
        assertEquals(2, calcShortestSteps("ne,ne,s,s"))
        assertEquals(3, calcShortestSteps("se,sw,se,sw,sw"))
        assertEquals("ne,ne,ne".toPath(), calcShortcut("ne,ne,ne"))
        assertEquals(none, calcShortcut("ne,ne,sw,sw"))
        assertEquals("se,se".toPath(), calcShortcut("ne,ne,s,s"))
        assertEquals("s,s,sw".toPath(), calcShortcut("se,sw,se,sw,sw"))
    }
/*
    @Test
    fun `part1 generate sequence with bug`() {
        val values = directions
        var working = true
        var seq: List<String>
        while(working) {
            seq = (0..4).map { Random().nextInt(values.size) }.map { values[it] }
            var res = calcShortcut(seq.joinToString( "," ))
            var resOfRes = calcShortcut(res.joinToString( "," ))

            if(res != resOfRes) {
                working = false
                println(seq)
                println("first res: $res")
                println("second res: $resOfRes")
            }

        }
    }*/

    @Test
    fun `part1 bug substitution result has to be substituted again`() {
        // se,ne,sw
        // res with bug: [s, ne]
        // res correct: [se]
        assertEquals(1, calcShortestSteps("se,ne,sw"))
        assertEquals(1, calcShortestSteps("ne,se,sw"))
    }

    @Test
    fun `part1 challenge cannot be shortened again`() {
        val toCheck = "nw,n,ne,ne,ne,n,n,n,n,n,ne,n,ne,sw,nw,ne,sw,nw,n,n,s,nw,n,n,n,n,n,n,n,nw,s,sw,nw,n,se,se,nw,s,nw,nw,nw,nw,nw,se,n,nw,n,s,sw,n,nw,sw,n,nw,se,nw,n,nw,n,nw,sw,n,nw,ne,se,n,nw,ne,nw,nw,se,n,n,nw,nw,se,n,nw,sw,nw,n,n,nw,nw,s,nw,ne,nw,nw,nw,ne,nw,se,n,nw,nw,sw,nw,sw,nw,nw,se,sw,sw,nw,s,nw,nw,nw,nw,sw,n,ne,nw,s,nw,nw,sw,nw,nw,sw,s,nw,nw,nw,se,nw,nw,sw,sw,nw,se,ne,nw,nw,sw,nw,sw,sw,nw,sw,s,sw,sw,sw,sw,nw,n,nw,nw,sw,sw,nw,nw,nw,nw,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,nw,sw,sw,se,sw,sw,sw,nw,sw,sw,sw,sw,sw,sw,nw,sw,sw,s,sw,se,sw,sw,sw,ne,n,sw,sw,sw,sw,sw,sw,se,sw,sw,n,sw,sw,s,sw,n,se,s,sw,s,sw,sw,sw,nw,sw,n,s,sw,sw,s,sw,sw,sw,sw,sw,s,sw,sw,s,sw,sw,sw,ne,sw,s,sw,nw,sw,sw,s,s,s,nw,s,s,nw,sw,se,s,s,sw,sw,sw,s,n,s,n,sw,s,sw,s,s,sw,sw,s,s,sw,nw,nw,s,sw,s,sw,se,s,nw,s,se,se,s,sw,s,sw,s,s,sw,sw,s,sw,s,s,sw,s,ne,s,s,n,s,sw,s,s,s,s,s,ne,sw,s,s,s,s,sw,se,se,s,s,s,s,s,nw,s,sw,s,s,se,s,s,se,n,s,s,s,sw,s,s,s,ne".toPath()
        val res = calcShortcut(toCheck.joinToString(","))
        val resOfRes = calcShortcut(res.joinToString(","))
        assertEquals(res.toSet(), resOfRes.toSet())
    }

    @Test
    fun `part1 challenge`() {
        // 1484 too high
        // 1106 too high
        //assertEquals(3, calcShortcut(challenge))
        assertEquals(834, calcShortestSteps(challenge))
    }
    @Test
    fun `part2 maxDistance simple`() {
        //n,n,n,n,s,s,s,s
        // 4 steps north was the furthest, but he came back...
        assertEquals(4, calcFurthestDistance("n,n,n,n,s,s,s,s"))
    }
    @Test
    fun `part2 challenge maxDistance`() {
        assertEquals(1569, calcFurthestDistance(challenge))
    }

    private fun String.toPath() = this.split(",")
}