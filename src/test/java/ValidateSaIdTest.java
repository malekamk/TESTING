import org.junit.jupiter.api.Test;

import static Validate_sa_id.ValidateSaId.isDateValid;
import static Validate_sa_id.ValidateSaId.isIdNumberValid;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidateSaIdTest {


    @Test
    void testInvalidLength() {
        assertFalse(isIdNumberValid("241332"), "ID number too short should be 13");
        assertFalse(isIdNumberValid("24"), "ID number too short should be 13");
        assertFalse(isIdNumberValid("241332732564"), "ID number too short should be 13");
    }

    @Test
    void testvalidLength() {
        assertTrue(isIdNumberValid("0411035297083"), "ID number should be 13 in length");
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
