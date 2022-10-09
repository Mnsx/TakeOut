package top.mnsx.take_out.service;

import top.mnsx.take_out.entity.DishFlavor;

import java.util.List;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/9 21:38
 * @Description:
 */
public interface DishFlavorService {
    void addOne(DishFlavor dishFlavor);
    List<DishFlavor> getByDishId(Long id);

    void deleteByDishId(Long id);
}
