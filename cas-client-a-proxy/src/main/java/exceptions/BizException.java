package exceptions;

import constant.ErrorCode;

/**
 * 业务异常基类，所有业务异常都必须继承于此异常
 *
 * @author liangzhenghui

 */
public class BizException extends RuntimeException {

	private static final long serialVersionUID = -5875371379845226068L;
	/**
	 * 调用webService服务出错
	 */
	public static final BizException CALL_WEBSERVICE_ERROR = new BizException(ErrorCode.CALL_WEBSERVICE_ERROR, "调用webservice服务出错");
	/**
	 * 用户未激活
	 */
	public static final BizException USER_NOT_ACTIVATED = new BizException(ErrorCode.USER_NOT_ACTIVATED, "用户未激活,请激活后再使用");
	
	/**
	 * 调用http服务出错
	 */
	public static final BizException CALL_SERVER_ERROR = new BizException(ErrorCode.CALL_SERVICE_ERROR, "调用接口服务出错");
	/**
	 * ticket参数为空
	 */
	public static final BizException TICKET_IS_EMPTY = new BizException(ErrorCode.TICKET_NULL, "ticket参数不能为空");

	/**
	 * Token 验证不通过
	 */
	public static final BizException TOKEN_IS_ILLICIT = new BizException(ErrorCode.TOKEN_TIMEOUT, "Token 验证非法");
	
	
	/**
	 * 非法的参数
	 */
	public static final BizException PARAM_INVALID = new BizException(ErrorCode.PARAM_INVALID, "非法的参数");

	/**
	 * 异常信息
	 */
	protected String msg;

	/**
	 * 具体异常码
	 */
	protected String code;

	public BizException(String code, String msgFormat, Object... args) {
		super(String.format(msgFormat, args));
		this.code = code;
		this.msg = String.format(msgFormat, args);
	}

	public BizException() {
		super();
	}

	public String getMsg() {
		return msg;
	}

	public String getCode() {
		return code;
	}

	/**
	 * 实例化异常
	 * 
	 * @param msgFormat
	 * @param args
	 * @return
	 */
	public BizException newInstance(String msgFormat, Object... args) {
		return new BizException(this.code, msgFormat, args);
	}

	public BizException(String message, Throwable cause) {
		super(message, cause);
	}

	public BizException(Throwable cause) {
		super(cause);
	}

	public BizException(String message) {
		super(message);
	}
}
