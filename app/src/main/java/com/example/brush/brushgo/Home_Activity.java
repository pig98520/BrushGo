package com.example.brush.brushgo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

/**
 * Created by swlab on 2017/5/5.
 */

public class Home_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button menu;
    private Button play;
    private Button stop;
    private TextView timer;
    private TextView countdown;
    private ProgressBar progressBar;
    private int defaultTime;
    private int timersec;
    private int currentTime;
    private int i;
    private FirebaseAuth auth;
    private Firebase myFirebaseRef;
    private ImageView clockArray[]=new ImageView[60];
    private String musicArray[]=new String[10];
    private CountDownTimer countdownTimer;
    private MediaPlayer music;
    private DrawerLayout drawer;
    private NavigationView navigateionView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Firebase.setAndroidContext(this);
        processView();
        setValue();
        setMusic();
        processControl();
    }

    private void setMusic() {
        music=new MediaPlayer();
        try {
            music.setDataSource(musicArray[(int) (Math.random()*musicArray.length)]);
            music.prepare();
        } catch (IOException e) {
            Toast.makeText(Home_Activity.this,"讀取不到音樂",Toast.LENGTH_LONG).show();
        }
    }
    private void setValue() {
        myFirebaseRef = new Firebase("https://brushgo-67813.firebaseio.com/setting/"+auth.getCurrentUser().getUid()+"/time");
        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                defaultTime=dataSnapshot.getValue(int.class);
                if(defaultTime==0)
                    defaultTime=180;
                timer.setText(defaultTime+"");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void processView() {
        navigateionView=(NavigationView) findViewById(R.id.nav_home);
        navigateionView.setNavigationItemSelectedListener(Home_Activity.this);
        drawer=(DrawerLayout)findViewById(R.id.drawerLayout);
        menu=(Button) findViewById(R.id.btn_menu);
        play=(Button) findViewById(R.id.btn_play);
        stop=(Button) findViewById(R.id.btn_stop);
        timer=(TextView)findViewById(R.id.txt_timer);
        countdown=(TextView)findViewById(R.id.txt_countdown);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        auth= FirebaseAuth.getInstance();
        myFirebaseRef = new Firebase("https://brushgo-67813.firebaseio.com");
        clockArray[0]=(ImageView)findViewById(R.id.imageView_1);
        clockArray[5]=(ImageView)findViewById(R.id.imageView_2);
        clockArray[10]=(ImageView)findViewById(R.id.imageView_3);
        clockArray[15]=(ImageView)findViewById(R.id.imageView_4);
        clockArray[20]=(ImageView)findViewById(R.id.imageView_5);
        clockArray[25]=(ImageView)findViewById(R.id.imageView_6);
        clockArray[30]=(ImageView)findViewById(R.id.imageView_7);
        clockArray[35]=(ImageView)findViewById(R.id.imageView_8);
        clockArray[40]=(ImageView)findViewById(R.id.imageView_9);
        clockArray[45]=(ImageView)findViewById(R.id.imageView_10);
        clockArray[50]=(ImageView)findViewById(R.id.imageView_11);
        clockArray[55]=(ImageView)findViewById(R.id.imageView_12);
        musicArray[0]="https://goo.gl/cIfw8f";
        musicArray[1]="https://goo.gl/aS34Wp";
        musicArray[2]="https://goo.gl/N8dbRu";
        musicArray[3]="https://goo.gl/Pvzkpl";
        musicArray[4]="https://goo.gl/AgMIkP";
        musicArray[5]="https://goo.gl/mqYySN";
        musicArray[6]="https://goo.gl/SrFCY8";
        musicArray[7]="https://goo.gl/dz4xE5";
        musicArray[8]="https://goo.gl/9cvuvO";
        musicArray[9]="https://goo.gl/FQULri";
    }

    private void processControl() {
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(music.isPlaying()){
                    play.setBackgroundResource(R.mipmap.ic_play_circle_outline_black_24dp);
                    music.pause();
                    timerPause();
                }
                else {
                    music.start();
                    play.setBackgroundResource(R.mipmap.ic_pause_circle_filled_black_24dp);
                    timerStart();
                }

            }

        });
        stop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                play.setBackgroundResource(R.mipmap.ic_play_circle_outline_black_24dp);
                music.stop();
                timerStop();
                setMusic();
            }
        });

    }
    private void timerStart() {
        timersec= Integer.parseInt(timer.getText().toString().trim());
        countdownTimer = new CountDownTimer(timersec * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(millisUntilFinished/1000+"");
                clcokStart();
                setProgressbar();
            }

            @Override
            public void onFinish() {
                play.setBackgroundResource(R.mipmap.ic_play_circle_outline_black_24dp);
                music.stop();
                setMusic();
                timerStop();
                clockStop();
                setProgressbar();
                finishDialog();
            }
        };
        countdownTimer.start();
    }

    private void timerPause() {
        countdownTimer.cancel();
    }

    private void timerStop() {
        countdownTimer.cancel();
        timer.setText(defaultTime+"");
        clockStop();
        setProgressbar();
    }

    private void setProgressbar() {
        currentTime = (Integer.parseInt(timer.getText().toString().trim()));
        progressBar.setMax(defaultTime);
        progressBar.setProgress(defaultTime-currentTime);
        progressBar.getProgressDrawable().setColorFilter(Color.BLACK,android.graphics.PorterDuff.Mode.SRC_IN);
    }

    private void finishDialog() {
        AlertDialog.Builder finishDialog=new AlertDialog.Builder(this);
        finishDialog.setTitle("時間到了~");
        finishDialog.setMessage("恭喜你刷好牙了~");
        DialogInterface.OnClickListener confirmClick =new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        };
        finishDialog.setNeutralButton("確定",confirmClick);
        finishDialog.show();
    }


    private void clcokStart() {
        currentTime = (Integer.parseInt(timer.getText().toString().trim()));
        if(currentTime%60%5==0) {
            if (clockArray[currentTime % 60].getVisibility() == View.INVISIBLE) {
                clockArray[currentTime % 60].setVisibility(View.VISIBLE);
            } else if (clockArray[currentTime % 60].getVisibility() == View.VISIBLE) {
                clockArray[currentTime % 60].setVisibility(View.INVISIBLE);
            }
        }
        if(currentTime==defaultTime-60)
            countdown.setText("加油!已經過了1分鐘了~");
        else if(currentTime==defaultTime-120)
            countdown.setText("加油!已經過了2分鐘了~");
        else if(currentTime==defaultTime-180)
            countdown.setText("加油!已經過了3分鐘了~");
    }

    private void clockStop() {
        for(i=0;i<60;i=i+5)
        {
            clockArray[i].setVisibility(View.INVISIBLE);
        }
        countdown.setText("");
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.Home)
        {
            Intent intent=new Intent();
            intent.setClass(this,Home_Activity.class);
            startActivity(intent);
        }
        else if(id==R.id.Video)
        {
            Intent intent=new Intent();
            intent.setClass(this,Video_Activity.class);
            startActivity(intent);
        }
        else if(id==R.id.Information)
        {
            Intent intent=new Intent();
            intent.setClass(this,Information_Activity.class);
            startActivity(intent);
        }
        else if(id==R.id.Question)
        {
            Intent intent=new Intent();
            intent.setClass(this,Question_Activity.class);
            startActivity(intent);
        }
        else if(id==R.id.Setting)
        {
            Intent intent=new Intent();
            intent.setClass(this,Setting_Activity.class);
            startActivity(intent);
        }
        else if(id==R.id.Logout)
        {
            auth.signOut();
            Intent intent=new Intent();
            intent.setClass(this,MainActivity.class);
            startActivity(intent);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}