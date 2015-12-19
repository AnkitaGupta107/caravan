package com.peppertap.caravan.activities;

import android.os.Bundle;

import com.peppertap.caravan.R;

public abstract class FaqActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_main);
    }

}
