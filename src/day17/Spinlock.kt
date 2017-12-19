package day17

//part1
fun afterSpin(step : Int = 1, times: Int = 1, numAfter: Int = times) : Int {
    val spinRes = spin(step = step, times = times)
    val idxLast = spinRes.indexOf(numAfter)
    return spinRes[idxLast + 1]
}

fun spin(step : Int = 1, times: Int = 1) : List<Int>{
    // Optimization 1 : Use Map instead of list. This way there is no arraycopy in ArrayList used
    var buffer = mutableListOf(0)
    var pos = 0
    var nextVal = 1

    (0 until times).forEach {
        pos = ((pos + step) % buffer.size) + 1
        if(pos > buffer.lastIndex) buffer.add(nextVal++)
        else buffer.add(pos, nextVal++)
    }

    return buffer
}
//part2
fun optSpinAfter0(step : Int = 1, times: Int = 1) : Int {
    // Optimization : only pretend that we are mutating a list
    var res = -1
    var bufferSize = 1 // "fake" list only containing {0}
    var zeroIdx = 0
    var pos = 0
    var nextVal = 1

    (0 until times).forEach {
        // new position
        pos = ((pos + step) % bufferSize) + 1
        if(pos == zeroIdx + 1) {
            // new value inserted straight after zero
            res = nextVal
        } else if(pos <= zeroIdx) {
            // new value inserted before the zero
            zeroIdx++
        }
        // "insert" the value and thus increase the buffer size
        nextVal++
        bufferSize++
    }
    return res
}