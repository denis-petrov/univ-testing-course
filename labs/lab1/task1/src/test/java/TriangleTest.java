import com.app.model.Triangle;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static com.app.util.MessageUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TriangleTest {

    @Test
    void getTriangleType_EquilateralTriangle_ParamsAreCorrect() {
        Triangle triangle = new Triangle(1, 1, 1);
        assertEquals(triangle.getTriangleType(), EQUILATERAL_TRIANGLE);

        Triangle triangle2 = new Triangle(10, 10, 10);
        assertEquals(triangle2.getTriangleType(), EQUILATERAL_TRIANGLE);
    }

    @Test
    void getTriangleType_IsoscelesTriangle_ParamsAreCorrect() {
        Triangle triangle = new Triangle(10, 10, 1);
        assertEquals(triangle.getTriangleType(), ISOSCELES_TRIANGLE);

        Triangle triangle2 = new Triangle(1, 10, 10);
        assertEquals(triangle2.getTriangleType(), ISOSCELES_TRIANGLE);
    }

    @Test
    void getTriangleType_DefaultTriangle_ParamsAreCorrect() {
        Triangle triangle = new Triangle(14, 12, 13);
        assertEquals(triangle.getTriangleType(), DEFAULT_TRIANGLE);
    }
}
