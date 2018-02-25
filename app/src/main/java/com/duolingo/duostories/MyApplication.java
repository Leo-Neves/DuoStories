package com.duolingo.duostories;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.duolingo.duostories.view.InicioActivity;

import br.agr.terras.aurora.AURORA;
import br.agr.terras.aurora.log.Logger;


/**
 * Created by leo on 08/12/17.
 */

public class MyApplication extends Application {
    private static InicioActivity activity;

    @Override
    public void onCreate() {
        super.onCreate();
        configurarLogger();
        configurarFileProvider();
        AURORA.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

    private void configurarLogger() {
        Logger.init("DUOSTORIES");
    }

    private void configurarFileProvider() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    public static InicioActivity getActivity() {
        return activity;
    }

    public static String string(int id) {
        return activity.getString(id);
    }

    public static void setActivity(InicioActivity activity) {
        MyApplication.activity = activity;
    }
}
