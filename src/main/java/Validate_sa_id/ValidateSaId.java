package Validate_sa_id;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class ValidateSaId {

    public static boolean isIdNumberValid(String idNumber) {
        if(idNumber == null || idNumber.length() != 13) return false;
        if(!isOnlyDigit(idNumber)) return false;
        if(!isDateValid(idNumber.substring(0,6))) return false;
        if(!validGender(idNumber.substring(6,10))) return false;
        int citizenship = Character.getNumericValue(idNumber.charAt(10));
        if (citizenship != 1  && citizenship != 0) return false;
        if(Character.getNumericValue(idNumber.charAt(11)) != 8) return false;
        if(!luhnAlgorithm(idNumber)) return false;

        return true;
    }

    public static boolean luhnAlgorithm(String idNumber) {

        int odd = 0;
        StringBuilder evenNumbers = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            int number = Character.getNumericValue(idNumber.charAt(i));
            if((i + 1 ) % 2 == 0){
                evenNumbers.append(number);
            }
            odd += number;
        }

        int even = Integer.parseInt(evenNumbers.toString()) * 2;
        int evensum = 0;
        for(char i : String.valueOf(even).toCharArray() ){
            evensum += Character.getNumericValue(i);
        }

        int total = odd + evensum;
        int calculatedsum = (total % 10 == 0) ? 0 : (total % 10 + 1) ;

        int checkSum = Character.getNumericValue(idNumber.charAt(12));

        return checkSum == calculatedsum;
    }


    public static boolean validGender(String genderNumber) {
        int gender = Integer.parseInt(genderNumber);
        if(gender >= 0000 && gender <= 9999 ) {
            return true;
        }
        return false;

    }

    public static boolean isOnlyDigit(String idNumber) {
        for(char c : idNumber.toCharArray()){
            if(!Character.isDigit(c)) return false;
        }
        return true;
    }

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