package ggomg.MemberManagement.javaTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ggomg.MemberManagement.role.RoleName;
import org.junit.jupiter.api.Test;

public class ValueOfTest {

    @Test
    void enum_valueOf_fail() {
        String input = "USERRR";
        assertThrows(IllegalArgumentException.class, () -> {
            RoleName roleName = RoleName.valueOf(input);
        });
    }

    @Test
    void enum_valueOf_success() {
        String input = "USER";
        assertDoesNotThrow(() -> {
            RoleName roleName = RoleName.valueOf(input);
        });
    }


}
