package edu.mssucis385.clickergame;

public class Costs {
    private static int mPointMultiplierCost = 15;
    private static int mBasePointCost = 15;
    private static int mDoubleChanceStackCost = 15;
    private static int mTickCost = 15;
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

    public static int getTickCost() {
        return mTickCost;
    }

    public static void setTickCost(int mTickCost) {
        Costs.mTickCost = mTickCost;
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
}
