package edu.mssucis385.clickergame;

import android.os.CountDownTimer;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Random;

public class Points {
    private static double mPoints;
    private static double mPointMultiplier = 1;
    private static int mBasePoint = 1;
    private static double mDoubleChanceStack;
    private static long mMaxFingers = 1;
    private static long mTimerLength;
    private static long mTimerTick = 1000;
    private static boolean timerCanRun = false;

    public static void removePoints(int cost){
        mPoints -= cost;
    }

    public static double getPoints()
    {
        return mPoints;
    }

    public static void setPoints(double points)
    {
        mPoints = points;
    }
    public static void setPointMultiplier(double multiplier)
    {
        mPointMultiplier = multiplier;
    }
    public static double getPointMultiplier()
    {
        return mPointMultiplier;
    }
    public static void setBasePoint(int point)
    {
        mBasePoint = point;
    }
    public static int getBasePoint()
    {
        return mBasePoint;
    }

    public static double getDoubleChanceStack() {
        return mDoubleChanceStack;
    }

    public static void setDoubleChanceStack(double mDoubleChance) {
        mDoubleChanceStack = mDoubleChance;
    }
    public static void addDoubleChanceStack(double mDoubleChance){
        mDoubleChanceStack += mDoubleChance;
    }

    public static long getMaxFingers() {
        return mMaxFingers;
    }

    public static void setMaxFingers(long fingers) {
        mMaxFingers = fingers;
    }

    public static long getTimerLength() {
        return mTimerLength;
    }

    public static void setTimerLength(long timerLength) {
      mTimerLength = timerLength;
    }

    public static long getTimerTick() {
        return mTimerTick;
    }

    public static void setTimerTick(long timerTick) {
       mTimerTick = timerTick;
    }

    public static boolean canTimerRun() {
        return timerCanRun;
    }

    public static void setTimerCanRun(boolean timerCanRun) {
        Points.timerCanRun = timerCanRun;
    }

    public static void addPoints()
    {

        if(Points.getDoubleChanceStack() > 0)
            if(doublePoints())
                Points.setPoints(Points.getPoints()+Points.getBasePoint() * Points.getPointMultiplier() * 2);
            else
                Points.setPoints(Points.getPoints()+Points.getBasePoint() * Points.getPointMultiplier());
        else
            Points.setPoints(Points.getPoints()+Points.getBasePoint() * Points.getPointMultiplier());
    }
    //calculates if points get doubled
    private static boolean doublePoints(){
        double weight = 1-1/(0.15 * Points.getDoubleChanceStack() +1);
        Random numberGenerator = new Random();
        double randomNumber=Math.random();
        if(randomNumber > weight){
            return  false;
        }
        else  return  true;

    }

    @NonNull
    public String toString() {
        return mPoints + "," + mPointMultiplier + "," + mBasePoint + "," +
                mDoubleChanceStack + "," + mMaxFingers + "," + mTimerLength + "," + mTimerTick + "," + timerCanRun;
    }
}
