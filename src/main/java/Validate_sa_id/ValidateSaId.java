package Validate_sa_id;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Utility class to validate South African ID numbers.
 *
 * This class checks structural rules, date validity, citizenship rules,
 * checksum correctness (Luhn algorithm), and basic format constraints.
 */
public class ValidateSaId {

    private static final int ID_LENGTH = 13;
    private static final int RACE_INDICATOR = 8;
    private static final int GENDER_THRESHOLD = 5000;
    private static final int GENDER_MIN = 0;
    private static final int GENDER_MAX = 9999;

    /**
     * Validates the full SA ID number by checking all required conditions.
     *
     * We validate step-by-step to immediately reject any invalid ID without
     * wasting time checking the rest.
     *
     * @param idNumber the ID number to validate
     * @return true if the ID number is valid, false otherwise
     */
    public static boolean isIdNumberValid(String idNumber) {
        if (idNumber == null || idNumber.length() != ID_LENGTH) return false;
        if (!isOnlyDigit(idNumber)) return false;
        if (!isDateValid(idNumber.substring(0,6))) return false;
        if (!validGender(idNumber.substring(6,10))) return false;

        int citizenship = Character.getNumericValue(idNumber.charAt(10));
        if (citizenship != 1 && citizenship != 0) return false;

        if (Character.getNumericValue(idNumber.charAt(11)) != RACE_INDICATOR) return false; // We fix to 8 for newer ID standards (race indicator)

        if (!luhnAlgorithm(idNumber)) return false; // Validate checksum to detect typing errors

        return true;
    }

    /**
     * Validates the ID number using the Luhn algorithm.
     *
     * We manually separate odd and even digits to match SA ID structure.
     * Even digits are concatenated, multiplied, and summed according to rules.
     *
     * @param idNumber the ID number to validate
     * @return true if the checksum matches, false otherwise
     */
    public static boolean luhnAlgorithm(String idNumber) {
        int odd = 0;
        StringBuilder evenNumbers = new StringBuilder();

        for (int i = 0; i < 12; i++) {
            int number = Character.getNumericValue(idNumber.charAt(i));
            if ((i + 1) % 2 == 0) {
                evenNumbers.append(number); // Even positions
            } else {
                odd += number; // Only odd positions are added immediately
            }
        }

        int even = Integer.parseInt(evenNumbers.toString()) * 2; // Multiply all evens combined by 2
        int evensum = 0;

        for (char i : String.valueOf(even).toCharArray()) {
            evensum += Character.getNumericValue(i); // Sum each digit after multiplication
        }

        int total = odd + evensum;
        int calculatedsum = (total % 10 == 0) ? 0 : (10 - (total % 10)); // checksum formula

        int checkSum = Character.getNumericValue(idNumber.charAt(12));
        return checkSum == calculatedsum;
    }

    /**
     * Validates the gender sequence part of the ID number.
     *
     * Even though gender number could be male/female, we only care
     * that it falls between 0000 and 9999 because some IDs use
     * ranges differently.
     *
     * @param genderNumber the 4-digit sequence number
     * @return true if valid, false otherwise
     */
    public static boolean validGender(String genderNumber) {
        int gender = Integer.parseInt(genderNumber);
        if (gender >= GENDER_MIN && gender <= GENDER_MAX) {
            return true;
        }
        return false;
    }

    /**
     * Checks if a string contains only digits.
     *
     * We manually check character-by-character because regex is slower
     * and we want tighter control (early exit on first failure).
     *
     * @param idNumber the string to check
     * @return true if only digits, false otherwise
     */
    public static boolean isOnlyDigit(String idNumber) {
        for (char c : idNumber.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    /**
     * Validates the date part (YYMMDD) of the ID number.
     *
     * We use strict resolver style to catch invalid dates
     * like 20240230 (February 30th does not exist).
     *
     * @param date the date string in YYMMDD format
     * @return true if valid date, false otherwise
     */
    public static boolean isDateValid(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuMMdd")
                .withResolverStyle(ResolverStyle.STRICT);

        try {
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
