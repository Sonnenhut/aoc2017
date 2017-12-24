package day23

import day18.DanceMachine
object CoprocessorConflagation {
    class CPUMachine(instructions: List<String>, initialRegisters: Map<Char, Long> = mapOf()) : DanceMachine(instructions, initialRegisters = initialRegisters, receivePredicate = { false }) {

        fun executeCounting(operation: String): Int {
            danceUntilReceive()
            return instructionsExecuted.filter { it.operation == operation }.count()
        }
    }

    fun countMulInvoked(instructions: List<String>): Int {
        val cpu = CPUMachine(instructions)
        return cpu.executeCounting("mul")
    }

    fun executeAssembly(): Int {
        // I hate myself now...
        return AssemblyKotlin(false).exec()
    }
}

class AssemblyKotlin(val debug: Boolean = true) {
    var a = if (debug) 0 else 1
    var b = 0
    var c = 0
    var d = 0
    var e = 0
    var f = 0
    var g = 0
    var h = 0

    // optimized
    fun exec(): Int {
        b = 99
        c = b
        if (!debug) {
            b = b * 100 + 100000 // 109900
            c = b + 17000 // 92900
        }
        do {
            if(!b.toBigInteger().isProbablePrime(1)) {
                // count numbers that are *not* prime
                h++
            }
            if(b == c) break
            b += 17
        } while (true)
        return h
    }
    fun execOriginal(): Int {
        b = 99
        c = b
        if (a == 1) {
            b *= 100
            b -= -100000
            c = b
            c -= -17000
        }
        do {
            f = 1
            d = 2

            do {
                e = 2
                do {
                    g = d
                    g *= e
                    g -= b
                    if (g == 0) {
                        f = 0
                    }
                    e -= -1
                    g = e
                    g -= b
                } while (g != 0)
                d -= -1
                g = d
                g -= b
            } while (g != 0)
            if (f == 0) {
                h -= -1
            }
            g = b
            g -= c
            if(g == 0) break
            b += 17
        } while (true)
        return h
    }
}