package top.mnsx.take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mnsx.take_out.dao.CategoryDao;
import top.mnsx.take_out.entity.Category;
import top.mnsx.take_out.service.CategoryService;
import top.mnsx.take_out.service.DishService;
import top.mnsx.take_out.service.SetMealService;
import top.mnsx.take_out.service.ex.CategoryBandingDishException;
import top.mnsx.take_out.service.ex.CategoryBandingSetMealException;
import top.mnsx.take_out.service.ex.CategoryHasExistException;

import java.util.List;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/9 13:58
 * @Description: 逻辑层分类
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private DishService dishService;
    @Autowired
    private SetMealService setMealService;

    @Override
    public void addOne(Category category) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getName, category.getName());
        Category temp = categoryDao.selectOne(wrapper);
        if (temp != null) {
            throw new CategoryHasExistException();
        }
        categoryDao.insert(category);
    }

    @Override
    public List<Category> findByPage() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Category::getSort);
        wrapper.eq(Category::getIsDeleted, 0);
        return categoryDao.selectList(wrapper);
    }

    @Override
    public void deleteById(Long id) {
        Category category = categoryDao.selectById(id);
        int dishCount = dishService.getCountByCategoryId(id);
        if (dishCount > 0) {
            throw new CategoryBandingDishException();
        }
        log.info(String.valueOf(dishCount));
        Integer setMealCount = setMealService.getCountByCategoryId(id);
        if (setMealCount > 0) {
            throw new CategoryBandingSetMealException();
        }
        category.setIsDeleted(1);
        categoryDao.updateById(category);
    }

    @Override
    public void modifyById(Category category) {
        categoryDao.updateById(category);
    }


    @Override
    public List<Category> list(Category category) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(category != null, Category::getType, category.getType());
        wrapper.eq(Category::getIsDeleted, 0);
        wrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        return categoryDao.selectList(wrapper);
    }

    @Override
    public Category findById(Long id) {
        return categoryDao.selectById(id);
    }
}
