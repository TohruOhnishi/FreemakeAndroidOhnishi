/*
 * Copyright 2017 freemake.
 */
package freemake.co.jp.androidlearning.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import freemake.co.jp.androidlearning.R;

/**
 * メインアクティビティ.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * ログ出力用タグ.
     */
    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
