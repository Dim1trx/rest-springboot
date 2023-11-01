package com.rodrigues.restapi.controllers;

import com.rodrigues.restapi.util.NumberManipulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calc")
public class MathController {
    @Autowired
    private NumberManipulator manipulator;

    @GetMapping(value = "/sum/{numberOne}/{numberTwo}")
    public String sum(
            @PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo
    ) {
        manipulator.validateInput(numberOne, numberTwo);

        double numOne = manipulator.convertToDouble(numberOne);
        double numTwo = manipulator.convertToDouble(numberTwo);

        double sum = numOne + numTwo;

        return String.format("%.2f + %.2f = %.2f", numOne, numTwo, sum);
    }

    @GetMapping(value = "/subtraction/{numberOne}/{numberTwo}")
    public String subtraction(
            @PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo
    ) {
        manipulator.validateInput(numberOne, numberTwo);

        double numOne = manipulator.convertToDouble(numberOne);
        double numTwo = manipulator.convertToDouble(numberTwo);

        double subtraction = numOne - numTwo;

        return String.format("%.2f - %.2f = %.2f", numOne, numTwo, subtraction);
    }

    @GetMapping(value = "/multiplication/{numberOne}/{numberTwo}")
    public String multiplication(
            @PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo
    ) {
        manipulator.validateInput(numberOne, numberTwo);

        double numOne = manipulator.convertToDouble(numberOne);
        double numTwo = manipulator.convertToDouble(numberTwo);

        double multiplication = numOne * numTwo;

        return String.format("%.2f * %.2f = %.2f", numOne, numTwo, multiplication);
    }

    @GetMapping(value = "/division/{numberOne}/{numberTwo}")
    public String division(
            @PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo
    ) {
        manipulator.validateInput(numberOne, numberTwo);

        double numOne = manipulator.convertToDouble(numberOne);
        double numTwo = manipulator.convertToDouble(numberTwo);

        double division = numOne / numTwo;

        return String.format("%.2f / %.2f = %.2f", numOne, numTwo, division);
    }

    @GetMapping(value = "/sqrt/{number}")
    public String sqrt(@PathVariable(value = "number") String number) {
        manipulator.validateInput(number);

        double num = manipulator.convertToDouble(number);

        double sqrt = Math.sqrt(num);

        return String.format("√%.2f = %.2f", num, sqrt);
    }

    @GetMapping(value = "/average/{numberOne}/{numberTwo}")
    public String average(
            @PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo
    ) {
        manipulator.validateInput(numberOne, numberTwo);

        double numOne = manipulator.convertToDouble(numberOne);
        double numTwo = manipulator.convertToDouble(numberTwo);

        double average = (numOne + numTwo) / 2.0;

        return String.format("Average = %.2f", average);
    }


}
