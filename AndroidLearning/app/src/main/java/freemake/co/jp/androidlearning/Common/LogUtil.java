/*
 * Copyright 2017 freemake.
 */

package freemake.co.jp.androidlearning.Common;

import android.util.Log;

import static freemake.co.jp.androidlearning.Constance.AppConst.BEGINS_BRACKETS;
import static freemake.co.jp.androidlearning.Constance.AppConst.COLON;
import static freemake.co.jp.androidlearning.Constance.AppConst.END_BRACKETS;
import static freemake.co.jp.androidlearning.Constance.AppConst.PERIOD;
import static freemake.co.jp.androidlearning.Constance.AppConst.SHARP;

/**
 * ログクラス.
 */
public class LogUtil {

    /**
     * ログ出力用タグ.
     */
    private static final String TAG = LogUtil.class.getSimpleName();

    // TODO:アプリパラメータクラス等に移行する
    /**
     * デバッグ時「true」に設定.
     */
    private static final boolean isDebug = true;

    /**
     * メソッド開始ログ用メッセージ.
     */
    private static final String LOG_MESSAGE_METHOD_START = "start";

    /**
     * メソッド終了ログ用メッセージ.
     */
    private static final String LOG_MESSAGE_METHOD_END = "end";

    /**
     * ログメッセージなし.
     */
    private static final String LOG_MESSAGE_NULL = "(null)";

    /**
     * デバックログ出力.
     *
     * @param message ログメッセージ
     */
    public static void d(String message) {
        if (isDebug) {
            Log.d(TAG, getMetaInfo() + getLogMessage(message));
        }
    }

    /**
     * インフォログ出力.
     *
     * @param message ログメッセージ
     */
    public static void i(String message) {
        if (isDebug) {
            Log.i(TAG, getMetaInfo() + getLogMessage(message));
        }
    }

    /**
     * エラーログ出力.
     *
     * @param tag     ログ出力タグ
     * @param message ログメッセージ
     */
    public static void e(String tag, String message) {
        if (isDebug) {
            Log.e(TAG, getMetaInfo() + getLogMessage(message));
        }
    }

    /**
     * メソッド開始ログ出力.
     */
    public static void startMethod() {
        if (isDebug) {
            Log.e(TAG, getMetaInfo() + getLogMessage(LOG_MESSAGE_METHOD_START));
        }
    }

    /**
     * メソッド終了ログ出力.
     */
    public static void endMethod() {
        if (isDebug) {
            Log.e(TAG, getMetaInfo() + getLogMessage(LOG_MESSAGE_METHOD_END));
        }
    }

    /**
     * ログメッセージ取得.
     *
     * @param message ログメッセージ
     * @return "(null)"
     */
    private static String getLogMessage(String message) {
        if (message == null) {
            return LOG_MESSAGE_NULL;
        }
        return message;
    }

    /**
     * ログ呼び出し元のメタ情報を取得する.
     *
     * @return [className#methodName:line]
     */
    private static String getMetaInfo() {
        // スタックトレースから情報を取得 // 0: VM, 1: Thread, 2: LogUtil#getMetaInfo, 3: LogUtil#d など, 4: 呼び出し元
        final StackTraceElement element = Thread.currentThread().getStackTrace()[4];
        return getMetaInfo(element);
    }

    /**
     * スタックトレースからクラス名、メソッド名、行数を取得する.
     *
     * @return [className#methodName:line]
     */
    public static String getMetaInfo(StackTraceElement element) {
        // クラス名、メソッド名、行数を取得
        final String fullClassName = element.getClassName();
        final String simpleClassName = fullClassName.substring(fullClassName.lastIndexOf(PERIOD) + 1);
        final String methodName = element.getMethodName();
        final int lineNumber = element.getLineNumber();
        // メタ情報
        StringBuilder sb = new StringBuilder();
        sb.append(BEGINS_BRACKETS)
                .append(simpleClassName)
                .append(SHARP)
                .append(methodName)
                .append(COLON)
                .append(lineNumber)
                .append(END_BRACKETS);

        final String metaInfo = sb.toString();
        return metaInfo;
    }

}
