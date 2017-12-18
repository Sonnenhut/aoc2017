package day15

open class Generator(initial : Long,  val factor: Int, val criteria : (Long) -> Boolean = { true }) {
    private val mod = 2147483647
    private var previous: Long = initial
    fun next() : Long {
        do {
            previous = (previous * factor) % mod
        } while (!criteria(previous)) // until there is one value that matches the criteria
        return previous
    }
}
class GeneratorAOne(initial: Long) : Generator(initial, 16807)
class GeneratorBOne(initial: Long) : Generator(initial, 48271)
class GeneratorATwo(initial: Long) : Generator(initial, 16807, { value: Long -> value % 4 == 0L } )
class GeneratorBTwo(initial: Long) : Generator(initial, 48271, { value: Long -> value % 8 == 0L } )

class Judge {
    companion object {
        fun compare(genA: Generator, genB: Generator, pairs: Long = 40000000L) : Int {
            return (0 until pairs).filter {
                val aVal = genA.next().toBinaryStr().takeLast(16)
                val bVal = genB.next().toBinaryStr().takeLast(16)
                aVal == bVal
            }.count()
        }
    }
}
fun Long.toBinaryStr() : String{
    return this.toString(2).padStart(32,'0')
}