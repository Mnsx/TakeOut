package top.mnsx.take_out.entity;

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
    // 员工
    public static ResultCode EMPLOYEE_NOT_EXIST = new ResultCode(41001, "员工不存在");
    public static ResultCode PASSWORD_NOT_SUCESS = new ResultCode(41002, "密码不正确");
    public static ResultCode EMPLOYEE_HAS_BAN = new ResultCode(41003, "员工已经被禁止");
}
