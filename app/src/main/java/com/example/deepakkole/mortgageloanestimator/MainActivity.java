package com.example.deepakkole.mortgageloanestimator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements LoanEstimator.PassValuetoFragment2 {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void passValue(double totalPropertyTax,double totalMonthlyMortgagePayment,double totalInterestPaid, int months) {
        FinalLoanEstimator finalLoanEstimator = (FinalLoanEstimator) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        finalLoanEstimator.setValues(totalPropertyTax, totalMonthlyMortgagePayment, totalInterestPaid, months);
    }
     @Override
     public void reset(){
         FinalLoanEstimator finalLoanEstimator = (FinalLoanEstimator) getSupportFragmentManager().findFragmentById(R.id.fragment2);
         finalLoanEstimator.resetValues();
     }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
