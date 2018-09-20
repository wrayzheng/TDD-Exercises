import static java.lang.Character.isDigit;

class IsbnVerifier {

    private String isbnWithoutDashes;

    boolean isValid(String stringToVerify) {
        isbnWithoutDashes = trimDashes(stringToVerify);
        return validateLength()
            && validateDigitInIsbn()
            && validateCheckDigit()
            && validateCheckSum();
    }

    String trimDashes(String stringToVerify) {
        return stringToVerify.replaceAll("-", "");
    }

    boolean validateLength() {
        return isbnWithoutDashes.length() == 10;
    }

    boolean validateDigitInIsbn() {
        for (int i = 0; i < 9; i++) {
            char c = isbnWithoutDashes.charAt(i);
            if (!isDigit(c)) return false;
        }
        return true;
    }

    boolean validateCheckDigit() {
        char c = isbnWithoutDashes.charAt(9);
        return c == 'X' || (c >= '0' && c <= '9');
    }

    boolean validateCheckSum() {
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            char c = isbnWithoutDashes.charAt(i);
            int weight = 10 - i;
            sum += charToDigit(c) * weight;
        }
        return sum % 11 == 0;
    }

    int charToDigit(char c) {
        return c == 'X' ? 10 : c - '0';
    }

}
