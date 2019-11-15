package by.attrade.util;

import org.springframework.ui.Model;

import java.util.List;

public class ErrorMessageAttributeWrapper {
    public static void wrapErrorsAsAttributes(List<Pair<String, Exception>> errors, Model model) {
        for (Pair<String, Exception> p : errors) {
            model.addAttribute(p.getFirst(), p.getSecond().getMessage());
        }
    }
}
