/*
 * Copyright 2017 freemake.
 */

package freemake.co.jp.androidlearning.Model;

/**
 * 追加情報エンティティクラス.
 */
public class QuantityInfoEntity {

    /**
     * 追加時刻保持用.
     */
    private String mAddDate;

    /**
     * 数量保持用.
     */
    private int mQuantity;

    /**
     * 数量保持用(文字列).
     */
    private String mQuantityString;

    /**
     * コメント保持用.
     */
    private String mComment;

    /**
     * 選択状態保持用.
     */
    private boolean mIsSelected;

    /**
     * コンストラクタ.
     */
    public QuantityInfoEntity() {
        mIsSelected = false;
    }

    /**
     * 追加時刻を取得する.
     *
     * @return 追加時刻
     */
    public String getAddDate() {
        return mAddDate;
    }

    /**
     * 追加時刻をセットする.
     *
     * @param addDate 追加時刻
     */
    public void setAddDate(String addDate) {
        mAddDate = addDate;
    }

    /**
     * 数量を取得する.
     *
     * @return 数量
     */
    public int getQuantity() {
        return mQuantity;
    }

    /**
     * 数量をセットする.
     *
     * @param quantity 数量
     */
    public void setQuantity(int quantity) {
        mQuantity = quantity;
        mQuantityString = String.format("%,d", quantity);
    }

    /**
     * 数量(文字列)を取得する.
     *
     * @return 数量文字列
     */
    public String getQuantityString() {
        return mQuantityString;
    }

    /**
     * コメントを取得する.
     *
     * @return コメント
     */
    public String getComment() {
        return mComment;
    }

    /**
     * コメントをセットする.
     *
     * @param comment コメント
     */
    public void setComment(String comment) {
        mComment = comment;
    }

    /**
     * 選択状態か.
     *
     * @return true = 選択状態, false = 非選択状態
     */
    public boolean isSelected() {
        return mIsSelected;
    }

    /**
     * 選択状態をセットする.
     *
     * @param isSelected 選択状態
     */
    public void setSelected(boolean isSelected) {
        mIsSelected = isSelected;
    }
}
