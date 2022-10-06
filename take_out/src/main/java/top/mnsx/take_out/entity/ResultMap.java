package top.mnsx.take_out.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject: mnsx-utils
 * @User: Mnsx_x
 * @CreateTime: 2022/10/1 16:17
 * @Description: 响应类——利用建造者模式创建，链式编程构建响应类，通过读取ResultCode的内容返回异常
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultMap {
    // 响应码
    private Integer code;
    // 响应数据
    private Object data;
    // 响应消息
    private String msg;

    ResultMap(Builder builder) {
        this.code = builder.code;
        this.data = builder.data;
        this.msg = builder.message;
    }

    /**
     * 请求成功响应方法（带参数）
     * @param data 响应参数
     * @return 返回响应类
     */
    public static ResultMap ok(Object data) {
        return builder().code(ResultCode.SUCCESS.getCode())
                .message(ResultCode.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    /**
     * 请求成功响应方法
     * @return 返回响应类
     */
    public static ResultMap ok() {
        return builder().code(ResultCode.SUCCESS.getCode())
                .message(ResultCode.SUCCESS.getMessage())
                .build();
    }

    /**
     * 请求失败响应方法
     * @param resultCode 错误返回码类
     * @return 返回响应类
     */
    public static ResultMap fail(ResultCode resultCode) {
        return builder().code(resultCode.getCode())
                .message(resultCode.getMessage())
                .build();
    }

    /**
     * 请求失败响应方法（带参数）
     * @param resultCode 错误返回码类
     * @param data 响应参数
     * @return 返回响应类
     */
    public static ResultMap fail(ResultCode resultCode, Object data) {
        return builder().code(resultCode.getCode())
                .message(resultCode.getMessage())
                .data(data)
                .build();
    }

    private static Builder builder() {
        return new Builder();
    }

    // 构建者内部类
    private static class Builder {
        private Integer code;
        private Object data;
        private String message;

        public Builder code(Integer code) {
            this.code = code;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public ResultMap build() {
            return new ResultMap(this);
        }
    }
}