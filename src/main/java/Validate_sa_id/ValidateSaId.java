package Validate_sa_id;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class ValidateSaId {

    public static boolean isIdNumberValid(String idNumber) {
        return false;

    }

    //The first 6 digits (YYMMDD) are based on your date of birth.
    public static boolean isDateValid(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuMMdd")
                .withResolverStyle(ResolverStyle.STRICT);

        try{
            LocalDate.parse(date,formatter);
            return true;

        }catch( DateTimeParseException e){
            return false;
        }
    }


}