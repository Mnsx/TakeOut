package top.mnsx.take_out.dto;

import lombok.Data;
import top.mnsx.take_out.entity.SetMeal;
import top.mnsx.take_out.entity.SetMealDish;

import java.util.List;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/10 14:09
 * @Description:
 */
@Data
public class SetMealDto extends SetMeal {
    private List<SetMealDish> setMealDishes;
    private String categoryName;
}
