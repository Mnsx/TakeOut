package top.mnsx.take_out.service;

import top.mnsx.take_out.dto.SetMealDto;
import top.mnsx.take_out.entity.SetMealDish;

import java.util.List;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/10 12:17
 * @Description:
 */
public interface SetMealDishService {
    Integer getCountByDishId(Long id);

    void save(List<SetMealDish> setMealDishes);

    void deleteBySetMealId(Long id);
}
