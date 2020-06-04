package app.android.ww.com.myfit;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SleepTime extends AppCompatActivity {

    Date currentDate = Calendar.getInstance().getTime();
    String date = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(currentDate);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleeptime);

        File file = new File(date+".txt");
        try{
            if(file.exists()){
                TextView sText = (TextView)findViewById(R.id.text_sleepTime);
                TextView wText = (TextView)findViewById(R.id.text_wakeTime);
                TextView sLength = (TextView)findViewById(R.id.text_sleepLength);

                String sleep = ReadTextFile(date+".txt");
                String wake = ReadTextFile(date+".txt");
                int length;
                sText.setText("수면 시각: "+sleep.charAt(0)+sleep.charAt(1)+"시 "+sleep.charAt(2)+sleep.charAt(3)+"분");
                wText.setText("기상 시각: "+wake.charAt(0)+wake.charAt(1)+"시 "+wake.charAt(2)+wake.charAt(3)+"분");
                int sleepI = Integer.parseInt(sleep);
                int wakeI = Integer.parseInt(wake);
                if(sleepI > wakeI) {
                    wakeI += 2400;
                }
                length = wakeI - sleepI;
                if((length % 100) != (wakeI%100 - sleepI%100)){
                    length -= 40;
                }
                String lengthS = getTime(length==0?0:length/100, length%100);
                sLength.setText(lengthS.charAt(0)+lengthS.charAt(1)+"시간 "+lengthS.charAt(2)+lengthS.charAt(3)+"분");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String ReadTextFile(String path){
        StringBuffer strBuffer = new StringBuffer();
        try{
            InputStream is = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line="";
            while((line=reader.readLine())!=null){
                strBuffer.append(line+"\n");
            }

            reader.close();
            is.close();
        }catch (IOException e){
            e.printStackTrace();
            return "";
        }
        return strBuffer.toString();
    }
    //https://ghj1001020.tistory.com/308

    public String getTime(int hour, int min){
        String output = "";
        if(hour < 10){
            output += 0;
            if(hour == 0){
                output += 0;
            }
        }
        output += Integer.toString(hour);
        if(min < 10){
            output += 0;
            if(min == 0){
                output += 0;
            }
        }
        output += Integer.toString(hour);
        return output;
    }


    public void onClick(View view) {
        TimePickerDialog.OnTimeSetListener firstListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                TextView sText = (TextView)findViewById(R.id.text_sleepTime);
                File file = new File(date+".txt");
                try{
                    if(file.exists()){
                        file.delete();
                    }
                    file.mkdirs();
                }catch(Exception e){
                    e.printStackTrace();
                }

                String time = getTime(i, i1)+"\n";
                try{
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(time.getBytes());
                    fos.close();
                }catch(IOException e){}
                sText.setText("수면 시각: "+Integer.toString(i)+"시 "+Integer.toString(i1)+"분");
            }
        };

        TimePickerDialog.OnTimeSetListener secondListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                TextView wText = (TextView)findViewById(R.id.text_wakeTime);

                File file = new File(date+".txt");
                String time = getTime(i,i1);
                try{
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(time.getBytes());
                    fos.close();
                }catch(IOException e){}
                wText.setText("기상 시각: "+Integer.toString(i)+"시 "+Integer.toString(i1)+"분");
            }
        };

        TimePickerDialog sleepTime = new TimePickerDialog(SleepTime.this,firstListener,0,0,false);
        sleepTime.setTitle("취침 시각");
        sleepTime.show();
        TimePickerDialog wakeTime = new TimePickerDialog(SleepTime.this,secondListener,0,0,false);
        wakeTime.setTitle("기상 시각");
        wakeTime.show();
    }
}
