package top.mnsx.take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mnsx.take_out.dao.SetMealDao;
import top.mnsx.take_out.dao.SetMealDishDao;
import top.mnsx.take_out.dto.SetMealDto;
import top.mnsx.take_out.entity.SetMealDish;
import top.mnsx.take_out.service.SetMealDishService;
import top.mnsx.take_out.service.SetMealService;

import java.util.List;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/10 12:18
 * @Description:
 */
@Slf4j
@Service
public class SetMealDishServiceImpl implements SetMealDishService {
    @Autowired
    private SetMealDishDao setMealDishDao;

    @Override
    public Integer getCountByDishId(Long id) {
        LambdaQueryWrapper<SetMealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetMealDish::getDishId, id);
        return setMealDishDao.selectCount(wrapper);
    }

    @Override
    public void save(List<SetMealDish> setMealDishes) {
        for (SetMealDish setMealDish : setMealDishes) {
            setMealDishDao.insert(setMealDish);
        }
    }

    @Override
    public void deleteBySetMealId(Long id) {
        LambdaQueryWrapper<SetMealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetMealDish::getSetMealId, id);
        List<SetMealDish> setMealDishes = setMealDishDao.selectList(wrapper);
        for (SetMealDish setMealDish : setMealDishes) {
            setMealDish.setIsDeleted(1);
            setMealDishDao.updateById(setMealDish);
        }
    }
}
