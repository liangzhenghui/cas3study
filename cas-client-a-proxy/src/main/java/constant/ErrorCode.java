package constant;

/**
 * 错误码常量表
 * Created by yiyonglin on 2016/6/14.
 */
public final class ErrorCode {
	 /**
     * ticket参数为空
     */
    public final static String USER_NOT_ACTIVATED = "1000";
	 /**
     * 非法的参数
     */
    public final static String PARAM_INVALID = "1001";
	 /**
     * ticket参数为空
     */
    public final static String TICKET_NULL = "1002";
    /**
     * 参数为空
     */
    public final static String ARGS_NULL = "1003";
   
    /**
     * 请求非法
     */
    public final static String REQUEST_ILLEGAL = "1004";
    /**
     * 访问码过期
     */
    public final static String TOKEN_TIMEOUT = "1005";

    /**
     * 调用服务端出错
     */
    public final static String CALL_SERVICE_ERROR = "1006";
    
    /**
     * 调用webservice服务出错
     */
    public final static String CALL_WEBSERVICE_ERROR = "1007";

    /**
     * 坐标转换出错
     */
    public final static String LOCATION_TRANSLATE = "1008";
  
}
