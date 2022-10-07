package top.mnsx.take_out.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/7 22:53
 * @Description: 响应数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataVo<T> {
    private List<T> data;
    private Long total;
}
