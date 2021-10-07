import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EquationSolverTest {
    @Test
    @DisplayName("Solution of simple quadratic equation")
    void testSolution() {
        assertEquals("[-2.0, -3.0]", EquationSolver.solve(1, 5, 6), "Quadratic equations should be solved");
    }

    @Test
    @DisplayName("Ensure correct handling of unexpected forms of equations")
    void testUnsolvable() {
        assertEquals("[NaN, NaN]", EquationSolver.solve(1, 2, 12), "Testing equation with no roots");
        assertEquals("Not a quadratic equation", EquationSolver.solve(0, 1, 2), "Testing non-quadratic equation");
    }
}
