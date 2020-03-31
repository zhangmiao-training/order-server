package formssi.orderserver.bean;


import java.util.HashMap;

public class ResultBean {

    private int code = 0;
    private String error = "";
    private Object data;

    private static final int INTERNAL_SERVER_ERROR = 500;

    public ResultBean() {
        this.code = 0;
        this.error = "success";
        this.data = new HashMap<>();
    }

    public ResultBean(int code, String error) {
        this.code = code;
        this.error = error;
        this.data = new HashMap<>();
    }

    public ResultBean(Object data) {
        this.code = 0;
        this.error = "success";
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ResultBean error() {
        return error(INTERNAL_SERVER_ERROR, "internal server error");
    }

    public static ResultBean error(String msg) {
        return error(INTERNAL_SERVER_ERROR, msg);
    }

    public static ResultBean error(int code, String msg) {
        return new ResultBean(code, msg);
    }

    public static ResultBean success() {
        return new ResultBean();
    }

    public static ResultBean success(Object data) {
        return new ResultBean(data);
    }

    public boolean isSuccess() {
        if (this.code == 0) {
            return true;
        } else {
            return false;
        }
    }

}
