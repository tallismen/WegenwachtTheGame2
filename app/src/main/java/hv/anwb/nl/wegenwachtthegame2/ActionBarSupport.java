package hv.anwb.nl.wegenwachtthegame2;

import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

public interface ActionBarSupport {

    void setSupportActionBar(@Nullable Toolbar toolbar);

    ActionBar getSupportActionBar();
}
