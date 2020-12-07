package edu.mssucis385.clickergame;

import androidx.appcompat.app.AppCompatActivity;


import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.DOMStringList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity{

    private TextView pointsItem;
    private Button pointButton;
    private Points pointHelper;
    private boolean mIsBound = false;
    private boolean mCanPlay;
    private boolean mDarkTheme;
    private SharedPreferences mSharedPrefs;
    private Menu menu;
    private double points;
    private Costs costHelper;


    public void writeFile() {
         try {
             FileOutputStream outputStream = openFileOutput("playerData.txt", Context.MODE_PRIVATE);
                 outputStream.write(pointHelper.toString().getBytes());
                 outputStream.write(",".getBytes());
                 outputStream.write(costHelper.toString().getBytes());
             outputStream.close();
         }
         catch (FileNotFoundException e){
             e.printStackTrace();
         }
         catch (java.io.IOException e){
             e.printStackTrace();
         }
     }
     public void readFile() throws IOException {

             FileInputStream inputStream = openFileInput("playerData.txt");
             int c;
             String data = "";
             while ((c = inputStream.read()) != -1)
                 data = data + Character.toString((char)c);
                 String [] playerData = data.split(",");

                 pointHelper.setPoints(Double.parseDouble(playerData[0]));
                 pointHelper.setPointMultiplier(Double.parseDouble(playerData[1]));
                 pointHelper.setBasePoint(Integer.parseInt(playerData[2]));
                 pointHelper.setDoubleChanceStack(Double.parseDouble(playerData[3]));
                 pointHelper.setMaxFingers(Integer.parseInt(playerData[4]));
                 pointHelper.setTimerLength(Long.parseLong(playerData[5]));
                 pointHelper.setTimerTick(Long.parseLong(playerData[6]));
                 pointHelper.setTimerCanRun(Boolean.parseBoolean(playerData[7]));
                 costHelper.setPointMultiplierCost(Integer.parseInt(playerData[8]));
                 costHelper.setBasePointCost(Integer.parseInt(playerData[9]));
                 costHelper.setDoubleChanceStackCost(Integer.parseInt(playerData[10]));
                 costHelper.setFingerCost(Integer.parseInt(playerData[11]));
                 costHelper.setTimerLengthCost(Integer.parseInt(playerData[12]));
                 costHelper.setTimerTickCost(Integer.parseInt(playerData[13]));
                 inputStream.close();


     }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Change the theme if preference is true
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mDarkTheme = mSharedPrefs.getBoolean(SettingsFragment.PREFERENCE_THEME, false);
        if (mDarkTheme) {
            setTheme(R.style.DarkTheme);
        }
        pointHelper = new Points();
        costHelper = new Costs();
        mCanPlay = mSharedPrefs.getBoolean(SettingsFragment.PREFERENCE_MUSIC, false);

        Intent intent = new Intent(this, PointIntentService.class);
        startService(intent);
        setContentView(R.layout.activity_main);
       // ImageView backgroundImage = (ImageView) findViewById(R.id.backgroundAnimation);
        //((AnimationDrawable) backgroundImage.getBackground()).start();
        pointsItem = findViewById(R.id.points);
        pointButton = findViewById(R.id.pointsButton);

        try {
            readFile();
        }
        catch (java.io.IOException e){
            e.printStackTrace();
        }
        pointButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int index = motionEvent.getActionIndex();
                int maskedAction = motionEvent.getActionMasked();
                switch (maskedAction){
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:

                        int fingers = motionEvent.getPointerCount();
                        if(fingers > Points.getMaxFingers())
                            for(int i = 0; i < Points.getMaxFingers();i++)
                                addPoints(view);
                            else if (Points.getMaxFingers() != 0)
                                for(int i = 0; i < motionEvent.getPointerCount(); i++)
                                    addPoints(view);
                                else addPoints(view);
                        return true;
                }
                return false;
            }
        });
        points = Points.getPoints();
        if(mCanPlay)
        startService(new Intent(MainActivity.this, MusicIntentService.class));
    }

    public void addPoints(View view) {

        Points.addPoints();
        if (Points.getTimerLength() > 0)
            Points.setTimerCanRun(true);

        updateMenuTitle();


    }

    public void openUpgradeMenu(View view) {
        Intent intent = new Intent(this,
                UpgradeActivity.class);
        startActivity(intent);

    }

    public void openCosmeticMenu(View view) {
    }

    public void openOptionMenu(View view) {
        Intent intent = new Intent(this,
                SettingsActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onStop() {
        super.onStop();
        writeFile();
        if (Points.canTimerRun()) {
            Intent intent = new Intent(this, PointIntentService.class);
            startService(intent);
        }
        stopService(new Intent(MainActivity.this, MusicIntentService.class));

    }

    @Override
    protected void onResume(){
        super.onResume();

        boolean darkTheme = mSharedPrefs.getBoolean(SettingsFragment.PREFERENCE_THEME, false);
        boolean canPlay = mSharedPrefs.getBoolean(SettingsFragment.PREFERENCE_MUSIC, false);
        if (darkTheme != mDarkTheme) {
            recreate();

            }
        if(points != Points.getPoints()){
            updateMenuTitle();
        }

            startService(new Intent(MainActivity.this, MusicIntentService.class));
        if(!canPlay){
            stopService(new Intent(MainActivity.this, MusicIntentService.class));
        }





    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mCanPlay)
        stopService(new Intent(MainActivity.this, MusicIntentService.class));
        writeFile();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar_menu, menu);
        updateMenuTitle();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.informationButton:
                Toast.makeText(this, "How to Play: Press the add points button to make your score go up. The upgrade tab contains various upgrades to increase point that are gained", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void updateMenuTitle(){
        MenuItem mPointMenuItem = menu.findItem(R.id.points);
        points = Points.getPoints();
        mPointMenuItem.setTitle(getText(R.string.points).toString() + (int) points);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}