package controllers;

public class ThreadUtils {
    /**
     * このメソッドを呼び出したメソッド名、ファイル名、行数の情報を取得します。
     *
     * @return メソッド名、ファイル名、行数の情報文字列
     */
    public static String calledAt() {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        StringBuilder sb = new StringBuilder();
        sb.append(ste.getMethodName())        // メソッド名取得
            .append("(")
            .append(ste.getFileName())        // ファイル名取得
            .append(":")
            .append(ste.getLineNumber())    // 行番号取得
            .append(")");
        return sb.toString();
    }

    /**
     * このメソッドを呼び出したメソッドの呼び出し元のメソッド名、ファイル名、行数の情報を取得します。
     *
     * @return メソッド名、ファイル名、行数の情報文字列
     */
    public static String calledFrom() {
        StackTraceElement[] steArray = Thread.currentThread().getStackTrace();
        if (steArray.length <= 3) {
            return "";
        }
        StackTraceElement ste = steArray[3];
        StringBuilder sb = new StringBuilder();
        sb.append(ste.getMethodName())        // メソッド名取得
            .append("(")
            .append(ste.getFileName())        // ファイル名取得
            .append(":")
            .append(ste.getLineNumber())    // 行番号取得
            .append(")");
        return sb.toString();
    }
}
