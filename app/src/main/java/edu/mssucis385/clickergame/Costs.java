package edu.mssucis385.clickergame;

import androidx.annotation.NonNull;

public class Costs {
    private static int mPointMultiplierCost = 15;
    private static int mBasePointCost = 15;
    private static int mDoubleChanceStackCost = 15;
    private static int mFingerCost = 15;
    private static int mTimerLengthCost = 15;
    private static int mTimerTickCost = 15;

    public static int getPointMultiplierCost() {
        return mPointMultiplierCost;
    }

    public static void setPointMultiplierCost(int mPointMultiplierCost) {
        Costs.mPointMultiplierCost = mPointMultiplierCost;
    }

    public static int getBasePointCost() {
        return mBasePointCost;
    }

    public static void setBasePointCost(int mBasePointCost) {
        Costs.mBasePointCost = mBasePointCost;
    }

    public static int getDoubleChanceStackCost() {
        return mDoubleChanceStackCost;
    }

    public static void setDoubleChanceStackCost(int mDoubleChanceStackCost) {
        Costs.mDoubleChanceStackCost = mDoubleChanceStackCost;
    }

    public static int getFingerCost() {
        return mFingerCost;
    }

    public static void setFingerCost(int mTickCost) {
        Costs.mFingerCost = mTickCost;
    }

    public static int getTimerLengthCost() {
        return mTimerLengthCost;
    }

    public static void setTimerLengthCost(int mTimerLengthCost) {
        Costs.mTimerLengthCost = mTimerLengthCost;
    }

    public static int getTimerTickCost() {
        return mTimerTickCost;
    }

    public static void setTimerTickCost(int mTimerTickCost) {
        Costs.mTimerTickCost = mTimerTickCost;
    }


    @NonNull
    @Override
    public String toString() {
        return mPointMultiplierCost + "," + mBasePointCost + "," + mDoubleChanceStackCost + "," + mFingerCost + "," + mTimerLengthCost + "," + mTimerTickCost;
}
}
