package com.example.flubb.week3task;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/* **********************************************
 * 프로그램명 : MainActivity.java
 * 작성자 : 2018038070 나선율
 * 작성일 : 2020.04.12
 *프로그램 설명 : 3주차 과제 프로그램의 Java코드
 ************************************************/

public class MainActivity extends AppCompatActivity {
    Button showBtn, urlBtn;
    EditText txt;
    RadioGroup group;
    RadioButton warm, cool;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView)findViewById(R.id.imageView);

        showBtn = (Button)findViewById(R.id.showBtn);
        urlBtn = (Button)findViewById(R.id.urlBtn);

        txt = (EditText)findViewById(R.id.editText);

        group = (RadioGroup)findViewById(R.id.toggle);
        warm = (RadioButton)findViewById(R.id.warm);
        cool = (RadioButton)findViewById(R.id.cool);

        String defUrl = "https://cbnu.blackboard.com";
        txt.setText(defUrl);

        showBtn.setOnClickListener((arg0)->{
            Toast.makeText(getApplicationContext(), txt.getText(),Toast.LENGTH_SHORT).show();
        });

        urlBtn.setOnClickListener((arg0)->{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(txt.getText().toString()));
            startActivity(browserIntent);
        });

        group.setOnCheckedChangeListener((arg0, arg1)-> {
            image.setVisibility(android.view.View.VISIBLE);
            switch (group.getCheckedRadioButtonId()) {
                case R.id.warm:
                    image.setImageResource(R.drawable.warmphoto);
                    break;
                case R.id.cool:
                    image.setImageResource(R.drawable.coolphoto);
                    break;
            }
        });
    }
}
