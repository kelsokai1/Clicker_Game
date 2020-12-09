package edu.mssucis385.clickergame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class UpgradeActivity extends AppCompatActivity {
    Menu menu;
    TextView mBasePointText;
    TextView mPointMultiplierText;
    TextView mFingerCostText;
    TextView mDoubleCostText;
    TextView mTimerCostText;
    TextView mTimerEfficiencyCostText;

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
        mBasePointText = (TextView) findViewById(R.id.baseCost);
        mPointMultiplierText = (TextView) findViewById(R.id.multiplierCost);
        mFingerCostText = (TextView) findViewById(R.id.fingerCost);
        mDoubleCostText = (TextView) findViewById(R.id.doubleCost);
        mTimerCostText = (TextView) findViewById(R.id.timerCost);
        mTimerEfficiencyCostText = (TextView) findViewById(R.id.efficiencyCost);

        mBasePointText.setText(mBasePointText.getText().toString() + Costs.getBasePointCost());
        mPointMultiplierText.setText(mPointMultiplierText.getText().toString() + Costs.getPointMultiplierCost());
        mFingerCostText.setText(mFingerCostText.getText().toString() + Costs.getFingerCost());
        mDoubleCostText.setText(mDoubleCostText.getText().toString() + Costs.getDoubleChanceStackCost());
        mTimerCostText.setText(mTimerCostText.getText().toString() + Costs.getTimerLengthCost());
        mTimerEfficiencyCostText.setText(mTimerEfficiencyCostText.getText().toString() + Costs.getTimerTickCost());


    }

    public void basePointUpgrade(View view) {
        if (Points.getPoints() >= Costs.getBasePointCost()) {
            if (Points.getBasePoint() != 1)
                Points.setBasePoint(Points.getBasePoint() * 2);
            else
                Points.setBasePoint(2);
            Points.removePoints(Costs.getBasePointCost());
            Costs.setBasePointCost(Costs.getPointMultiplierCost() * 2);
        } else
            Toast.makeText(this, "Not enough points", Toast.LENGTH_SHORT).show();
        mBasePointText.setText(getText(R.string.cost).toString() + Costs.getBasePointCost());

        updateMenuTitle();
    }

    public void pointMultiplierUpgrade(View view) {
        if (Points.getPoints() >= Costs.getPointMultiplierCost()) {

            Points.setBasePoint(Points.getBasePoint() + 1);
            Points.removePoints(Costs.getPointMultiplierCost());
            Costs.setPointMultiplierCost(Costs.getPointMultiplierCost() * 2);

        } else
            Toast.makeText(this, "Not enough points", Toast.LENGTH_SHORT).show();

        mPointMultiplierText.setText(getText(R.string.cost).toString() + Costs.getPointMultiplierCost());

        updateMenuTitle();
    }

    public void fingerUpgrade(View view) {
        if (Points.getPoints() >= Costs.getFingerCost()) {
            if (Points.getMaxFingers() > 10) {
                Toast.makeText(this, "Max Upgrade", Toast.LENGTH_SHORT).show();
            } else {
                Points.setMaxFingers(Points.getMaxFingers() + 1);
                Points.removePoints(Costs.getFingerCost());
                Costs.setFingerCost(Costs.getFingerCost() * 2);
            }
        } else
            Toast.makeText(this, "Not enough points", Toast.LENGTH_SHORT).show();
        mFingerCostText.setText(getText(R.string.cost).toString() + Costs.getFingerCost());

        updateMenuTitle();

    }

    public void pointDoubleUpgrade(View view) {
        if (Points.getPoints() >= Costs.getDoubleChanceStackCost()) {
            Points.addDoubleChanceStack(1);
            Points.removePoints(Costs.getDoubleChanceStackCost());
            Costs.setDoubleChanceStackCost(Costs.getDoubleChanceStackCost() * 2);

        } else
            Toast.makeText(this, "Not enough points", Toast.LENGTH_SHORT).show();
        mDoubleCostText.setText(getText(R.string.cost).toString() + Costs.getDoubleChanceStackCost());

        updateMenuTitle();
    }

    public void timerUpgrade(View view) {

        if (Points.getPoints() >= Costs.getTimerLengthCost()) {
            Points.setTimerLength(Points.getTimerTick() + 10000);
            Points.setTimerCanRun(true);
            Points.removePoints(Costs.getTimerLengthCost());
            Costs.setTimerLengthCost(Costs.getTimerTickCost() * 2);
        } else
            Toast.makeText(this, "Not enough points", Toast.LENGTH_SHORT).show();
        mTimerCostText.setText(getText(R.string.cost).toString() + Costs.getTimerLengthCost());
        updateMenuTitle();
    }

    public void timerEfficiencyUpgrade(View view) {

        if (Points.getPoints() >= Costs.getTimerTickCost()) {
            if (Points.getTimerTick() > 50) {
                Points.setTimerTick(Points.getTimerTick() - 50);
                Points.removePoints(Costs.getTimerTickCost());
                Costs.setTimerTickCost(Costs.getTimerTickCost() * 2);
            } else
                Toast.makeText(this, "Efficiency Full Upgraded", Toast.LENGTH_SHORT).show();

        } else
            Toast.makeText(this, "Not enough points", Toast.LENGTH_SHORT).show();

        mTimerEfficiencyCostText.setText(getText(R.string.cost).toString() + Costs.getTimerTickCost());
        updateMenuTitle();
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

    public void updateMenuTitle() {
        MenuItem mPointMenuItem = menu.findItem(R.id.points);
        mPointMenuItem.setTitle(getText(R.string.points).toString() + (int) Points.getPoints());
    }
}