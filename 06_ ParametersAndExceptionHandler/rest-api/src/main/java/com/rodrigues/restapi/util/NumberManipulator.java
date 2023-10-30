package com.rodrigues.restapi.util;

import com.rodrigues.restapi.exceptions.UnsuportedMathOperation;

public class NumberManipulator {
    public void validateInput(String numberOne, String numberTwo) {
        if (numberOne == null || numberTwo == null || !isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new UnsuportedMathOperation("Both numbers must be valid numeric values.");
        }
    }
    public void validateInput(String number) {
        if (number == null || !isNumeric(number) ) {
            throw new UnsuportedMathOperation("Both numbers must be valid numeric values.");
        }
    }

    public boolean isNumeric(String number) {
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

    public Double convertToDouble(String number) {
        number = replaceComma(number);
        return Double.parseDouble(number);
    }

    public String replaceComma(String number) {
        return number.replace(",", ".");
    }

}
