package mushirih.thoughtleadership2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.toe.chowder.Chowder;
import com.toe.chowder.interfaces.PaymentListener;

/**
 * Created by p-tah on 08/02/2016.
 */
public class About extends AppCompatActivity implements PaymentListener {

    String PAYBILL_NUMBER = "898998";
    String XPAT_PAYBILL="903400 ";
    String PASSKEY = "ada798a925b5ec20cc331c1b0048c88186735405ab8d59f968ed4dab89da5515";
    Chowder chowder;
    String phoneNumber="0712613052";
    int amount=0;
    Button place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        Toolbar toolbar= (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Place Ad");
         place=findViewById(R.id.place);
        final String productId = "1717171717171";
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount==0){
                    Toast.makeText(getApplicationContext(),"Please select ad feature",Toast.LENGTH_LONG).show();
                }else {
                    Chowder chowder = new Chowder(About.this, PAYBILL_NUMBER, PASSKEY, About.this);


                    //You can then use processPayment() to process individual payments
                    chowder.processPayment(String.valueOf(amount), phoneNumber, productId);
                }
            }
        });



    }


    public void click(View view) {
        if(((CheckBox)view).isChecked()) {
            switch (view.getId()) {
                case R.id.a:
                    amount=amount+550;
                    break;
                case R.id.b:
                    amount=amount+650;
                    break;
                case R.id.c:
                    amount=amount+750;
                    break;
                case R.id.d:
                    amount=amount+40;
                    break;
                case R.id.e:
                    amount=amount+150;
                    break;
                case R.id.f:
                    amount=amount+250;
                    break;
                case R.id.g:
                    amount=amount+100;
                    break;
                case R.id.h:
                    amount=amount+200;
                    break;
                case R.id.i:
                    amount=amount+550;
                    break;
                case R.id.j:
                    amount=amount+4500;
                    break;
                case R.id.k:
                    amount=amount+4500;
                    break;
                case R.id.l:
                    amount=amount+250;
                    break;
                case R.id.m:
                    amount=amount+150;
                    break;
                case R.id.n:
                    amount=amount+250;
                    break;
                case R.id.o:
                    amount=amount+350;
                    break;
                case R.id.p:
                    amount=amount+450;
                    break;
                case R.id.q:
                    amount=amount+0;
                    break;
                case R.id.r:
                    amount=amount+350;
                    break;

            }
        }
        place.setText("Place Ad worth Ksh "+amount);
    }

    @Override
    public void onPaymentReady(String returnCode, String processDescription, String merchantTransactionId, String transactionId) {
        //The user is now waiting to enter their PIN
        //You can use the transaction id to confirm payment to make sure you store the ids somewhere if you want the user to be able to check later
        //Save the transaction ID
        SharedPreferences sp = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        sp.edit().putString("chowderTransactionId", transactionId).apply();

        new AlertDialog.Builder(About.this)
                .setTitle("Payment in progress")
                .setMessage("Please wait for a pop up from Safaricom and enter your Bonga PIN.")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Well you can skip the dialog if you want, but it will make the user feel safer, they'll know what's going on instead of sitting there
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void onPaymentSuccess(String merchantId, String msisdn, String amount, String mpesaTransactionDate, String mpesaTransactionId, String transactionStatus, String returnCode, String processDescription, String merchantTransactionId, String encParams, String transactionId) {
        //When the payment is complete you have the option to subscribe the user
        //This means that after a set period of time you can prompt the user to make another payment if the subscription is invalid
        String productId = "1717171717171";
        chowder.subscribeForProduct(productId, Chowder.SUBSCRIBE_DAILY);

        //After a month, if you check the product's subscription it will be invalid, but before it will be valid

        //The payment was successful.
        new AlertDialog.Builder(About.this)
                .setTitle("Payment confirmed")
                .setMessage(transactionStatus + ". Your amount of Ksh." + amount + " has been successfully paid from " + msisdn + " to PayBill number " + merchantId + " with the M-Pesa transaction code " + mpesaTransactionId + " on " + mpesaTransactionDate + ".\n\nThank you for your business.")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Well you can skip the dialog if you want, but it might make the user feel safer
                        //The user has successfully paid so give them their goodies
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void onPaymentFailure(String merchantId, String msisdn, final String amount, String transactionStatus, String processDescription) {
        //The payment has failed.
        new AlertDialog.Builder(About.this)
                .setTitle("Payment failed")
                .setMessage(transactionStatus + ". Your amount of Ksh." + amount + " was not paid from " + msisdn + " to PayBill number " + merchantId + ". Please try again.")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .setPositiveButton("Pay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Your product's ID must have 13 digits
                        String productId = "1717171717171";

                        chowder.processPayment(amount, phoneNumber.replaceAll("\\+", ""), productId);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Well you can skip the dialog if you want, but it might make the user feel safer
                //The user has successfully paid so give them their goodies
                dialog.dismiss();
            }
        }).show();
    }
}
