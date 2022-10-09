package top.mnsx.take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mnsx.take_out.dao.SetMealDao;
import top.mnsx.take_out.entity.SetMeal;
import top.mnsx.take_out.service.SetMealService;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/9 15:48
 * @Description:
 */
@Service
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    private SetMealDao setMealDao;

    @Override
    public Integer getCountByCategoryId(Long id) {
        LambdaQueryWrapper<SetMeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetMeal::getCategoryId, id);
        return setMealDao.selectCount(wrapper);
    }
}
