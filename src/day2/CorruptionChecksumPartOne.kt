package day2

object CorruptionChecksum {

    fun checksumPartOne(spreadsheet: List<List<Int>>): Int {
        return checksum(spreadsheet) { intArr ->
            intArr.max()!! - intArr.min()!!
        }
    }

    fun checksumPartTwo(spreadsheet: List<List<Int>>): Int {
        return checksum(spreadsheet, ::findEvenDivision)
    }

    fun findEvenDivision(values: List<Int>) : Int {
        for(firstValue in values) {
            val otherValue = values.firstOrNull { firstValue % it == 0 && firstValue != it}
            if(otherValue != null)
                return firstValue / otherValue
        }
        return -1
    }

    private fun checksum(spreadsheet: List<List<Int>>, mapValues: (List<Int>) -> Int): Int {
        return spreadsheet.map { mapValues(it) }.sum()
    }
}