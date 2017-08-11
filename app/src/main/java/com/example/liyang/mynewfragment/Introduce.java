package com.example.liyang.mynewfragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.AsyncListUtil;
import android.webkit.WebView;

import datashare.DataShare;
import entity.Goods;

public class Introduce extends AppCompatActivity {
    private WebView textintro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle= this.getIntent().getExtras();
        final Goods thegood=(Goods) bundle.getSerializable("TheGooods");
        String data= thegood.gettextintroduce();
        System.out.println(data);
        setContentView(R.layout.activity_introduce);
        textintro=(WebView) findViewById(R.id.webview);
        textintro.getSettings().setJavaScriptEnabled(true);
        textintro.loadData(data, "text/html; charset=UTF-8", null);
    }
}
