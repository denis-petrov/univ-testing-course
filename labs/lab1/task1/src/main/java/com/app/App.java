package com.app;

import com.app.exception.NotTriangleException;
import com.app.model.Triangle;
import com.app.model.TriangleFacade;

import java.util.Arrays;

import static com.app.util.MessageUtil.*;

public class App {

    public static void main(String[] args) {
        try {
            System.out.print("Input parameters: ");
            Arrays.stream(args)
                    .forEach(e -> System.out.print(e + " "));
            System.out.println();
            Triangle triangle = TriangleFacade.buildTriangle(args[0], args[1], args[2]);
            System.out.println("Triangle type: " + triangle.getTriangleType());
        } catch (NumberFormatException nfe) {
            System.out.println(INCORRECT_INPUT_PARAMETERS);
        } catch (NotTriangleException nte) {
            System.out.println(NOT_TRIANGLE);
        } catch (Exception e) {
            System.out.println(UNKNOWN_ERROR);
        }
    }
}
