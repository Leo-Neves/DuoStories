package com.duolingo.duostories.view;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.duolingo.duostories.MyApplication;
import com.duolingo.duostories.R;
import com.duolingo.duostories.presenter.interfaces.OnBackPressed;
import com.duolingo.duostories.view.PesquisarAmigoFragment;


/**
 * Created by leo on 07/12/17.
 */

public class InicioActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private FrameLayout frameLayout, frameDrawer;

    private Fragment fragment, mainFragment;
    private OnBackPressed onBackPressed;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.setActivity(this);
        criarComponentes();
        configurarDrawer();
        configurarToolbar();
        abrirTelaInicial();
    }

    private void criarComponentes() {
        setContentView(R.layout.activity_main);
        drawer = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        frameLayout = findViewById(R.id.frameMain);
        frameDrawer = findViewById(R.id.frameDrawer);
    }

    private void configurarDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.setDrawerListener(toggle);
        drawer.setScrimColor(Color.BLACK);
        toggle.syncState();
    }

    private void configurarToolbar() {
        toolbar.setTitle(R.string.app_name);
    }

    private void abrirTelaInicial() {
        openMainFragment(new PesquisarAmigoFragment());
    }

    public Toolbar getToolbar(){
        return toolbar;
    }

    public void openMainFragment(Fragment fragment){
        if (fragment==null || (this.mainFragment!=null &&  this.mainFragment.equals(fragment) && this.fragment==null))
            return;
        this.mainFragment = fragment;
        this.fragment = null;
        setDrawerEnabled(true);
        getSupportFragmentManager().beginTransaction().replace(frameLayout.getId(), fragment).commitAllowingStateLoss();
    }

    public void setDrawerEnabled(boolean enabled){
        drawer.setDrawerLockMode(enabled?DrawerLayout.LOCK_MODE_UNLOCKED:DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        if (!enabled)
            drawer.closeDrawers();
    }

    public void openFragment(Fragment fragment) {
        if (this.fragment!=null && this.fragment.equals(fragment))
            return;
        this.fragment = fragment;
        setDrawerEnabled(false);
        getSupportFragmentManager().beginTransaction().replace(frameLayout.getId(), fragment).commitAllowingStateLoss();
    }

    public void setOnBackPressed(OnBackPressed onBackPressed){
        this.onBackPressed = onBackPressed;
    }

    @Override
    public void onBackPressed() {
        if (drawer!=null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (onBackPressed!=null){
                onBackPressed.onBackPressed();
            }else{
                if (frameLayout!=null && fragment != null) {
                    if (mainFragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(frameLayout.getId(), mainFragment).commitAllowingStateLoss();
                    } else {
                        getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
                        frameLayout.setVisibility(View.GONE);
                    }
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    toolbar.setVisibility(View.VISIBLE);
                    fragment = null;
                } else
                    super.onBackPressed();
            }
        }
    }

}
