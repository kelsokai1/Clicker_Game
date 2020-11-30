package edu.mssucis385.clickergame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

public class UpgradeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Change the theme if preference is true
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean darkTheme = sharedPrefs.getBoolean(SettingsFragment.PREFERENCE_THEME, false);
        if (darkTheme) {
            setTheme(R.style.DarkTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);
    }

    public void basePointUpgrade(View view) {
        if (Points.getPoints() >= Costs.getBasePointCost()) {
            if (Points.getBasePoint() != 1)
                Points.setBasePoint(Points.getBasePoint() * 2);
            else
                Points.setBasePoint(2);
            Points.removePoints(Costs.getBasePointCost());
        } else
            Toast.makeText(this, "Not enough points", Toast.LENGTH_SHORT).show();
    }

    public void pointMultiplierUpgrade(View view) {
        if (Points.getPoints() >= Costs.getPointMultiplierCost()) {

            Points.setBasePoint(Points.getBasePoint() + 1);
        }
    }

    public void fingerUpgrade(View view) {
        if (Points.getPoints() >= Costs.getTickCost()) {
            if (Points.getMaxFingers() > 10 ) {
                Toast.makeText(this, "Max Upgrade", Toast.LENGTH_SHORT).show();
            } else
                Points.setMaxFingers(Points.getMaxFingers() +1);
            Points.removePoints(Costs.getPointMultiplierCost());
        } else
            Toast.makeText(this, "Not enough points", Toast.LENGTH_SHORT).show();

    }

    public void pointDoubleUpgrade(View view) {
        if (Points.getPoints() >= Costs.getDoubleChanceStackCost()) {
            Points.addDoubleChanceStack(1);
            Points.removePoints(Costs.getDoubleChanceStackCost());
        } else
            Toast.makeText(this, "Not enough points", Toast.LENGTH_SHORT).show();
    }

    public void timerUpgrade(View view) {

        if (Points.getPoints() >= Costs.getTimerLengthCost()) {
            Points.setTimerLength(Points.getTimerTick() + 10000);
            Points.setTimerCanRun(true);
            Points.removePoints(Costs.getTimerLengthCost());
        } else
            Toast.makeText(this, "Not enough points", Toast.LENGTH_SHORT).show();
    }

    public void timerEfficiencyUpgrade(View view) {

        if (Points.getPoints() >= Costs.getTimerTickCost()) {
            if (Points.getTimerTick() > 50) {
                Points.setTimerTick(Points.getTimerTick() - 50);
                Points.removePoints(Costs.getTickCost());
            } else
                Toast.makeText(this, "Efficiency Full Upgraded", Toast.LENGTH_SHORT).show();

        } else
            Toast.makeText(this, "Not enough points", Toast.LENGTH_SHORT).show();

    }
}