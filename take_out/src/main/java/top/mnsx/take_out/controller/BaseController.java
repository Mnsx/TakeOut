package top.mnsx.take_out.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import top.mnsx.take_out.component.ResultCode;
import top.mnsx.take_out.component.ResultMap;
import top.mnsx.take_out.service.ex.*;
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
            resultCode = ResultCode.PASSWORD_NOT_SUCCESS;
        } else if (e instanceof EmployeeHasBanException) {
            resultCode = ResultCode.EMPLOYEE_HAS_BAN;
        } else if (e instanceof TokenErrorException) {
            resultCode = ResultCode.TOKEN_ERROR;
        } else if (e instanceof EmployeeHasExistException) {
            resultCode = ResultCode.EMPLOYEE_HAS_EXIST;
        } else if (e instanceof CategoryHasExistException) {
            resultCode = ResultCode.CATEGORY_HAS_EXIST;
        } else if (e instanceof CategoryBandingDishException) {
            resultCode = ResultCode.CATEGORY_BANDING_DISH;
        } else if (e instanceof CategoryBandingSetMealException) {
            resultCode = ResultCode.CATEGORY_BANDING_SET_MEAL;
        } else if (e instanceof DishNameHasExistException) {
            resultCode = ResultCode.DISH_NAME_HAS_EXIST;
        } else if (e instanceof DishBandingSetMealException) {
            resultCode = ResultCode.DISH_BANDING_SET_MEAL;
        } else {
            resultCode = ResultCode.INNER_ERROR;
        }

        return JSONUtil.mapToJson(ResultMap.fail(resultCode));
    }
}
