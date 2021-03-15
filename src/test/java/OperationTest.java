import controller.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperationTest {

    private Operation operation;

    @BeforeEach
    public void setUp() throws Exception {
        operation = new Operation("4x^5-3x^4+x^2-8x+1","x^2-1");
    }

    @Test
    @DisplayName("Sum test")
    public void testSum(){
        operation.polynomialSum();
        assertEquals("4x^5-3x^4+2x^2-8x", operation.getResult().getThisPolynomial(0),"Sum should work");
    }

    @Test
    @DisplayName("Subtraction test")
    public void testSubtraction(){
        operation.polynomialSubtraction();
        assertEquals("4x^5-3x^4-8x+2", operation.getResult().getThisPolynomial(0),"Subtraction should work");
    }

    @Test
    @DisplayName("Multiplication test")
    public void testMultiplication(){
        operation.polynomialMultiplication();
        assertEquals("4x^7-3x^6-4x^5+4x^4-8x^3+8x-1", operation.getResult().getThisPolynomial(0),"Multiplication should work");
    }

    @Test
    @DisplayName("Division test")
    public void testDivision(){
        operation.polynomialDivision();
        assertEquals("4x^3-3x^2+4x-2", operation.getResult().getThisPolynomial(0),"Division should work");
    }

    @Test
    @DisplayName("Derivative test")
    public void testDerivative(){
        operation.polynomialDerivative(operation.getPolynomial1());
        assertEquals("20x^4-12x^3+2x-8", operation.getResult().getThisPolynomial(0),"Derivative should work");
    }

    @Test
    @DisplayName("Integration test")
    public void testIntegration(){
        operation.polynomialIntegration(operation.getPolynomial2());
        assertEquals("0.33x^3-x", operation.getResult().getThisPolynomial(1),"Integration should work");
    }
}