package app.android.ww.com.myfit;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*
 * 운동시간 타이머
 *
 * 기능
 * 1. start : 운동 시작 (타이머 시작)
 * 2. pause : 운동 일시중지 (타이머 일시중지)
 * 3. stop : 운동 종료 //추가적으로 구현필요
 *
 */

public class TimerActivity extends AppCompatActivity {

    TextView textView ;
    Button start, pause, stop;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    int Seconds, Minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        textView = findViewById(R.id.textView);
        start = findViewById(R.id.start);
        pause = findViewById(R.id.pause);
        stop = findViewById(R.id.stop);
        handler = new Handler() ;

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeBuff += MillisecondTime;
                handler.removeCallbacks(runnable);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 운동을 멈출 때 로직 구현

            }

        });

    }

    //타이머 기능 함수
    public Runnable runnable = new Runnable() {
        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;

            System.out.println("Minutes :" +Minutes);
            System.out.println("Second :" +Seconds);


            textView.setText("" + String.format("%02d", Minutes)
                                + ":"
                                + String.format("%02d", Seconds));
            handler.postDelayed(this, 0);
        }
    };
}
