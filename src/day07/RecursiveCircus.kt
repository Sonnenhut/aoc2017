package day07

import kotlin.math.absoluteValue

data class FlatProgram(val name:String, val weight : Int, val holding: List<String>) {
    fun convert(others: MutableList<FlatProgram>) : Program {
        others.remove(this) // already converted
        return if(this.holding.isEmpty()) {
            Program(name, weight, listOf())
        } else {
            val convertedChilds = others.filter { it.name in holding }.map { it.convert(others) }
            Program(name, weight, convertedChilds)
        }
    }
}
data class Program(val name:String, val weight : Int, val holding: List<Program>) {
    val accWeight: Int by lazy {
        weight + holdingWeight
    }
    val holdingWeight: Int by lazy {
         holding.map { it.accWeight }.sum()
    }
}

class RecursiveCircus {
    companion object {
        fun findLowestElement(elements: List<FlatProgram>): FlatProgram {
            var lastHoldingElement = elements[0]
            var holdingElement: FlatProgram? = elements[0]
            while(holdingElement != null) {
                lastHoldingElement = holdingElement
                holdingElement = elements.find { it.holding.contains(holdingElement!!.name) }
            }
            return lastHoldingElement
        }

        fun buildTree(elements: List<FlatProgram>) : Program {
            val lowest = findLowestElement(elements)

            // setup all else elements
            val elementsToSortAway = elements.toMutableList()
            elementsToSortAway.remove(lowest)

            // convert the lowest, wich should result in all
            val root = lowest.convert(elementsToSortAway)

            // be sure that we don't ignore elements
            if(elementsToSortAway.isNotEmpty()) {
                throw IllegalStateException("could not build up tree fully, there are still elements left: $elementsToSortAway")
            }
            return root
        }

        fun findUnbalancedProgram(flatElements: List<FlatProgram>) : Pair<Program, Int>? {
            val elem = buildTree(flatElements)
            return findUnbalancedProgramRecur(elem)!!
        }

        fun findUnbalancedProgramRecur(elem: Program) : Pair<Program, Int>? {
            var res: Pair<Program, Int>? = null
            val directUnbalanced = findUnbalanced(elem.holding)

            if(directUnbalanced == null) {
                res = null
            } else {
                // is the real imbalance upward, or is the current element the problem?
                res = findUnbalancedProgramRecur(directUnbalanced.first)
                if(res == null) {
                    res =  directUnbalanced
                }
            }
            return res
        }

        fun findUnbalanced(programs: List<Program>) : Pair<Program, Int>? {
            // there may be a bug...
            val accWeightsOfPrograms = programs.groupBy { it.accWeight }
            // there should be one Program that has an abnormal weight
            val diffWeightProg = accWeightsOfPrograms.filter { it.value.size == 1 }.values.firstOrNull()?.firstOrNull()
            // and others that share the same (correct) weight
            val commonWeightProg = accWeightsOfPrograms.filter { it.value.size > 1 }.values.firstOrNull()?.firstOrNull()

            return if(commonWeightProg != null && diffWeightProg != null) {
                val correctWeight = (commonWeightProg.accWeight - diffWeightProg.holdingWeight).absoluteValue
                Pair(diffWeightProg, correctWeight)
            } else {
                null
            }
        }
    }
}