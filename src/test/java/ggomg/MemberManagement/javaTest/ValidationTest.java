package ggomg.MemberManagement.javaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class ValidationTest {

    @Test
    void trim_can_exclude_double_space() {
        String input = "  user  ";
        assertEquals(4, input.trim().length());
    }

    @Test
    void fail_when_special() {
        String regularExpressions = "[a-zA-Z0-9\\\\s]+";
        String testString = "af!R";
        assertFalse(testString.matches(regularExpressions));
    }

    @Test
    void fail_when_space_in_middle() {
        String regularExpressions = "[a-zA-Z0-9\\\\s]+";
        String testString = "af R";
        assertFalse(testString.matches(regularExpressions));
    }

    @Test
    void fail_when_space_in_last() {
        String regularExpressions = "[a-zA-Z0-9\\\\s]+";
        String testString = "afR ";
        assertFalse(testString.matches(regularExpressions));
    }

    @Test
    void fail_when_space_in_first() {
        String regularExpressions = "[a-zA-Z0-9\\\\s]+";
        String testString = " afR";
        assertFalse(testString.matches(regularExpressions));
    }

    @Test
    void fail_when_special_and_space() {
        String regularExpressions = "[a-zA-Z0-9\\\\s]+";
        String testString = "af@d f% R";
        assertFalse(testString.matches(regularExpressions));
    }

    @Test
    void special_character_apace_success() {
        String regularExpressions = "[a-zA-Z0-9\\\\s]+";
        String testString = "afdR";
        assertTrue(testString.matches(regularExpressions));
    }

    @Test
    void success_space() {
        String testString = "afdR";
        assertEquals(testString.trim(), testString);
    }

    @Test
    void fail_space_last() {
        String testString = "afdR ";
        assertNotEquals(testString.replaceAll(" ", ""), testString);
    }

    @Test
    void fail_space_first() {
        String testString = " afdR";
        assertNotEquals(testString.replaceAll(" ", ""), testString);
    }

    @Test
    void fail_space_middle() {
        String testString = "af dR";
        assertNotEquals(testString.replaceAll(" ", ""), testString);
    }

    @Test
    void success_Kor_En_Num_Only() {
        String regularExpressions = "^[가-힣a-zA-Z0-9]*$";
        String testString = "한he글1";
        assertTrue(testString.matches(regularExpressions));
    }

    @Test
    void fail_Kor_En_Num_Only_space() {
        String regularExpressions = "^[가-힣a-zA-Z0-9]*$";
        String testString = "한he 글1";
        assertFalse(testString.matches(regularExpressions));
    }

    @Test
    void fail_Kor_En_Num_Only_special() {
        String regularExpressions = "^[가-힣a-zA-Z0-9]*$";
        String testString = "한he%글1";
        assertFalse(testString.matches(regularExpressions));
    }

}
