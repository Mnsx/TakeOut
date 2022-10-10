package top.mnsx.take_out.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.mnsx.take_out.entity.Dish;
import top.mnsx.take_out.entity.DishFlavor;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/9 21:25
 * @Description:
 */
@Data
public class DishDto extends Dish {
    private List<DishFlavor> flavors = new ArrayList<>();
    private String categoryName;
}
