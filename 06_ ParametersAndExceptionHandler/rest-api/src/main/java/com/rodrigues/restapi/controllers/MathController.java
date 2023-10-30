package com.rodrigues.restapi.controllers;

import com.rodrigues.restapi.exceptions.UnsuportedMathOperation;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/calc")
public class MathController {

    private static final String template = "Hello, %s!";
    private static final AtomicLong counter = new AtomicLong();


    @RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public String sum(
            @PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo
    ) {
        if (numberOne == null || numberTwo == null) throw new UnsuportedMathOperation("Both numbers must not be null");

        numberOne = replaceComma(numberOne);
        numberTwo = replaceComma(numberTwo);

        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsuportedMathOperation("Both numbers must be numeric.");

        double numOne = convertToDouble(numberOne);
        double numTwo = convertToDouble(numberTwo);
        double sum = numOne + numTwo;

        return String.format("%.2f + %.2f = %.2f", numOne, numTwo, sum);
    }

    private boolean isNumeric(String number) {
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

    private Double convertToDouble(String number) {
        return Double.parseDouble(number);
    }

    private String replaceComma(String number) {
        return number.replace(",", ".");
    }





}
