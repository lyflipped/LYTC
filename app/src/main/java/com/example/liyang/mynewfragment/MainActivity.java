package com.example.liyang.mynewfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.content.Intent;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import android.support.v4.app.FragmentTransaction;
public class MainActivity extends AppCompatActivity {

    //private TextView mTextMessage;
    private HomeFragment home;
    private ShoppingFragment shoppingFragment;
     private MyselfFragment myselfFragment;
    private SearchFragment searchFragment;
   // private  FragmentManager fragmentManager = getSupportFragmentManager();
    //private  FragmentTransaction beginTransaction = fragmentManager.beginTransaction();

    private void  Initfragment()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        if(home==null)
        { home = new HomeFragment();}
        beginTransaction.addToBackStack(null);
        beginTransaction.add(home,"home");
        if(shoppingFragment==null)
        {
            shoppingFragment=new ShoppingFragment();
        }
        beginTransaction.add(shoppingFragment,"shoppingcard");
        if(myselfFragment==null)
        {
            myselfFragment=new MyselfFragment();
        }
        beginTransaction.add(myselfFragment,"myself");
        // beginTransaction.show(home)

    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    //mTextMessage.setText(R.string.title_home);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
                        if(home==null)
                        { home = new HomeFragment();}
                             beginTransaction.addToBackStack(null);
                            beginTransaction.replace(R.id.mainfragment, home);
                            beginTransaction.commit();
                            return true;
                case R.id.navigation_dashboard:
                   // mTextMessage.setText(R.string.title_dashboard);
                    FragmentManager fragmentManager1=getSupportFragmentManager();
                    FragmentTransaction beginTransaction1=fragmentManager1.beginTransaction();
                    shoppingFragment = new ShoppingFragment();
                    beginTransaction1.addToBackStack(null);
                    beginTransaction1.replace(R.id.mainfragment,shoppingFragment);
                    beginTransaction1.addToBackStack(null);
                    beginTransaction1.commit();
                    return true;
                case R.id.navigation_notifications:
                   FragmentManager fragmentManager2=getSupportFragmentManager();
                    FragmentTransaction beginTransaction2=fragmentManager2.beginTransaction();
                    //if(myselfFragment==null) {
                         myselfFragment = new MyselfFragment();
                   // }
                    beginTransaction2.addToBackStack(null);
                    beginTransaction2.replace(R.id.mainfragment,myselfFragment);
                    beginTransaction2.commit();
                    return true;
                case R.id.navigation_search:
                    FragmentManager fragmentManager3=getSupportFragmentManager();
                    FragmentTransaction beginTransaction3=fragmentManager3.beginTransaction();
                    searchFragment=new SearchFragment();
                    beginTransaction3.addToBackStack(null);
                    beginTransaction3.replace(R.id.mainfragment,searchFragment);
                    beginTransaction3.commit();
                    return true;

            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化界面   默认显示主页第一个
       HomeFragment home=new HomeFragment();
        //MyselfFragment fragment=new MyselfFragment();
       FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction beginTransaction=fragmentManager.beginTransaction();
        beginTransaction.replace(R.id.mainfragment,home);
       beginTransaction.addToBackStack(null);
        beginTransaction.commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
