package day01

// http://adventofcode.com/2017/day/1
object InverseCaptcha {
    fun solvePartOne(input: Int = -1, inputStr: String = input.toString()): Int {
        // compare to the next value
        // rotating to the start if we reached the last index
        return solve(inputStr) { idx ->
            when(idx) {
                inputStr.lastIndex -> 0
                else -> idx + 1
            }
        }
    }

    fun solvePartTwo(input: Int = -1, inputStr: String = input.toString()): Int {
        // compare to the value that is x steps ahead
        // where x is half of the length of the whole number
        return solve(inputStr) { idx ->
            (idx + (inputStr.length / 2)) % inputStr.length
        }
    }

    private fun solve(inputStr: String, calcIdxToCompare: (Int) -> Int ): Int {
        val inputArr: IntArray = inputStr.map { it.toString().toInt() }.toIntArray()
        val matchingNextDigit = mutableListOf<Int>()

        for (idx in (0..inputArr.lastIndex)) {
            val currentVal = inputArr[idx]
            val compareIdx = calcIdxToCompare(idx)
            val nextVal = inputArr[compareIdx]
            if (currentVal == nextVal) matchingNextDigit.add(inputArr[idx])
        }

        return matchingNextDigit.toIntArray().sum()
    }
}