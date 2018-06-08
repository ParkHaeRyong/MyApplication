package com.example.park.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Button;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class VoterActivity extends Activity {
    Button inesrtVote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter);
        inesrtVote = (Button) findViewById(R.id.insertVote);

        inesrtVote.setOnClickListener(btnListener);

        Intent intent = getIntent();
        String voteTitle = intent.getStringExtra("voteTitle");
        String[] keyword = intent.getStringArrayExtra("keyword");

        fnRadioTextSet(keyword, voteTitle);
    }

    private void fnRadioTextSet(String keyword[], String voteTitle) {
        RadioGroup rbtnGrp = (RadioGroup) findViewById(R.id.radioGroup);
        TextView textView = (TextView) findViewById(R.id.textView);

        String[] key = Arrays.toString(keyword).split(",");

        textView.setText(voteTitle);
        for (int i = 0; i < rbtnGrp.getChildCount(); ++i) {
            ((RadioButton) rbtnGrp.getChildAt(i)).setText(key[i].toString().replace("[", "").replace("]", ""));
        }


    }

    class CustomTask extends AsyncTask<Object, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Object... params) {
            try {
                String str;

                for(int i = 0 ; i<params.length; i++) {
                    System.out.println(params[i]);
                }
                URL url = new URL("http://192.168.100.119:8080/webProject/" + params[0]);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                sendMsg = "param=" + params[1];
                System.out.println(sendMsg);
                osw.write(sendMsg);
                osw.flush();

                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();

                } else {
                    Log.i("통신 결과", conn.getResponseCode() + "에러");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMsg;
        }

        @Override
        protected void onPostExecute(String receiveMsg) {
            super.onPostExecute(receiveMsg);
        }

    }
    final View.OnClickListener btnListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            RadioGroup rbtnGrp       = (RadioGroup) findViewById(R.id.radioGroup);
            RadioButton radioButton  = (RadioButton) findViewById(R.id.radioButton);
            TextView textView        = (TextView) findViewById(R.id.textView);
            String jsp               = "androidVoteInsert.jsp";

            JSONObject jsonObject = new JSONObject();

           if(radioButton.isChecked()){
               try {
                   jsonObject.put("chk", "0");
                   jsonObject.put("title", textView.getText().toString());

                   new CustomTask().execute(jsp,jsonObject.toString());
               }catch (Exception e){
                   e.printStackTrace();
               }
           }else{
               try {
                   jsonObject.put("chk", "1");
                   jsonObject.put("title", textView.getText().toString());

                   new CustomTask().execute(jsp,jsonObject.toString());
               }catch (Exception e){
                   e.printStackTrace();
               }
           }
        }
    };
}
