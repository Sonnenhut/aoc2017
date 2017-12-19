package day17

import org.junit.Assert.*
import org.junit.Test

class SpinlockTest {

    val challenge = Challenge.read(17).toInt()

    @Test
    fun `spin one time with step 1`() {
        assertEquals(listOf(0,1), spin(times = 1))
    }
    @Test
    fun `spin two times with step 1`() {
        assertEquals(listOf(0,2,1), spin(times = 2))
    }
    @Test
    fun `spin three times with step 1`() {
        assertEquals(listOf(0,2,1,3), spin(times = 3))
    }

    @Test
    fun `spin two times with step 3`() {
        assertEquals(listOf(0,2,1), spin(times = 2,step = 3))
    }
    @Test
    fun `part1 example`() {
        assertEquals(listOf(0,9,5,7,2,4,3,8,6,1), spin(times = 9,step = 3))
        assertEquals(638, afterSpin(times = 2017,step = 3))
    }
    @Test
    fun `part1 challenge`() {
        assertEquals(355, afterSpin(times = 2017,step = challenge))
    }
    @Test
    fun `part2 challenge`() {
        // 6154116 too low
        assertEquals(6154117, optSpinAfter0(times = 50000000,step = challenge))
    }

}