package top.mnsx.take_out.component;

import com.baomidou.mybatisplus.extension.api.R;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject: mnsx-utils
 * @User: Mnsx_x
 * @CreateTime: 2022/9/22 19:58
 * @Description: 响应码类——用来保存响应请求的响应码和响应消息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultCode {
    // 响应码
    private int code;
    // 响应消息
    private String message;

    // 成功响应
    public static ResultCode SUCCESS = new ResultCode(1, "success");
    // 错误响应
    // 服务器内部问题
    public static ResultCode INNER_ERROR = new ResultCode(50000, "服务器内部问题");
    // token
    public static ResultCode TOKEN_ERROR = new ResultCode(40001, "请登录后在使用系统");
    // 员工
    public static ResultCode EMPLOYEE_NOT_EXIST = new ResultCode(41001, "员工不存在");
    public static ResultCode PASSWORD_NOT_SUCCESS = new ResultCode(41002, "密码不正确");
    public static ResultCode EMPLOYEE_HAS_BAN = new ResultCode(41003, "员工已经被禁止");
    public static ResultCode EMPLOYEE_HAS_EXIST = new ResultCode(41004, "员工名称已经存在");
    // 分类
    public static ResultCode CATEGORY_HAS_EXIST = new ResultCode(42001, "分类已经存在");
    public static ResultCode CATEGORY_BANDING_DISH = new ResultCode(42002, "分类被菜品绑定，请处理菜品数据后，在进行操作");
    public static ResultCode CATEGORY_BANDING_SET_MEAL = new ResultCode(42003, "分类被套餐绑定，请处理套餐数据后，在进行操作");
    // 菜品
    public static ResultCode DISH_NAME_HAS_EXIST = new ResultCode(43001, "菜品已经被使用");
    public static ResultCode DISH_BANDING_SET_MEAL = new ResultCode(43002, "菜品被套餐绑定，请处理套餐数据后，在进行操作");
}
