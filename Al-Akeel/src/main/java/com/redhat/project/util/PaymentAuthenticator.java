package com.redhat.project.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;



//TODO add date check
public class PaymentAuthenticator {

    public boolean pay(CreditCardInfo ccd) {
        String CCN = ccd.CCN;
        String CVV = ccd.CVV;
        String Date = ccd.DATE;

        System.out.println(validDate(Date) + "========================================================================================================");

        if(!CCN.matches("-?\\d+(\\.\\d+)?") || 
            CCN.length()!=16 || 
            CVV.length()!=3 || 
            !CVV.matches("-?\\d+(\\.\\d+)?")||
            !validDate(Date))
            return false;
        return true;
    }

    private boolean validDate(String cardDate){
        // Get current month and year
        YearMonth currentYearMonth = YearMonth.now();
        int currentMonth = currentYearMonth.getMonthValue();
        int currentYear = currentYearMonth.getYear() %100;

        System.out.println(currentMonth + "==============================================CURRENT MONTH==========================================================");
        System.out.println(currentYear + "==============================================CURRENT YEAR==========================================================");

        //     02/21
        int cardMonth = Integer.parseInt(cardDate.substring(0, 2));
        System.out.println(Integer.parseInt(cardDate.substring(0, 2)) + "==============================================FIRST PART==========================================================");
        int cardYear = Integer.parseInt(cardDate.substring(3));
        System.out.println(Integer.parseInt(cardDate.substring(3)) + "==============================================SECOND PART==========================================================");

        return !((cardYear < currentYear) || (cardYear == currentYear && cardMonth < currentMonth));
        
      
        
 
    }

}