package com.wzw.microboot.common;

public class JsonResult<T> {
    private static final String CODE_SUCCESS = "success";

    private static final String CODE_FAIL = "fail";
    private T data;
    private String code;
    private String msg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 若没有数据返回，默认状态码为 0，提示信息为“操作成功！”
     */
    public JsonResult() {
        this.code = "0";
        this.msg = "操作成功！";
    }

    /**
     * 若没有数据返回，可以人为指定状态码和提示信息
     * @param code
     * @param msg
     */
    public JsonResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    /**
     * 若没有数据返回，可以人为指定状态码和提示信息
     * @param code
     * @param data
     */
    public JsonResult(String code, T data) {
        this.code = code;
        this.data = data;
    }
    /**
     * 有数据返回时，状态码为 0，默认提示信息为“操作成功！”
     * @param data
     */
    public JsonResult(T data) {
        this.data = data;
        this.code = "0";
        this.msg = "操作成功！";
    }

    /**
     * 有数据返回，状态码为 0，人为指定提示信息
     * @param data
     * @param msg
     */
    public JsonResult(T data, String msg) {
        this.data = data;
        this.code = "0";
        this.msg = msg;
    }
    public static JsonResult success(String data){
        return new JsonResult(CODE_SUCCESS, data);
    }

    public static JsonResult fail(String msg){
        return new JsonResult(CODE_FAIL, msg);
    }

    public static JsonResult widthCode(String errorCode) {
        return new JsonResult(errorCode);
    }

}
