package com.example.hp.mychat;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class TypeMessage extends AppCompatActivity {

    private Toolbar toolbar;

    private EditText edittxtMessage;
    private EditText editNumber;
    private ImageView sendImagetxt;
    //    private ImageView sendvoiceimg;
    private final int REQ_CODE_SPEECH_INPUT = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_message);
/*      toolbar = (Toolbar)findViewById(R.id.mainToolBar);
        setSupportActionBar(toolbar); */

        editNumber =(EditText)findViewById(R.id.idEnnterNumber);
        edittxtMessage= (EditText)findViewById(R.id.idEnterMessage);
        sendImagetxt=(ImageView)findViewById(R.id.imageSendTextMessage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            Intent back = new Intent(TypeMessage.this , MainActivity.class);
            startActivity(back);
        }
        return true;
    }

    public void mthdShowContacts(View view) {
        Intent next = new Intent(TypeMessage.this ,ShowContacts.class);
        startActivity(next);
    }

    public void mthdSendTextMessage(View view) {

      //  askSpeechInput();

        String myMessage = edittxtMessage.getText().toString();
        String myNumber = editNumber.getText().toString();
        try{
           // int checkNumb  = Integer.parseInt(myNumber);
            sendMsg(myNumber , myMessage);
         /*   Intent backMain = new Intent(TypeMessage.this , MainActivity.class);
            startActivity(backMain); */

        }
        catch (Exception e){
            Toast.makeText(TypeMessage.this , "Enter Correrct Number " , Toast.LENGTH_SHORT ).show();
        }

    }

    ////////////////////////////////////////////////

    private void askSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Hi speak something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    // Receiving speech input

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    edittxtMessage.setText(result.get(0));          // Here i call method to send message

                }
                break;
            }

        }
    }


    ///////////////////////////////////////////////

    private void sendMsg(String myNumber, String myMessage) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(myNumber ,null,myMessage,null,null);
    }

    public void methdCheckTxt(View view) {
        Toast.makeText(TypeMessage.this , "Touchh " , Toast.LENGTH_SHORT ).show();
        // sendvoiceimg.setImageResource(R.drawable.sendmsg);
        //   sendvoiceimg.setVisibility(View.GONE);
        //   sendImagetxt.setImageResource(R.drawable.sendmsg);

    }



    public void mthdSendVoiceMessage(View view) {
        Toast.makeText(TypeMessage.this , "voicsss " , Toast.LENGTH_SHORT ).show();
    }
}
