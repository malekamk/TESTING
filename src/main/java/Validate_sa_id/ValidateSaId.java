package Validate_sa_id;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class ValidateSaId {

    public static boolean isIdNumberValid(String idNumber) {
        if(idNumber == null || idNumber.length() != 13) return false;
        if(!isOnlyDigit(idNumber)) return false;

        return true;

    }

    public static boolean isOnlyDigit(String idNumber) {
        for(char c : idNumber.toCharArray()){
            if(!Character.isDigit(c)) return false;
        }
        return true;
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