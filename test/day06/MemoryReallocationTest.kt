package day06

import org.junit.Test

import org.junit.Assert.*

class MemoryReallocationTest {

    val challenge = Challenge.read(6).split('\t').map { it.toInt()}

    @Test
    fun `redistribute {0,(1)} results {1,0}`() {
        assertEquals(listOf(1,0), MemoryReallocation.redistribute(1,listOf(0,1)))
    }
    @Test
    fun `redistribute {0,2,(7),0} results {2,4,1,2}`() {
        assertEquals(listOf(2,4,1,2), MemoryReallocation.redistribute(2,listOf(0,2,7,0)))
    }
    @Test
    fun `calc redistribution cycles {0,2,7,0} results 5`() {
        assertEquals(5, MemoryReallocation(listOf(0,2,7,0)).calcRedistributionCycles())
    }
    @Test
    fun `calc redistribution cycles {0,1} results 2`() {
        assertEquals(2, MemoryReallocation(listOf(0,1)).calcRedistributionCycles())
    }
    @Test
    fun `part 1 challenge`() {
        assertEquals(14029, MemoryReallocation(challenge).calcRedistributionCycles())
    }
    @Test
    fun `part 2 challenge`() {
        assertEquals(2765, MemoryReallocation(challenge).calcDistanceSinceFromOccurrence())
    }
}