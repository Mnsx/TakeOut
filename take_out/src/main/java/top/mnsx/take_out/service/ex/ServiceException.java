package top.mnsx.take_out.service.ex;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/6 21:55
 * @Description: 逻辑层——异常父类
 */
public class ServiceException extends RuntimeException {
    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
