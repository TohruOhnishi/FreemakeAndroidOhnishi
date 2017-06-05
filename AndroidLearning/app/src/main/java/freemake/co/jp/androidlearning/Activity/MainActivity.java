/*
 * Copyright 2017 freemake.
 */

package freemake.co.jp.androidlearning.Activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import freemake.co.jp.androidlearning.Adapter.QuantityInfoAdapter;
import freemake.co.jp.androidlearning.Common.LogUtil;
import freemake.co.jp.androidlearning.Model.QuantityInfoEntity;
import freemake.co.jp.androidlearning.R;
import freemake.co.jp.androidlearning.databinding.ActivityMainBinding;

/**
 * メインアクティビティ.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 数量初期値.
     */
    private static final int INIT_QUANTITY = 0;

    /**
     * 最大数量.
     */
    private static final int MAX_QUANTITY = 9999;

    /**
     * 最小数量.
     */
    private static final int MIN__QUANTITY = 0;

    /**
     * プラスボタン押下時に加算される値.
     */
    private static final int ADD_VALUE = 1;

    /**
     * マイナスボタン押下時に加算される値.
     */
    private static final int SUB_VALUE = -1;

    /**
     * 現在時刻表示遅延時間.
     */
    private static final long DELAY_TIME = 0;

    /**
     * 現在時刻表示を定期的に繰り返す時間.
     */
    private static final long PERIOD_TIME = 1000L;

    /**
     * 現在時刻表示フォーマット.
     */
    private static final SimpleDateFormat CURRENT_DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    /**
     * {@link ActivityMainBinding}
     */
    ActivityMainBinding mBinding;

    /**
     * 数量保持用.
     */
    private int mQuantity;

    /**
     * {@link Handler}
     */
    private Handler mHandler;

    /**
     * {@link Timer}
     */
    private Timer mTimer;

    /**
     * 数量情報リストアダプタ保持用.
     */
    private QuantityInfoAdapter mAdapter;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtil.startMethod();

        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initView();

        LogUtil.endMethod();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onResume() {
        LogUtil.startMethod();

        super.onResume();
        // 現在時刻を表示
        showCurrentTime();

        LogUtil.endMethod();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onPause() {
        LogUtil.startMethod();

        super.onPause();

        // 定期実行をcancel
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        LogUtil.endMethod();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(View view) {
        LogUtil.startMethod();

        switch (view.getId()) {
            case R.id.button_plus:
                // プラスボタン押下時の処理
                onClickPlus();
                LogUtil.endMethod();
                return;
            case R.id.button_minus:
                // マイナスボタン押下時の処理
                onClickMinus();
                LogUtil.endMethod();
                return;
            case R.id.button_add:
                // 追加ボタン押下時の処理
                onClickAdd();
                LogUtil.endMethod();
                return;
            case R.id.button_clear:
                // クリアボタン押下時の処理
                onClickClear();
                LogUtil.endMethod();
                return;
            case R.id.button_total_quantity_selected:
                // 選択された合計数量ボタン押下時の処理
                onClickTotalQuantitySelected();
                LogUtil.endMethod();
                return;
            default:
                break;
        }

        LogUtil.endMethod();
    }

    /**
     * 画面初期表示.
     */
    private void initView() {
        LogUtil.startMethod();

        // 数量表示欄表示
        mQuantity = getQuantity();
        updateQuantityText(mQuantity);

        // プラスボタン設定
        mBinding.buttonPlus.setOnClickListener(this);
        // マイナスボタン設定
        mBinding.buttonMinus.setOnClickListener(this);
        // クリアボタン設定
        mBinding.buttonClear.setOnClickListener(this);
        // 追加ボタン設定
        mBinding.buttonAdd.setOnClickListener(this);
        // 選択された合計数量ボタン
        mBinding.buttonTotalQuantitySelected.setOnClickListener(this);

        // 追加情報リスト表示
        showQuantityInfoList();

        LogUtil.endMethod();
    }

    /**
     * プラスボタン押下時の処理.
     */
    private void onClickPlus() {
        LogUtil.startMethod();

        if (mQuantity >= MAX_QUANTITY) {
            showToast(getString(R.string.message_input_error));
            LogUtil.endMethod();
            return;
        }

        mQuantity += ADD_VALUE;
        updateQuantityText(mQuantity);
        LogUtil.endMethod();
    }

    /**
     * マイナスボタン押下時の処理.
     */
    private void onClickMinus() {
        LogUtil.startMethod();

        if (mQuantity <= MIN__QUANTITY) {
            showToast(getString(R.string.message_input_error));
            LogUtil.endMethod();
            return;
        }

        mQuantity += SUB_VALUE;
        updateQuantityText(mQuantity);
        LogUtil.endMethod();
    }

    /**
     * 追加ボタン押下時の処理.
     */
    private void onClickAdd() {
        LogUtil.startMethod();

        QuantityInfoEntity addValue = new QuantityInfoEntity();
        addValue.setAddDate(mBinding.textCurrentTime.getText().toString());
        addValue.setQuantity(mQuantity);
        addValue.setComment(mBinding.editComment.getText().toString());
        if (mAdapter != null) {
            mAdapter.add(addValue);
        }

        LogUtil.endMethod();

    }

    /**
     * クリアボタン押下時の処理.
     */
    private void onClickClear() {
        LogUtil.startMethod();
        mAdapter.clear();
        LogUtil.endMethod();
    }

    /**
     * 選択された合計数量ボタン押下時の処理.
     */
    private void onClickTotalQuantitySelected() {
        LogUtil.startMethod();

        // 選択された合計数量をメッセージダイアログで表示
        String dialogMessage = getString(R.string.message_total_quantity_selected, getAllQuantity());
        showMessageDialog(dialogMessage);

        LogUtil.endMethod();
    }

    /**
     * 数量表示欄の表示を更新する.
     *
     * @param quantity
     */
    private void updateQuantityText(int quantity) {
        LogUtil.startMethod();

        // 3桁毎にカンマを付与
        String setValue = String.format("%,d", quantity);
        mBinding.textQuantity.setText(getString(R.string.text_label_quantity, setValue));

        LogUtil.endMethod();
    }

    /**
     * トーストを表示する.
     *
     * @param message トーストメッセージ
     */
    private void showToast(String message) {
        LogUtil.startMethod();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        LogUtil.endMethod();
    }

    /**
     * メッセージダイアログを表示する.
     *
     * @param message ダイアログメッセージ
     */
    private void showMessageDialog(String message) {
        LogUtil.startMethod();
        // TODO:ダイアログを表示する処理未実装
        showToast(message);
        LogUtil.endMethod();
    }

    /**
     * 現在時刻を表示する.
     */
    private void showCurrentTime() {
        LogUtil.startMethod();

        mHandler = new Handler(getMainLooper());
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {

            /**
             * {@inheritDoc}
             */
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    public void run() {
                        LogUtil.startMethod();
                        Calendar calendar = Calendar.getInstance();
                        String nowDate = CURRENT_DATE_FORMAT.format(calendar.getTime());
                        mBinding.textCurrentTime.setText(nowDate);
                        LogUtil.endMethod();
                    }
                });
            }
        }, DELAY_TIME, PERIOD_TIME);
        LogUtil.endMethod();
    }

    /**
     * 数量を取得する.
     *
     * @return 数量
     */
    private int getQuantity() {
        //TODO:初期値を返すのみ、外部から取得するようにする?
        LogUtil.startMethod();
        LogUtil.endMethod();
        return INIT_QUANTITY;
    }

    /**
     * 追加情報リストを表示する.
     */
    private void showQuantityInfoList() {
        LogUtil.startMethod();

        ArrayList<QuantityInfoEntity> quantityInfoList = getQuantityInfo();
        mAdapter = new QuantityInfoAdapter(this, 0, quantityInfoList);
        mBinding.listQuantityInfo.setAdapter(mAdapter);

        LogUtil.endMethod();
    }

    /**
     * 数量情報を取得する.
     *
     * @return 数量情報
     */
    private ArrayList<QuantityInfoEntity> getQuantityInfo() {
        LogUtil.startMethod();
        ArrayList<QuantityInfoEntity> quantityInfoList = new ArrayList<>();
        LogUtil.endMethod();
        return quantityInfoList;
    }

    /**
     * 数量の総数を取得する.
     *
     * @return 数量の総数
     */
    private int getAllQuantity() {
        LogUtil.startMethod();
        LogUtil.endMethod();
        return mAdapter.getAllQuantity();
    }
}
