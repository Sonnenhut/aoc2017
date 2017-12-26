package day07

import day07.RecursiveCircus.Companion.buildTree
import day07.RecursiveCircus.Companion.findUnbalancedProgram
import org.junit.Assert.*
import org.junit.Test

class RecursiveCircusTest {

    private val exampleLines =
            """pbga (66)
xhth (57)
ebii (61)
havc (66)
ktlj (57)
fwft (72) -> ktlj, cntj, xhth
qoyq (66)
padx (45) -> pbga, havc, qoyq
tknk (41) -> ugml, padx, fwft
jptl (61)
ugml (68) -> gyxo, ebii, jptl
gyxo (61)
cntj (57)""".lines()

    private fun example() = exampleLines.map { mapTextInputLine(it) }

    private fun challenge() = Challenge.read(7).lines().map { mapTextInputLine(it) }


    // matches for : "fwft (72) -> ktlj, cntj, xhth"
    // \1 -> fwft
    // \2 -> 72
    // \3 " -> "
    // \4 -> ktlj, cntj, xhth
    private val regex = """(.*) \((.*)\)( -> )?(.*)""".toRegex()

    @Test
    fun `regex works, yay!`() {
        assertTrue(regex.matches("fwft (72) -> ktlj, cntj, xhth"))
        assertTrue(regex.matches("pbga (66)"))
    }

    @Test
    fun `regex extract simple works, yay!`() {
        val match = regex.matchEntire("pbga (66)")!!.groupValues
        assertEquals("pbga", match[1])
        assertEquals("66", match[2])
    }

    @Test
    fun `regex extract complex works, yay!`() {
        val match = regex.matchEntire("fwft (72) -> ktlj, cntj, xhth")!!.groupValues
        assertEquals("fwft", match[1])
        assertEquals("72", match[2])
        assertEquals(" -> ", match[3])
        assertEquals("ktlj, cntj, xhth", match[4])
    }

    @Test
    fun `part1 find lowest element with abc to def`() {
        val flatTree = listOf(FlatProgram("abc", 0, listOf("def")), FlatProgram("def", 0, listOf()))
        assertEquals("abc", RecursiveCircus.findLowestElement(flatTree).name)
    }

    @Test
    fun `part1 find lowest element with only abc to def to xyz`() {
        val flatTree = listOf(FlatProgram("abc", 0, listOf("def"))
                , FlatProgram("def", 0, listOf("xyz"))
                , FlatProgram("xyz", 0, listOf()))
        assertEquals("abc", RecursiveCircus.findLowestElement(flatTree).name)
    }

    @Test
    fun `part1 example`() {
        assertEquals("tknk", RecursiveCircus.findLowestElement(example()).name)
    }

    @Test
    fun `part1 challenge`() {
        assertEquals("vgzejbd", RecursiveCircus.findLowestElement(challenge()).name)
    }

    @Test
    fun `part2 can convert 4depth program`() {
        // aaa -> abc -> def -> xxx
        val flatTree = mutableListOf(FlatProgram("aaa", 0, listOf("abc"))
                , FlatProgram("abc", 0, listOf("def"))
                , FlatProgram("def", 0, listOf("xxx"))
                , FlatProgram("xxx", 0, listOf()))
        val expected = Program("aaa", 0, listOf(
                Program("abc", 0, listOf(
                        Program("def", 0, listOf(
                                Program("xxx", 0, listOf())
                        ))
                ))))

        assertEquals(expected, buildTree(flatTree))
    }
    @Test
    fun `part2 find unbalanced program in depth 2`() {
        // a -> aa (5)
        //   -> ab (5)
        //   -> ac (10) <- this one is too heavy
        val flatTree = mutableListOf(FlatProgram("a", 0, listOf("aa","ab","ac"))
                , FlatProgram("aa", 5, listOf())
                , FlatProgram("ab", 5, listOf())
                , FlatProgram("ac", 10, listOf()))
        val res = findUnbalancedProgram(flatTree)

        assertEquals("ac", res?.first?.name)
        assertEquals(5, res?.second)
    }
    @Test
    fun `part2 find unbalanced program in depth 3`() {
        // a -> aa (10) -> aaa (5)
        //   -> ab (10) -> aba (5)
        //   -> ac (10) -> aca (5)
        //              -> acb (5)
        // ac is unbalanced, should be 5

        val flatTree = mutableListOf(FlatProgram("a", 0, listOf("aa","ab","ac"))
                , FlatProgram("aa", 10, listOf("aaa"))
                , FlatProgram("aaa", 5, listOf())
                , FlatProgram("ab", 10, listOf("aba"))
                , FlatProgram("aba", 5, listOf())
                , FlatProgram("ac", 10, listOf("aca","acb"))
                , FlatProgram("aca", 5, listOf())
                , FlatProgram("acb", 5, listOf()))
        val res = findUnbalancedProgram(flatTree)

        assertEquals("ac", res?.first?.name)
        assertEquals(5, res?.second)
    }
    @Test
    fun `part2 find unbalanced program in depth 4`() {
        // a -> aa (10) -> aaa (15)
        //              -> aab (15)
        //              -> aac (4) -> aaca (5)
        //                         -> aacb (5)
        //   -> ab (10) -> aba (15)
        //              -> abb (15)
        //              -> abc (15)
        //   -> ac (10) -> aca (15)
        //              -> acb (15)
        //              -> acc (15)
        // aac is unbalanced, should be 5

        val flatTree = mutableListOf(FlatProgram("a", 0, listOf("aa","ab","ac"))
                , FlatProgram("aa", 10, listOf("aaa","aab","aac"))
                , FlatProgram("aaa", 15, listOf())
                , FlatProgram("aab", 15, listOf())
                , FlatProgram("aac", 4, listOf("aaca","aacb"))
                , FlatProgram("aaca", 5, listOf())
                , FlatProgram("aacb", 5, listOf())
                , FlatProgram("ab", 10, listOf("aba","abb","abc"))
                , FlatProgram("aba", 15, listOf())
                , FlatProgram("abb", 15, listOf())
                , FlatProgram("abc", 15, listOf())
                , FlatProgram("ac", 10, listOf("aca","acb", "acc"))
                , FlatProgram("aca", 15, listOf())
                , FlatProgram("acb", 15, listOf())
                , FlatProgram("acc", 15, listOf()))
        val res = findUnbalancedProgram(flatTree)

        assertEquals("aac", res?.first?.name)
        assertEquals(5, res?.second)
    }
    @Test
    fun `part2 challenge`() {
        assertEquals("kiatxq", findUnbalancedProgram(challenge())?.first?.name)
        assertEquals(1226, findUnbalancedProgram(challenge())?.second)
    }

    private fun mapTextInputLine(input: String): FlatProgram {
        val groups = regex.matchEntire(input)!!.groupValues
        val name = groups[1]
        val weight = groups[2].toInt()
        val holding = if (groups.lastIndex != 4) listOf() else groups[4].split(", ")
        return FlatProgram(name, weight, holding)
    }

}