package com.example.park.myapplication;

import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import android.widget.RelativeLayout;
import android.widget.Switch;

public class VoteActivity extends Activity {

    EditText firstKeyword, secondKeyword, voteTitle;
    Button uploadVote;
    Switch creVote;
    RelativeLayout vote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        firstKeyword     = (EditText) findViewById(R.id.firstKeyword);
        secondKeyword    = (EditText) findViewById(R.id.secondKeyword);
        voteTitle        = (EditText) findViewById(R.id.voteTitle);
        vote             = (RelativeLayout) findViewById(R.id.vote);
        creVote          = (Switch) findViewById(R.id.creVote);
        uploadVote       = (Button) findViewById(R.id.uploadVote);

        uploadVote.setOnClickListener(btnListener);

        creVote.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    vote.setVisibility(View.VISIBLE);
                } else {
                    vote.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
                    Intent intent = new Intent(VoteActivity.this, VoterActivity.class);

                    intent.putExtra("voteTitle", voteTitle.getText().toString());
                    String myKeyword[] = new String[] {firstKeyword.getText().toString(),secondKeyword.getText().toString()};
                    intent.putExtra("keyword",myKeyword);
                    startActivity(intent);
        }
    };
}