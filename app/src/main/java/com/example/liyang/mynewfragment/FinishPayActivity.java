package com.example.liyang.mynewfragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinishPayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_pay);
        initview();
    }
    private void initview()
    {
        TextView paymoney=(TextView) findViewById(R.id.paymoney_finish);
        Intent intent=getIntent();
        String money=intent.getStringExtra("money");
        paymoney.setText("￥"+money);
        TextView backtomain=(TextView) findViewById(R.id.backto_main);
        backtomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back=new Intent();
                back.setClass(FinishPayActivity.this,MainActivity.class);
                startActivity(back);
                finish();
            }
        });
    }

}
