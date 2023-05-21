package com.redhat.project.util;


import java.time.YearMonth;



//TODO add date check
public class PaymentAuthenticator {

    public boolean pay(CreditCardInfo ccd) {
        String CCN = ccd.CCN;
        String CVV = ccd.CVV;
        String Date = ccd.DATE;

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

        //     02/21
        int cardMonth = Integer.parseInt(cardDate.substring(0, 2));
        int cardYear = Integer.parseInt(cardDate.substring(3));

        return !((cardYear < currentYear) || (cardYear == currentYear && cardMonth < currentMonth));
 
    }

}