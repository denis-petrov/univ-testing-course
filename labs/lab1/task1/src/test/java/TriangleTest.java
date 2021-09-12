import com.app.model.Triangle;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static com.app.util.MessageUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TriangleTest {

    @Test
    public void getTriangleType_EquilateralTriangle_ParamsAreCorrect() {
        Triangle triangle = new Triangle(BigInteger.ONE, BigInteger.ONE, BigInteger.ONE);
        assertEquals(triangle.getTriangleType(), EQUILATERAL_TRIANGLE);

        Triangle triangle2 = new Triangle(BigInteger.TEN, BigInteger.TEN, BigInteger.TEN);
        assertEquals(triangle2.getTriangleType(), EQUILATERAL_TRIANGLE);
    }

    @Test
    public void getTriangleType_IsoscelesTriangle_ParamsAreCorrect() {
        Triangle triangle = new Triangle(BigInteger.TEN, BigInteger.TEN, BigInteger.ONE);
        assertEquals(triangle.getTriangleType(), ISOSCELES_TRIANGLE);

        Triangle triangle2 = new Triangle(BigInteger.ONE, BigInteger.TEN, BigInteger.TEN);
        assertEquals(triangle2.getTriangleType(), ISOSCELES_TRIANGLE);
    }

    @Test
    public void getTriangleType_DefaultTriangle_ParamsAreCorrect() {
        Triangle triangle = new Triangle(BigInteger.valueOf(14), BigInteger.valueOf(12), BigInteger.valueOf(13));
        assertEquals(triangle.getTriangleType(), DEFAULT_TRIANGLE);
    }
}
