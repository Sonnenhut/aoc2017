package day20

import kotlin.math.abs

class Particle(val id: Int, val p: List<Int>, val v: List<Int>, val a: List<Int>) {
    companion object {
        fun from(id: Int, raw: String) : Particle{
            //p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>
            val rawStripped = raw.replace("<|>".toRegex(), "").split('=')
            return Particle(id, rawStripped[1].convert(),rawStripped[2].convert(),rawStripped[3].convert())
        }
        private fun String.convert() : List<Int> {
            return this.split(',').let { listOf(it[0].trim().toInt(),it[1].trim().toInt(),it[2].trim().toInt()) }
        }
    }
    fun tick() : Particle {
        val newVel = v.mapIndexed { idx, vel -> vel + a[idx] }
        val newPos = p.mapIndexed { idx, pos -> pos + newVel[idx] }
        return Particle(id,newPos,newVel,a)
    }
    fun distance() : Int {
        return p.fold(0) { acc, axis -> acc + abs(axis) }
    }
}

// This can be solved in a smarter way (checking how the distances change over time)
// The GPU will not thank me for the performance, but: "Keep it simpe, stupid"
object ParticleSwarm {
    //part1
    fun closestId(rawParticles: List<String>): Int {
        var particles = rawParticles.mapIndexed { idx, it ->  Particle.from(idx,it) }
        (0..999).forEach {
            particles = particles.map { it.tick() }
        }
        return particles.minBy { it.distance() }!!.id
    }
    //part2
    fun collisionRemaining(rawParticles: List<String>): Int {
        var particles = rawParticles.mapIndexed { idx, it ->  Particle.from(idx,it) }.collide()
        (0..999).forEach {
            particles = particles.map { it.tick() }.collide()
        }
        return particles.size
    }
}

fun List<Particle>.collide() : List<Particle> {
    return this.groupBy { it.p }.filter { it.value.size == 1 }.map { it.value.first() }
}