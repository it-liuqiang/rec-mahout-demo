package com.example.rec.model;
import java.io.Serializable;


/**
 * 通用结果类
 *
 * @author zhangzt
 * @param <T>
 */
public class Result<T> implements Serializable {

	private static final long serialVersionUID = -6478327438363182007L;
	private boolean success = true;
	private String code;
	private String message = "";
	private T data;

	private String msgDesc = "";

	private Result() {
	}

	private Result(boolean success, String code, String msg, T data) {
		this.success = success;
		this.code = code;
		this.message = msg;
		this.data = data;
	}
	private Result(boolean success, String code, String msg, T data, String msgDesc) {
		this.success = success;
		this.code = code;
		this.message = msg;
		this.data = data;
		this.msgDesc = msgDesc;
	}
	private Result(String code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}





	public static <T> Result<T> success() {
		return new Result<T>("200", "success", null);
	}

	public static <T> Result<T> success(String message) {
		return new Result<T>(true, "200", message, null);
	}

	public static <T> Result<T> success(T data) {
		return new Result<T>(true, "200", "success", data);
	}

	public static <T> Result<T> success(String code, String msg) {
		return new Result<T>(true, code, msg, null);
	}

	public static <T> Result<T>  fail(String code, String errorMsg) {
		return new Result(false, code, errorMsg, null);
	}

	public static <T> Result<T>  fail(String code, String errorMsg, T data) {
		return new Result<T>(false, code, errorMsg, data);
	}
	public static <T> Result<T>  fail(String code, String errorMsg, T data,String msgDesc) {
		return new Result<T>(false, code, errorMsg, data,msgDesc);
	}

	public static <T> Result<T> fail(T data) {
		return new Result<T>(false, "500", "服务器异常", data);
	}

	public static <T> Result<T>  fail(String errorMsg) {
		return fail("500", errorMsg);
	}

	public static <T> Result<T>  data(T data) {
		return new Result<T>(true,"200", "", data);
	}

	public static <T> Result<T>  data(String code, String msg, T data) {
		return new Result(true, "200", msg, data);
	}

	public String getCode() {
		return this.code;
	}

	public String getMsg() {
		return this.message;
	}

	public T getData() {
		return this.data;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return this.success;
	}

	public String getMsgDesc() {
		return msgDesc;
	}

	public void setMsgDesc(String msgDesc) {
		this.msgDesc = msgDesc;
	}
}
