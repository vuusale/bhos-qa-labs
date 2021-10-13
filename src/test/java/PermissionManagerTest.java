import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PermissionManagerTest {
    public PermissionManager permissionManager = new PermissionManager();

    @Test
    @DisplayName("Testing default value")
    void testSolution() {
        assertEquals("User", permissionManager.getmCurrentLevel(), "Default permission level should be User");
    }

    @Test
    @DisplayName("Testing setter")
    void testSetter() {
        permissionManager.setmCurrentLevel("Admin");
        assertEquals("Admin", permissionManager.getmCurrentLevel(), "Setter should work");
    }
}
