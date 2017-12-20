package day20

import day20.ParticleSwarm.closestId
import day20.ParticleSwarm.collisionRemaining
import org.junit.Assert.*
import org.junit.Test

class ParticleSwarmTest {

    val challenge = Challenge.read(20).lines()
    val example1 = listOf("p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>","p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>")
    val example2 = listOf("p=<-6,0,0>, v=< 3,0,0>, a=< 0,0,0>",
            "p=<-4,0,0>, v=< 2,0,0>, a=< 0,0,0>",
            "p=<-2,0,0>, v=< 1,0,0>, a=< 0,0,0>",
            "p=<3,0,0>, v=< -1,0,0>, a=< 0,0,0>")

    @Test
    fun `particle parsed`() {
        val particle = Particle.from(0,"p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>")
        assertEquals(listOf(3,0,0), particle.p)
        assertEquals(listOf(2,0,0), particle.v)
        assertEquals(listOf(-1,0,0), particle.a)
    }
    @Test
    fun `example tick`() {
        var particle = Particle.from(0,"p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>").tick()
        // tick 1
        assertEquals(listOf( 4,0,0), particle.p)
        assertEquals(listOf( 1,0,0), particle.v)
        assertEquals(listOf(-1,0,0), particle.a)
        // tick 2
        particle = particle.tick()
        assertEquals(listOf( 4,0,0), particle.p)
        assertEquals(listOf( 0,0,0), particle.v)
        assertEquals(listOf(-1,0,0), particle.a)
        // tick 3
        particle = particle.tick()
        assertEquals(listOf( 3,0,0), particle.p)
        assertEquals(listOf(-1,0,0), particle.v)
        assertEquals(listOf(-1,0,0), particle.a)
    }
    @Test
    fun `complex distance`() {
        var particle = Particle.from(0,"p=< 5,5,-5>, v=< 0,0,0>, a=<-5,-5,5>")
        // tick 0
        assertEquals(15, particle.distance())
        // tick 1
        particle = particle.tick()
        assertEquals(0, particle.distance())
        // tick 2
        particle = particle.tick()
        assertEquals(30, particle.distance())
        // tick 2
        particle = particle.tick()
        assertEquals(75, particle.distance())

        // ensure that everything went as planned
        assertEquals(listOf(-25,-25,25), particle.p)
        assertEquals(listOf(-15,-15,15), particle.v)
        assertEquals(listOf(-5, -5,  5), particle.a)
    }
    @Test
    fun `part1 example`() {
        assertEquals(0, closestId(example1))
    }
    @Test
    fun `part1 challenge`() {
        assertEquals(170, closestId(challenge))
    }

    @Test
    fun `collision removes elements at same p`() {
        val p = listOf(0,1,2)
        val otherP = listOf(99,99,99)
        val zero = listOf(0,0)
        val samePosParticles = (0 until 10).mapIndexed { idx, _ -> Particle(idx,p,zero+idx,zero+idx) }
        val otherParticle = Particle(99, otherP, zero+0, zero+0)
        val allParticles = samePosParticles + otherParticle
        assertEquals(listOf(otherParticle), allParticles.collide())
    }

    @Test
    fun `part2 example`() {
        assertEquals(1, collisionRemaining(example2))
    }
    @Test
    fun `part2 challenge`() {
        // 432 too low
        assertEquals(571, collisionRemaining(challenge))
    }
}