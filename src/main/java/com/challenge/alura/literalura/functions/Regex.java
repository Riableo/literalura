package com.challenge.alura.literalura.functions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public boolean matchingLetter(String letterLastName){
        Pattern pattern = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(letterLastName);
        return matcher.find();
    }
}
