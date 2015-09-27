package com.example.deepakkole.mortgageloanestimator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by deepakkole on 9/18/15.
 */
public class LoanEstimator extends Fragment{

    private static EditText homeValue;
    private static  EditText downPayment;
    private static  EditText apr;
    private static  Spinner spTerm;
    private static  String spTermsText;
    private static  EditText propertytaxValue;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        final View view= inflater.inflate(R.layout.loanestimator,container,false);

        // load EditText into the memory
        homeValue=(EditText)view.findViewById(R.id.txthomevalue);
        downPayment=(EditText)view.findViewById(R.id.txtdownpayment);
        apr=(EditText)view.findViewById(R.id.txtapr);
        spTerm=(Spinner) view.findViewById(R.id.spTerms);

        propertytaxValue=(EditText)view.findViewById(R.id.propertytaxrate);

        //load TextView into the memory
        final TextView hometext=(TextView)view.findViewById(R.id.reqdHomeValueText);
        final TextView downText=(TextView) view.findViewById(R.id.reqddownValueText);
        final TextView aprText=(TextView) view.findViewById(R.id.reqdaprValueText);
        final TextView termsText=(TextView) view.findViewById(R.id.reqdtermsValueText);
        final TextView propertyTaxText =(TextView) view.findViewById(R.id.reqdpropertyTaxValueText);

        //load Spinner with values:

        //button Listener
        Button btnCalc =(Button)view.findViewById(R.id.btncalc);

        //EditText Listeners
        homeValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(homeValue.getText().length()==0 && !hasFocus){
                    hometext.setText("This is required field!!!");
                    aprText.setText("");
                    downText.setText("");
                    termsText.setText("");
                    propertyTaxText.setText("");
                }else{
                    hometext.setText("");
                }
            }
        });

        downPayment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(downPayment.getText().length()==0 && !hasFocus) {
                    downText.setText("This is required field!!!");
                    hometext.setText("");
                    aprText.setText("");
                    termsText.setText("");
                    propertyTaxText.setText("");

                }else{
                    downText.setText("");
                }
            }
        });

        apr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (apr.getText().length() == 0 && !hasFocus) {
                    aprText.setText("This is required field!!!");
                    hometext.setText("");
                    downText.setText("");
                    termsText.setText("");
                    propertyTaxText.setText("");
                }else{
                        aprText.setText("");
                    }


            }
        });

        propertytaxValue.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (propertytaxValue.getText().length() == 0 && !hasFocus) {
                    aprText.setText("");
                    hometext.setText("");
                    downText.setText("");
                    termsText.setText("");
                    propertyTaxText.setText("This is required field!!!");
                }else{
                    propertyTaxText.setText("");
                }

            }
        });

        //Button Listener
        btnCalc.setOnClickListener(new View.OnClickListener() {
            Map<String, Double> finalValues = new HashMap<String, Double>();
            @Override
            public void onClick(View v) {

                spTermsText = spTerm.getSelectedItem().toString();
                if(homeValue.getText().length()!=0 && downPayment.getText().length()!=0
                        && apr.getText().length()!=0 && spTermsText.length()!=0 && propertytaxValue.getText().length()!=0){
                    double principleLoanAmount=Double.parseDouble(homeValue.getText().toString());
                    int termsInYears = Integer.parseInt(spTermsText);
                    //double interestRatePerMonth=Integer.parseInt(apr.getText().toString());
                    double downPaymentValue=Double.parseDouble((downPayment.getText().toString()));
                    double aprValue=Double.parseDouble((apr.getText().toString()));
                    double propertyTax=0;
                    propertyTax = Double.parseDouble((propertytaxValue.getText().toString()));
                    //double finalhomeLoanValue=0;
                    double result=0;

                    if(aprValue==0){
                        aprText.setText("Cannot be Zero");
                        apr.setText("");
                    }else {
                        if (downPaymentValue > 0) {
                            double finalhomeLoanValue = principleLoanAmount - downPaymentValue;
                            double totalPropertyTax= (propertyTax==0? 0:(principleLoanAmount * (propertyTax/100) * termsInYears));
                            double  monthlyPropertyTax = (propertyTax==0? 0:(totalPropertyTax/(termsInYears*12)));
                            double aprInterestRate=aprValue/100;
                            int totalMonths = termsInYears * 12;
                            double numerator= Math.pow((1 + aprInterestRate/12), totalMonths);
                            double denominator = numerator - 1;
                            double monthlyMortgagePayment = finalhomeLoanValue * (aprInterestRate/12) * numerator / denominator ;
                            double totalMonthlyMortgagePayment = monthlyMortgagePayment + monthlyPropertyTax;
                            double totalInterestPaid = (totalMonthlyMortgagePayment * totalMonths) - finalhomeLoanValue - totalPropertyTax;
                            //result = totalMonthlyMortgagePayment;
                            sendValues(totalPropertyTax,totalMonthlyMortgagePayment,totalInterestPaid,totalMonths);
                        } else {
                            double totalPropertyTax= (propertyTax==0? 0:(principleLoanAmount * (propertyTax/100) * termsInYears));
                            double  monthlyPropertyTax = (propertyTax==0? 0:(totalPropertyTax/(termsInYears*12)));
                            double aprInterestRate=aprValue/100;
                            int totalMonths = termsInYears * 12;
                            double numerator= Math.pow((1 + aprInterestRate/12), totalMonths);
                            double denominator = numerator - 1;
                            double monthlyMortgagePayment = principleLoanAmount * (aprInterestRate/12) * numerator / denominator ;
                            double totalMonthlyMortgagePayment = monthlyMortgagePayment + monthlyPropertyTax;
                            double totalInterestPaid = (monthlyMortgagePayment * totalMonths) - principleLoanAmount - totalPropertyTax;
                            result =totalMonthlyMortgagePayment;
                            sendValues(totalPropertyTax,totalMonthlyMortgagePayment,totalInterestPaid,totalMonths);
                        }
                    }
                }else{
                    Toast.makeText(getActivity(),"Please fill all details !!!",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });

        //Reset Functionality

        Button btnreset =(Button) view.findViewById(R.id.btnreset);
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeValue.setText("");
                apr.setText("");
                downPayment.setText("");
                spTerm.setSelection(0);
                propertytaxValue.setText("");
                hometext.setText("");
                aprText.setText("");
                downText.setText("");
                termsText.setText("");
                reset();

            }
        });

        //load spinner

        final Spinner sp = (Spinner) view.findViewById(R.id.spTerms);

        sp.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                Toast.makeText(getActivity(), sp.getSelectedItem().toString(),
                        Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        return view;
    }

    public interface PassValuetoFragment2{
        public void passValue(double totalPropertyTax,double totalMonthlyMortgagePayment,double totalInterestPaid,int months);
        public void reset();
    }

    PassValuetoFragment2 passValuetoFragment2;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            passValuetoFragment2 = (PassValuetoFragment2) activity;

        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString());
        }
    }

    public void reset(){
        passValuetoFragment2.reset();
    }


    public void sendValues(double totalPropertyTax,double totalMonthlyMortgagePayment,double totalInterestPaid, int months){
            passValuetoFragment2.passValue(totalPropertyTax,totalMonthlyMortgagePayment,totalInterestPaid,months);
    }
}
