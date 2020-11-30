package edu.mssucis385.clickergame;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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

    public void writeFile() {
         try {
             FileOutputStream outputStream = openFileOutput("playerpoints.txt", Context.MODE_PRIVATE);
             String [] playerData = pointHelper.toString().split(" ");
             for (String item : playerData){
                 outputStream.write(item.getBytes());
             }
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

             FileInputStream inputStream = openFileInput("playerpoints.txt");
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
                 pointHelper.setTimerCanRun(Boolean.parseBoolean(playerData[6]));

                 inputStream.close();


     }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Change the theme if preference is true
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean darkTheme = sharedPrefs.getBoolean(SettingsFragment.PREFERENCE_THEME, false);
        if (darkTheme) {
            setTheme(R.style.DarkTheme);
        }

        pointHelper = new Points();

        setContentView(R.layout.activity_main);
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


    }

    public void addPoints(View view) {

        Points.addPoints();
        if (Points.getTimerLength() > 0)
            Points.setTimerCanRun(true);
        Toast.makeText(this, "" + Points.getPoints(), Toast.LENGTH_SHORT).show();

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        writeFile();
    }

}