package top.mnsx.take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mnsx.take_out.dao.DishDao;
import top.mnsx.take_out.dao.DishFlavorDao;
import top.mnsx.take_out.entity.DishFlavor;
import top.mnsx.take_out.service.DishFlavorService;

import java.util.List;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/9 21:38
 * @Description:
 */
@Service
public class DishFlavorServiceImpl implements DishFlavorService {
    @Autowired
    private DishFlavorDao dishFlavorDao;

    @Override
    public void addOne(DishFlavor dishFlavor) {
        dishFlavorDao.insert(dishFlavor);
    }

    @Override
    public List<DishFlavor> getByDishId(Long id) {
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId, id);
        return dishFlavorDao.selectList(wrapper);
    }

    @Override
    public void deleteByDishId(Long id) {
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId, id);
        dishFlavorDao.delete(wrapper);
    }
}
