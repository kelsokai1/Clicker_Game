package edu.mssucis385.clickergame;

public class Points {
    private static double mPoints;
    private static double mPointMultiplier = 1;
    private static double mTimeMultiplier = 1;
    private static int mBasePoint = 1;

    public static double getPoints()
    {
        return mPoints;
    }
    public static void AddPoints()
    {
        mPoints += mBasePoint;
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
    public static void setTimeMultiplier(double multiplier)
    {
        mTimeMultiplier = multiplier;
    }
    public static double getTimeMultiplier()
    {
        return mTimeMultiplier;
    }
    public static double geTimetMultiplier()
    {
        return mTimeMultiplier;
    }
    public static void setBasePoint(int point)
    {
        mBasePoint = point;
    }
    public static int getBasePoint()
    {
        return mBasePoint;
    }

}
