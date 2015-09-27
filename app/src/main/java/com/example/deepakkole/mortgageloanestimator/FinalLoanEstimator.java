package com.example.deepakkole.mortgageloanestimator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by deepakkole on 9/19/15.
 */
public class FinalLoanEstimator extends Fragment {
    EditText totaltaxpaid;
    EditText totalinterestpaid;
    EditText monthlypayment;
    EditText day;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =inflater.inflate(R.layout.finalloanestimator,container,false);
        totaltaxpaid=(EditText) view.findViewById(R.id.txttotaltaxpaid);
        day=(EditText) view.findViewById(R.id.txtpayoffdate);
        totalinterestpaid=(EditText) view.findViewById(R.id.txttotalinterestpaid);
        monthlypayment=(EditText) view.findViewById(R.id.txtmonthlypayment);
        return view;
    }

    public void setValues(double totalPropertyTax,double totalMonthlyMortgagePayment,double totalInterestPaid, int months){

        totalinterestpaid.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        Map<String, Double> hashValues=new HashMap<String,Double>();
        hashValues.put("totalPropertyTax",totalPropertyTax);
        hashValues.put("totalInterestPaid",totalInterestPaid);
        hashValues.put("totalMonthlyMortgagePayment", totalMonthlyMortgagePayment);
        totaltaxpaid.setText(hashValues.get("totalPropertyTax").toString());
        totalinterestpaid.setText(hashValues.get("totalInterestPaid").toString());
        monthlypayment.setText(hashValues.get("totalMonthlyMortgagePayment").toString());
        String payoffDay = payOffDate(months);
        day.setText(payoffDay);
    }

    public void resetValues(){
        totaltaxpaid.setText("");
        day.setText("");
        monthlypayment.setText("");
        totalinterestpaid.setText("");
    }
    public String payOffDate(int months){
        String dateFormat=null;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format=new SimpleDateFormat("dd-MMMM-yyyy");
        cal.add(Calendar.MONTH, months-1);
        dateFormat =format.format(cal.getTime());
        return dateFormat;
    }
}
