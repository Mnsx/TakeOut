package top.mnsx.take_out.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import top.mnsx.take_out.entity.ResultCode;
import top.mnsx.take_out.entity.ResultMap;
import top.mnsx.take_out.service.ex.EmployeeHasBanException;
import top.mnsx.take_out.service.ex.EmployeeNotExistException;
import top.mnsx.take_out.service.ex.PasswordNotSuccessException;
import top.mnsx.take_out.service.ex.ServiceException;
import top.mnsx.take_out.utils.JSONUtil;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/6 21:58
 * @Description: 控制层——父类
 */
@RestController
@Slf4j
public class BaseController {
    @ExceptionHandler(ServiceException.class)
    public String handlerException(Throwable e) {
        ResultCode resultCode = null;

        if (e instanceof EmployeeNotExistException) {
            resultCode = ResultCode.EMPLOYEE_NOT_EXIST;
        } else if (e instanceof PasswordNotSuccessException) {
            resultCode = ResultCode.PASSWORD_NOT_SUCESS;
        } else if (e instanceof EmployeeHasBanException) {
            resultCode = ResultCode.EMPLOYEE_HAS_BAN;
        } else {
            resultCode = ResultCode.INNER_ERROR;
        }

        log.info("error: {}", e.getCause());

        return JSONUtil.mapToJson(ResultMap.fail(resultCode));
    }
}
