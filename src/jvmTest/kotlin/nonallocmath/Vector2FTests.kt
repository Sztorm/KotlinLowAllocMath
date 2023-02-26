package nonallocmath

import com.sztorm.nonallocmath.Vector2F
import org.junit.jupiter.params.*
import org.junit.jupiter.params.provider.*
import kotlin.test.*

class Vector2FTests {

    @ParameterizedTest
    @MethodSource("vectors")
    fun vectorContentsAreValid(x: Float, y: Float) {
        val vec = Vector2F(x, y)

        // Comparing anything with NaN always returns false. To compare exact contents we need to
        // compare bits.
        assertTrue(
            vec.x.toRawBits() == x.toRawBits() &&
                    vec.y.toRawBits() == y.toRawBits()
        )
    }

    companion object {
        @JvmStatic
        fun vectors(): List<Arguments> = listOf(
            Arguments.of(2F, 4F),
            Arguments.of(2F, Float.NaN),
            Arguments.of(Float.NEGATIVE_INFINITY, -1F),
            Arguments.of(-1F, Float.NEGATIVE_INFINITY),
        )
    }
}