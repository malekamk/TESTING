import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static Validate_sa_id.ValidateSaId.*;
import static org.junit.jupiter.api.Assertions.*;

public class ValidateSaIdTest {

    @Nested
    class LuhnAlgorithmValidation {

        @Test
        void givenValidId_whenUsingLuhnAlgorithm_thenShouldPass() {
            assertTrue(luhnAlgorithm("0411035297083"));
        }

        @Test
        void givenInvalidId_whenUsingLuhnAlgorithm_thenShouldFail() {
            assertFalse(luhnAlgorithm("0411035297082"));
        }
    }

    @Nested
    class NullAndEmptyValidation {

        @Test
        void givenNullId_whenValidating_thenShouldFail() {
            assertFalse(isIdNumberValid(null), "Null ID should not be valid");
        }

        @Test
        void givenEmptyId_whenValidating_thenShouldFail() {
            assertFalse(isIdNumberValid(""), "Empty ID should not be valid");
        }

        @Test
        void givenWhitespaceId_whenValidating_thenShouldFail() {
            assertFalse(isIdNumberValid("   "), "Whitespace-only ID should not be valid");
        }
    }


    @Nested
    class LengthValidation {

        @Test
        void givenShortId_whenCheckingLength_thenShouldFail() {
            assertFalse(isIdNumberValid("123456"));
        }

        @Test
        void givenLongId_whenCheckingLength_thenShouldFail() {
            assertFalse(isIdNumberValid("1234567890123456"));
        }

        @Test
        void given13DigitId_whenCheckingLength_thenShouldPass() {
            assertTrue(isIdNumberValid("0411035297083"));
        }
    }

    @Nested
    class CharacterValidation {

        @Test
        void givenIdWithNonDigitChars_whenValidating_thenShouldFail() {
            assertFalse(isIdNumberValid("0411035297083."));
            assertFalse(isIdNumberValid("0411A035297083"));
        }

        @Test
        void givenIdWithOnlyDigits_whenValidating_thenShouldPass() {
            assertTrue(isIdNumberValid("0411035297083"));
        }
    }

    @Nested
    class GenderValidation {

        @Test
        void givenValidGenderNumber_whenValidating_thenShouldPass() {
            assertTrue(validGender("5297"));
            assertTrue(validGender("9999"));
            assertTrue(validGender("0000"));
        }

        @Test
        void givenInvalidGenderNumber_whenValidating_thenShouldFail() {
            assertFalse(validGender("10000")); // Greater than max allowed
        }
    }

    @Nested
    class RacialClassificationValidation {

        @Test
        void givenIdWithValidRaceIndicator_whenValidating_thenShouldPass() {
            assertTrue(isIdNumberValid("0411035297083"));
        }

        @Test
        void givenIdWithInvalidRaceIndicator_whenValidating_thenShouldFail() {
            assertFalse(isIdNumberValid("0411035297013"));
        }
    }

    @Nested
    class CitizenshipValidation {

        @Test
        void givenValidCitizenIndicator_whenValidating_thenShouldPass() {
            assertTrue(isIdNumberValid("0411035297083"));
        }

        @Test
        void givenInvalidCitizenIndicator_whenValidating_thenShouldFail() {
            assertFalse(isIdNumberValid("0411035297983"));
        }
    }

    @Nested
    class DateValidation {

        @Test
        void givenValidDayMonthYear_whenValidating_thenShouldPass() {
            assertTrue(isDateValid("241212")); // Dec 12, 2024
        }

        @Test
        void givenInvalidMonth_whenValidating_thenShouldFail() {
            assertFalse(isDateValid("241332")); // Month 13
        }

        @Test
        void givenInvalidDay_whenValidating_thenShouldFail() {
            assertFalse(isDateValid("241140")); // Day 40
        }

        @Test
        void givenLeapYearWithFeb29_whenValidating_thenShouldPass() {
            assertTrue(isDateValid("240229")); // Feb 29, 2024
        }

        @Test
        void givenNonLeapYearWithFeb29_whenValidating_thenShouldFail() {
            assertFalse(isDateValid("250229")); // Feb 29, 2025
        }
    }
}
