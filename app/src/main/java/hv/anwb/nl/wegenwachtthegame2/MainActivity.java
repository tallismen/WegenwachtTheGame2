package hv.anwb.nl.wegenwachtthegame2;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements ActionBarSupport, View.OnClickListener {

    private String TAG = "MainActivity";
    private ArrayList<Button> buttons;
    private boolean wisselen = true;
    private boolean erIsGewoonen = false;
    private Random random;
    private Handler handler;

    private TextView pechgevalWinText;
    private TextView wegenwachtWinText;
    private TextView gelijkspelText;

    private int pechGevalWin;
    private int wegenwachtWin;
    private int gelijkspelCount;

    private Button winImage;
    private Drawable turnDrawable;
    private Button turnImage;

    private Button links_boven;
    private Button midden_boven;
    private Button rechts_boven;

    private Button links_midden;
    private Button midden_midden;
    private Button rechts_midden;

    private Button links_onder;
    private Button midden_onder;
    private Button rechts_onder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToolbarUtil.setupActionBar(this, this, getString(R.string.app_name), false);
        setupButtons();
        turnDrawable = getDrawable(R.mipmap.ic_launcher);
        turnImage = (Button) findViewById(R.id.beurtView);
        turnImage.setBackground(turnDrawable);
        winImage = (Button) findViewById(R.id.gewonnenView);

        pechgevalWinText = (TextView) findViewById(R.id.pechgevalWinsText);
        wegenwachtWinText = (TextView) findViewById(R.id.wegenwachtWinsText);
        gelijkspelText = (TextView) findViewById(R.id.gelijkspelText);
        wegenwachtWin = 0;
        pechGevalWin = 0;
        gelijkspelCount = 0;
        random = new Random();
        handler = new Handler();
        resetScore();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause()");
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.preferences: {
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupButtons() {
        buttons = new ArrayList<>();
        //----------------------Bovenste rij------------------------------
        links_boven = (Button) findViewById(R.id.links_boven);
        links_boven.setOnClickListener(this);
        buttons.add(links_boven);

        midden_boven = (Button) findViewById(R.id.midden_boven);
        midden_boven.setOnClickListener(this);
        buttons.add(midden_boven);

        rechts_boven = (Button) findViewById(R.id.rechts_boven);
        rechts_boven.setOnClickListener(this);
        buttons.add(rechts_boven);

        //--------------------Midelste rij--------------------------------

        links_midden = (Button) findViewById(R.id.links_midden);
        links_midden.setOnClickListener(this);
        buttons.add(links_midden);

        midden_midden = (Button) findViewById(R.id.midden_midden);
        midden_midden.setOnClickListener(this);
        buttons.add(midden_midden);

        rechts_midden = (Button) findViewById(R.id.rechts_midden);
        rechts_midden.setOnClickListener(this);
        buttons.add(rechts_midden);

        //-------------------Onderste rij---------------------------------
        links_onder = (Button) findViewById(R.id.links_onder);
        links_onder.setOnClickListener(this);
        buttons.add(links_onder);

        midden_onder = (Button) findViewById(R.id.midden_onder);
        midden_onder.setOnClickListener(this);
        buttons.add(midden_onder);

        rechts_onder = (Button) findViewById(R.id.rechts_onder);
        rechts_onder.setOnClickListener(this);
        buttons.add(rechts_onder);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        if (!erIsGewoonen) {
            Log.i(TAG, "onClick()");
            Button button = (Button) view;
            buttons.remove(button);
            if (button.getText().equals("3")) {
                if (wisselen) {
                    wegenwachtZet(button);
                    wisselen = false;

                } else {
                    pechautoZet(button);
                    wisselen = true;
                }
            }
        }
        checkGame();
        if (!wisselen  && !erIsGewoonen) {
            computerZet();
            wisselen = true;
            checkGame();
        }
        updateScoreBoard();
    }

    private void checkGame(){
        if (checkForWin() && !erIsGewoonen) {
            erIsGewoonen = true;
            clearPlayArea(5000);
            if (wisselen) {
                Toast.makeText(this, "Pechgeval heeft gewonnen! ", Toast.LENGTH_LONG).show();
                pechGevalWin++;
                winImage.setBackground(getDrawable(R.mipmap.ic_pechauto));
            } else {
                if (!wisselen) {
                    computerZet();
                    wisselen = true;
                }
                Toast.makeText(this, "Wegenwacht heeft gewonnen! ", Toast.LENGTH_LONG).show();
                wegenwachtWin++;
                winImage.setBackground(getDrawable(R.mipmap.ic_launcher));
            }
        }
        if (checkGelijkSpel() && !erIsGewoonen) {
            clearPlayArea(5000);
            if (!wisselen) {
                computerZet();
                wisselen = true;
            }
            erIsGewoonen = true;
            Toast.makeText(this, "Gelijkspel!", Toast.LENGTH_LONG).show();
            gelijkspelCount++;
        }
    }

    private void computerZet() {
        onClick(getRandomButton());
    }

    private Button getRandomButton() {
        Log.i(TAG,"getRandomButton()");
        int number = random.nextInt(buttons.size());
        Log.i(TAG,"Nummer: "+number);
        Button button = buttons.get(number);
        Log.i(TAG,button.getText()+"");
        if (!button.getText().equals("3")) {
            getRandomButton();
        } else {
            return button;
        }
        return button;
    }

    private void wegenwachtZet(Button button) {
        button.setBackground(getDrawable(R.mipmap.ic_launcher));
        button.setText("1");
        turnDrawable = getDrawable(R.mipmap.ic_pechauto);
        turnImage.setBackground(turnDrawable);
    }

    private void pechautoZet(Button button) {
        button.setBackground(getDrawable(R.mipmap.ic_pechauto));
        button.setText("0");
        turnDrawable = getDrawable(R.mipmap.ic_launcher);
        turnImage.setBackground(turnDrawable);
    }

    public void resetButtonPressed(View v) {
        Log.i(TAG, "resetButtonPressed()");
        clearPlayArea(0);
        resetScore();
    }

    public void clearPlayAreaButtonPressed(View v) {
        Log.i(TAG, "clearPlayAreaButtonPressed()");
        clearPlayArea(0);
    }

    private boolean checkForWin() {
        Log.i(TAG, "checkForWin()");
        boolean win = false;
        ArrayList<Boolean> winRow = new ArrayList<>();
        winRow.add(checkTopRow());
        winRow.add(checkMiddleRow());
        winRow.add(checkBottomRow());
        winRow.add(checkLeftColum());
        winRow.add(checkMidleColum());
        winRow.add(checkRightColum());
        winRow.add(checkDiagonalLeft());
        winRow.add(checkDiagonalRight());
        for (Boolean winn : winRow) {
            if (winn) {
                Log.i(TAG, "Gewonnen: " + winn);
                win = winn;
            }
        }
        return win;
    }

    private boolean checkGelijkSpel() {
        boolean gelijkspel;
        String gelijkspelString = "";
        for (Button button : buttons) {
            gelijkspelString += gelijkspelString + button.getText();
        }
        if (gelijkspelString.contains("3")) {
            gelijkspel = false;
        } else {
            gelijkspel = true;
        }
        return gelijkspel;
    }

    private void clearPlayArea(int delay) {
        setupButtons();
        final Button button = (Button) findViewById(R.id.button4);
        final Button button1 = (Button) findViewById(R.id.button);
        button1.setBackground(getDrawable(R.drawable.button_gray));
        button1.setEnabled(false);
        button.setBackground(getDrawable(R.drawable.button_gray));
        button.setEnabled(false);
        handler.postDelayed(new Runnable() {
            public void run() {
                Log.i(TAG, "clearPlayArea()");
                for (Button button : buttons) {
                    button.setBackground(getDrawable(R.mipmap.ic_leeg));
                    button.setText("3");
                }
                erIsGewoonen = false;
                button.setBackground(getDrawable(R.drawable.button_orange));
                button1.setBackground(getDrawable(R.drawable.button_blue));
                button.setEnabled(true);
                button1.setEnabled(true);
            }
        }, delay);
    }

    private void resetScore() {
        Log.i(TAG, "resetScore()");
        wegenwachtWin = 0;
        pechGevalWin = 0;
        gelijkspelCount = 0;
        updateScoreBoard();
    }

    private void updateScoreBoard() {
        wegenwachtWinText.setText(Integer.toString(wegenwachtWin));
        pechgevalWinText.setText(Integer.toString(pechGevalWin));
        gelijkspelText.setText(Integer.toString(gelijkspelCount));
    }

    private boolean checkThreeButtons(Button button1, Button button2, Button button3) {
        String text = "0";
        String text2 = "1";
        boolean returnValue;
        if (text.equals(button1.getText()) && text.equals(button2.getText()) && text.equals(button3.getText())) {
            returnValue = true;
        } else {
            if (text2.equals(button1.getText()) && text2.equals(button2.getText()) && text2.equals(button3.getText())) {
                returnValue = true;
            } else {
                returnValue = false;
            }
        }
        return returnValue;
    }

    private boolean checkTopRow() {
        return checkThreeButtons(links_boven, midden_boven, rechts_boven);
    }

    private boolean checkMiddleRow() {
        return checkThreeButtons(links_midden, midden_midden, rechts_midden);
    }

    private boolean checkBottomRow() {
        return checkThreeButtons(links_onder, midden_onder, rechts_onder);
    }

    private boolean checkLeftColum() {
        return checkThreeButtons(links_boven, links_midden, links_onder);
    }

    private boolean checkMidleColum() {
        return checkThreeButtons(midden_boven, midden_midden, midden_onder);
    }

    private boolean checkRightColum() {
        return checkThreeButtons(rechts_boven, rechts_midden, rechts_onder);
    }

    private boolean checkDiagonalLeft() {
        return checkThreeButtons(links_boven, midden_midden, rechts_onder);
    }

    private boolean checkDiagonalRight() {
        return checkThreeButtons(rechts_boven, midden_midden, links_onder);
    }
}
