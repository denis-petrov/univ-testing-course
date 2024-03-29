import com.app.exception.NotTriangleException;
import com.app.model.Triangle;
import com.app.model.TriangleFacade;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TriangleFacadeTest {

    @Test
    void buildTriangle_Triangle_ParamsAreCorrect() {
        Triangle triangle = new Triangle(1, 1, 1);
        assertEquals(TriangleFacade.buildTriangle("1", "1", "1"), triangle);

        Triangle triangle2 = new Triangle(10, 10, 10);
        assertEquals(TriangleFacade.buildTriangle("10", "10", "10"), triangle2);
    }

    @Test
    void Should_ThrowNumberFormatException_WhenParametersAreNotLongType() {
        assertThrows(NumberFormatException.class, () -> TriangleFacade.buildTriangle("test", "12", "12"));
        assertThrows(NumberFormatException.class, () -> TriangleFacade.buildTriangle("12", "test", "12"));
        assertThrows(NumberFormatException.class, () -> TriangleFacade.buildTriangle("12", "12", "test"));
        assertThrows(NumberFormatException.class, () -> TriangleFacade.buildTriangle("12", "test", "test"));
        assertThrows(NumberFormatException.class, () -> TriangleFacade.buildTriangle("test", "test", "test"));
    }

    @Test
    void Should_ThrowNotTriangleException_WhenParametersAreLessOrEqualToZero() {
        assertThrows(NotTriangleException.class, () -> TriangleFacade.buildTriangle("0", "1", "1"));
        assertThrows(NotTriangleException.class, () -> TriangleFacade.buildTriangle("1", "0", "1"));
        assertThrows(NotTriangleException.class, () -> TriangleFacade.buildTriangle("1", "1", "0"));
        assertThrows(NotTriangleException.class, () -> TriangleFacade.buildTriangle("0", "0", "1"));
        assertThrows(NotTriangleException.class, () -> TriangleFacade.buildTriangle("1", "0", "0"));
        assertThrows(NotTriangleException.class, () -> TriangleFacade.buildTriangle("0", "0", "0"));
    }

    @Test
    void Should_ThrowNotTriangleException_WhenParametersCannotConstructTriangle() {
        assertThrows(NotTriangleException.class, () -> TriangleFacade.buildTriangle("100", "1", "1"));
        assertThrows(NotTriangleException.class, () -> TriangleFacade.buildTriangle("1", "100", "1"));
        assertThrows(NotTriangleException.class, () -> TriangleFacade.buildTriangle("1", "1", "100"));
    }
}
