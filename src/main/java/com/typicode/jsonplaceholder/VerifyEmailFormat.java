package com.typicode.jsonplaceholder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyEmailFormat {

    public boolean verifyEmail(ArrayList<String> email){
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);

        for(String singeEmail : email){
            System.out.println("The email is: "+singeEmail+"\n");
            Matcher matcher = pattern.matcher(singeEmail);
            return true;
        }
        return false;
    }


}
