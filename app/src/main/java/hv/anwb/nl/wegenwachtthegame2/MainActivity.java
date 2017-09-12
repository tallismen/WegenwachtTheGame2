package hv.anwb.nl.wegenwachtthegame2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ActionBarSupport, View.OnClickListener {

    private Context context;
    private String TAG = "MainActivity";
    private ArrayList<Button> buttons;
    private boolean wisselen = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToolbarUtil.setupActionBar(this, this, getString(R.string.app_name), false);
        context = this;
        setupButtons();
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
        Button links_boven = (Button) findViewById(R.id.links_boven);
        links_boven.setOnClickListener(this);
        buttons.add(links_boven);

        Button midden_boven = (Button) findViewById(R.id.midden_boven);
        midden_boven.setOnClickListener(this);
        buttons.add(midden_boven);

        Button rechts_boven = (Button) findViewById(R.id.rechts_boven);
        rechts_boven.setOnClickListener(this);
        buttons.add(rechts_boven);

        //--------------------Midelste rij--------------------------------

        Button links_midden = (Button) findViewById(R.id.links_midden);
        links_midden.setOnClickListener(this);
        buttons.add(links_midden);

        Button midden_midden = (Button) findViewById(R.id.midden_midden);
        midden_midden.setOnClickListener(this);
        buttons.add(midden_midden);

        Button rechts_midden = (Button) findViewById(R.id.rechts_midden);
        rechts_midden.setOnClickListener(this);
        buttons.add(rechts_midden);

        //-------------------Onderste rij---------------------------------
        Button links_onder = (Button) findViewById(R.id.links_onder);
        links_onder.setOnClickListener(this);
        buttons.add(links_onder);

        Button midden_onder = (Button) findViewById(R.id.midden_onder);
        midden_onder.setOnClickListener(this);
        buttons.add(midden_onder);

        Button rechts_onder = (Button) findViewById(R.id.rechts_onder);
        rechts_onder.setOnClickListener(this);
        buttons.add(rechts_onder);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        if(wisselen){
            button.setBackground(getDrawable(R.mipmap.ic_launcher));
            wisselen = false;
        }else {
            button.setBackground(getDrawable(R.mipmap.ic_auto));
            wisselen = true;
        }

//        switch(view.getId()) {
//            //Bovenste rij
//            case R.id.links_boven:
//                break;
//            case R.id.midden_boven:
//                break;
//            case R.id.rechts_boven:
//                break;
//            //Milderste rij
//            case R.id.links_midden:
//                break;
//            case R.id.midden_midden:
//                break;
//            case R.id.rechts_midden:
//                break;
//            //Onderste rij
//            case R.id.links_onder:
//                break;
//            case R.id.midden_onder:
//                break;
//            case R.id.rechts_onder:
//                break;
    }

    public void resetButtonPressed(View v){
        for (Button button : buttons){
            button.setBackground(getDrawable(R.color.anwbWit));
        }
    }
}
