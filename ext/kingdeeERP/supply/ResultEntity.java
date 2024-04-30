package ext.kingdeeERP.supply;

import java.util.HashMap;

public class ResultEntity extends HashMap<String, Object> {

    // 成功码
    public static final String SUCCESS_TAG = "success";
    // 状态码
    public static final String CODE_TAG = "code";
    // 返回内容
    public static final String MSG_TAG = "msg";
    // 返回内容
    public static final String AFFECTED_ROWS_TAG = "affectedRows";

    private static final int SUCCESS_CODE = 200;

    private static final int ERROR_CODE = 400;

    public ResultEntity() {
    }

    public ResultEntity(boolean success, int code, int affectedRows, String msg) {
        super.put(SUCCESS_TAG, success);
        super.put(CODE_TAG, code);
        super.put(AFFECTED_ROWS_TAG, affectedRows);
        super.put(MSG_TAG, msg);
    }

    public static ResultEntity success(int affectedRows, String msg) {
        return new ResultEntity(true, SUCCESS_CODE, affectedRows, msg);
    }

    public static ResultEntity error(int affectedRows, String msg) {
        return new ResultEntity(false, ERROR_CODE, affectedRows, msg);
    }

    @Override
    public ResultEntity put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
