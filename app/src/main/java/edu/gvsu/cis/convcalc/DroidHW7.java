package edu.gvsu.cis.convcalc;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

public class DroidHW7 extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
