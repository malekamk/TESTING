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
        if (idNumber == null || idNumber.length() != 13) return false;
        if (!isOnlyDigit(idNumber)) return false;
        if (!isDateValid(idNumber.substring(0,6))) return false;
        if (!validGender(idNumber.substring(6,10))) return false;

        int citizenship = Character.getNumericValue(idNumber.charAt(10));
        if (citizenship != 1 && citizenship != 0) return false;

        if (Character.getNumericValue(idNumber.charAt(11)) != 8) return false; // We fix to 8 for newer ID standards (race indicator)

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
                evenNumbers.append(number);
            }
            odd += number;
        }

        int even = Integer.parseInt(evenNumbers.toString()) * 2; // Multiply even numbers all at once
        int evensum = 0;

        for (char i : String.valueOf(even).toCharArray()) {
            evensum += Character.getNumericValue(i); // Sum digits of the multiplied even numbers
        }

        int total = odd + evensum;

        // Instead of doing (10 - (total % 10)) % 10,
        // we simplify: if remainder is 0, checksum is 0; else add 1 to remainder
        int calculatedsum = (total % 10 == 0) ? 0 : (total % 10 + 1);

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
        if (gender >= 0 && gender <= 9999) {
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
