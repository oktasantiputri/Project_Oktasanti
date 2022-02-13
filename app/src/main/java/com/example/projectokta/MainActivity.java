package com.example.projectokta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.projectokta.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        initView();
    }

    private void initView()
    {
        //custom toolbar
        setSupportActionBar(binding.toolbar);

        //default fragment dibuka pertama kali
        getSupportActionBar().setTitle("Home");
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment())
                .commit();
        binding.navView.setCheckedItem(R.id.nav_home);



        //membuka drawer
        toggle = new ActionBarDrawerToggle(this, binding.drawer, binding.toolbar,
                R.string.open, R.string.close);

        //drawer back button
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        //sinkronisasi drawer
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener
                (new NavigationView.OnNavigationItemSelectedListener()
                {
                    Fragment fragment = null;

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item)
                    {
                        switch (item.getItemId())
                        {
                            case R.id.nav_home:
                                fragment = new HomeFragment();
                                binding.drawer.closeDrawer(GravityCompat.START);
                                callFragment(fragment);
                                break;
                            case R.id.nav_peserta:
                                fragment = new PesertaFragment();
                                getSupportActionBar().setTitle("Peserta");
                                binding.drawer.closeDrawer(GravityCompat.START);
                                callFragment(fragment);
                                break;
                            case R.id.nav_Instruktur:
                                fragment = new InstrukturFragment();
                                getSupportActionBar().setTitle("Instruktur");
                                binding.drawer.closeDrawer(GravityCompat.START);
                                callFragment(fragment);
                                break;
                            case R.id.nav_materi:
                                fragment = new MateriFragment();
                                getSupportActionBar().setTitle("Materi");
                                binding.drawer.closeDrawer(GravityCompat.START);
                                callFragment(fragment);
                                break;
                            case R.id.nav_kelas:
                                fragment = new KelasFragment();
                                getSupportActionBar().setTitle("Kelas");
                                binding.drawer.closeDrawer(GravityCompat.START);
                                callFragment(fragment);
                                break;
                            case R.id.nav_dtkelas:
                                fragment = new KelasFragment();
                                getSupportActionBar().setTitle("Detail Kelas");
                                binding.drawer.closeDrawer(GravityCompat.START);
                                callFragment(fragment);
                                break;

                        }
                        return true;
                    }


                });
    }


    private void callFragment(Fragment fragment)
    {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }


}