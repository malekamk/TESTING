import org.junit.jupiter.api.Test;

import static Validate_sa_id.ValidateSaId.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidateSaIdTest {


    @Test
    void testInvalidLength() {
        assertFalse(isIdNumberValid("241332"), "ID number too short should be 13");
        assertFalse(isIdNumberValid("24133273256487"), "ID number too long should be 13");
    }

    @Test
    void testvalidLength() {
        assertTrue(isIdNumberValid("0411035297083"), "ID number should be 13 in length");
    }

    @Test
    void testinvalidCharacter() {
        assertFalse(isIdNumberValid("0411035297083."), "ID contains non digit characters");
        assertFalse(isIdNumberValid("0411A035297083"), "ID contains non digit characters");
    }

    @Test
    void testvalidCharacter() {
        assertTrue(isIdNumberValid("0411035297083"), "ID must contain digits only");
    }

    @Test
    void testvalidGender() {
        assertTrue(validGender("5297"), "5297 Gender number should be valid");
        assertTrue(validGender("9999"), "9999 Gender number should be valid");
        assertTrue(validGender("0000"), "0000 Gender number should be valid");

    }


    @Test
    void testInvalidMonth() {
        assertFalse(isDateValid("241332"), "Month 13 should be invalid");
    }

    @Test
    void testvalidMonth() {
        assertTrue(isDateValid("241203"), "Month 12 should be valid");
    }

    @Test
    void testInvalidDay() {
        assertFalse(isDateValid("241140"), "Day 40 should be invalid");
    }

    @Test
    void testvalidDay() {
        assertTrue(isDateValid("241212"), "Day 12 should be valid");
    }

    @Test
    void testNonLeapYearFeb29() {
        assertFalse(isDateValid("250229"), "Feb 29, 2025 should be invalid (not leap year)");
    }

    @Test
    void testLeapYear() {
        assertTrue(isDateValid("240229"), "Feb 29, 2024 should be valid (leap year)");
    }
}
