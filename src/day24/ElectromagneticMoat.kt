package day24

// part1
fun List<Component>.strongestBridge() : Int = this.bridges(0).map { it.strength }.max()!!
fun List<Component>.longestBridgeStrength() : Int  {
    val allBridges = this.bridges(0)
    val longestCnt = allBridges.map { it.length }.max()
    val longestAndStrongestBridge = allBridges.filter { it.length == longestCnt }.maxBy { it.strength }!!
    return longestAndStrongestBridge.strength
}
// part2
fun List<String>.toComponents() : List<Component> {
    return this.map { it.split('/').map { str -> str.toInt() } }
            .map { Component(it[0],it[1]) }
}

fun List<Component>.bridges(startingDock : Int = 0) : Set<Bridge> {
    //var toUse = this
    // create all possible bridges
    var bridges = listOf<Bridge>()
    val toDock = this.filter { it.canDock(startingDock) }
    if(toDock.isNotEmpty()) {
        for(dock in toDock) {
            // spin the dock that it fits
            val toConnect = dock.arrange(startingDock)
            val newBridge = Bridge(listOf(toConnect))
            // find matching bridges...
            bridges += newBridge
            // try normal way around
            bridges += (this - dock).bridges(newBridge.lastDock).map { newBridge + it }
        }
    }
    return bridges.toSet()
}

data class Bridge(val components: List<Component>) {
    val strength = this.components.fold(0) { acc, component ->  acc + component.score }
    operator fun plus(comp : Component) = Bridge(components + comp)
    operator fun minus(other: Bridge) = Bridge(components - other.components)
    operator fun plus(other: Bridge) = Bridge(this.components + other.components)
    val length = components.size
    val lastDock= components.last().last
    override fun toString(): String = components.joinToString("--")
}

data class Component(val first: Int, val last : Int) {
    val score = first + last
    fun canDock(dock : Int) = dock == arrange(dock).first
    fun arrange(dock : Int) = if(dock == first) this else flip()
    fun flip() = Component(last, first)
    override fun toString(): String = "$first/$last"
}