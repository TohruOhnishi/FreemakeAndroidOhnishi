/*
 * Copyright 2017 freemake.
 */

package freemake.co.jp.androidlearning.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import freemake.co.jp.androidlearning.Common.LogUtil;
import freemake.co.jp.androidlearning.Model.QuantityInfoEntity;
import freemake.co.jp.androidlearning.R;
import freemake.co.jp.androidlearning.databinding.ListItemBinding;

import static android.view.View.OnClickListener;

/**
 * 数量情報リストアダプタ.
 */
public class QuantityInfoAdapter extends ArrayAdapter<QuantityInfoEntity> {

    /**
     * 偶数(ポジションの商の余りが0).
     */
    private static final int EVEN_NUMBER = 0;

    /**
     * 奇数(ポジションの商の余りが1).
     */
    private static final int ODD_NUMBER = 1;

    /**
     * {@link Context}
     */
    private Context mContext;

    /**
     * 追加情報リスト.
     */
    private ArrayList<QuantityInfoEntity> mQuantityInfoList;

    /**
     * コンストラクタ.
     *
     * @param context          {@link Context}
     * @param resource         リソースID
     * @param quantityInfoList 追加情報リスト
     */
    public QuantityInfoAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<QuantityInfoEntity> quantityInfoList) {
        super(context, resource);
        LogUtil.startMethod();
        mContext = context;
        mQuantityInfoList = quantityInfoList;
        LogUtil.endMethod();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCount() {
        return mQuantityInfoList.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuantityInfoEntity getItem(int position) {
        return mQuantityInfoList.get(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LogUtil.startMethod();

        ListItemBinding binding;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        binding = DataBindingUtil.inflate(inflater, R.layout.list_item, parent, false);
        convertView = binding.getRoot();
        convertView.setTag(binding);

        // 数量情報を各Viewにセット
        binding.setQuantityInfo(mQuantityInfoList.get(position));

        // 偶数行、機数行で背景色を変更
        int backgroundColor = 0;
        switch (position % 2) {
            case EVEN_NUMBER:
                backgroundColor = Color.BLUE;
                break;
            case ODD_NUMBER:
                backgroundColor = Color.WHITE;
                break;
        }

        // 選択状態で背景色を変更
        final boolean isSelected = getItem(position).isSelected();
        if (isSelected) {
            backgroundColor = Color.GREEN;
        }

        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_remove:
                        // 削除ボタン押下時の処理
                        onClickRemove(position);
                        return;
                    case R.id.check_list_item:
                        // チェックボタン押下時の処理
                        onClickCheckListItem(position, !isSelected);
                        return;
                    default:
                        return;
                }
            }
        };

        // 削除ボタン/チェックボックスにリスナをセット
        binding.buttonRemove.setOnClickListener(listener);
        binding.checkListItem.setOnClickListener(listener);

        // 背景色をセット
        convertView.setBackgroundColor(backgroundColor);

        LogUtil.endMethod();
        return convertView;
    }

    /**
     * リスト項目を追加する.
     *
     * @param addValue 追加する情報
     */
    public void add(QuantityInfoEntity addValue) {
        LogUtil.startMethod();

        mQuantityInfoList.add(addValue);
        // 即座にアダプタを更新
        notifyDataSetChanged();

        LogUtil.endMethod();
    }

    /**
     * リスト項目を前削除する.
     */
    public void clear() {
        LogUtil.startMethod();

        mQuantityInfoList.removeAll(mQuantityInfoList);
        // 即座にアダプタを更新
        notifyDataSetChanged();

        LogUtil.endMethod();
    }

    /**
     * 削除ボタン押下時の処理.
     *
     * @param position リストのポジション
     */
    private void onClickRemove(int position) {
        LogUtil.startMethod();
        mQuantityInfoList.remove(position);
        // 即座にアダプタを更新
        notifyDataSetChanged();
        LogUtil.endMethod();
    }

    /**
     * チェックボタン押下時の処理.
     *
     * @param position  リストのポジション
     * @param isChecked 　チェックボックスのチェック状態
     */
    private void onClickCheckListItem(int position, boolean isChecked) {
        LogUtil.startMethod();
        mQuantityInfoList.get(position).setSelected(isChecked);
        // 即座にアダプタを更新
        notifyDataSetChanged();
        LogUtil.endMethod();
    }

    /**
     * 数量の総数を取得する.
     *
     * @return 数量の総数
     */
    public int getAllQuantity() {
        LogUtil.startMethod();
        int allQuantity = 0;
        for (QuantityInfoEntity quantityInfo : mQuantityInfoList) {
            if (quantityInfo.isSelected()) {
                // 選択状態の場合、数量を加算
                allQuantity += quantityInfo.getQuantity();
            }
        }
        LogUtil.endMethod();
        return allQuantity;
    }
}
