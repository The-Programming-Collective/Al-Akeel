package com.redhat.project.util;

import java.util.LinkedList;
import java.util.Map;

//TODO add date check
public class PaymentAuthenticator {

    public boolean Pay(Map<String,String> data) {
        String CCN = data.get("CCN");
        String PIN = data.get("PIN");
        if(!CCN.matches("-?\\d+(\\.\\d+)?") || 
            CCN.length()!=16 || 
            PIN.length()!=4 || 
            !PIN.matches("-?\\d+(\\.\\d+)?"))
            return false;
        return true;
    }

}