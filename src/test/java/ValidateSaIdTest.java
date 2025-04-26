import org.junit.jupiter.api.Test;

import static Validate_sa_id.ValidateSaId.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidateSaIdTest {


    @Test
    void testValidSaId() {
        assertTrue(luhnAlgorithm("0411035297083"), "luhn algorithm failed to validate ID");
        assertFalse(luhnAlgorithm("0411035297082"), "luhn algorithm failed to validate ID");
    }

    @Test
    void testValidRacialClass() {
        assertTrue(isIdNumberValid("0411035297083"), "8(default) should be available as last second digit");
    }

    @Test
    void testInvalidRacialClass() {
        assertFalse(isIdNumberValid("0411035297013"), "8(default) should be available as last second digit");
    }
    @Test
    void testInvalidLength() {
        assertFalse(isIdNumberValid("241332"), "ID number too short should be 13");
        assertFalse(isIdNumberValid("24133273256487"), "ID number too long should be 13");
    }

    @Test
    void testCitizenship() {
        assertTrue(isIdNumberValid("0411035297083"), "Citizenship status must be 0 or 1");
        assertTrue(isIdNumberValid("0411035297083"), "Citizenship status must be 0 or 1");
        assertFalse(isIdNumberValid("0411035297983"), "Citizenship status must be 0 or 1");

    }

    @Test
    void testValidLength() {
        assertTrue(isIdNumberValid("0411035297083"), "ID number should be 13 in length");
    }

    @Test
    void testInvalidCharacter() {
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
    void testInvalidGender() {
        assertFalse(validGender("10000"), "SSSS number should be 0000 to 9999");
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
    void testValidDay() {
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
