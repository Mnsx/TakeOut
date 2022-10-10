package top.mnsx.take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import top.mnsx.take_out.dao.SetMealDao;
import top.mnsx.take_out.dto.SetMealDto;
import top.mnsx.take_out.entity.Category;
import top.mnsx.take_out.entity.SetMeal;
import top.mnsx.take_out.entity.SetMealDish;
import top.mnsx.take_out.service.CategoryService;
import top.mnsx.take_out.service.SetMealDishService;
import top.mnsx.take_out.service.SetMealService;
import top.mnsx.take_out.utils.ImageUtil;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private SetMealDishService setMealDishService;
    @Autowired
    private CategoryService categoryService;

    @Override
    public Integer getCountByCategoryId(Long id) {
        LambdaQueryWrapper<SetMeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetMeal::getCategoryId, id);
        return setMealDao.selectCount(wrapper);
    }

    @Override
    public String addPicture(MultipartFile multipartFile) {
        String path = "D:\\WorkSpace\\TakeOut\\take_out\\picutrue";
        return ImageUtil.saveImg(multipartFile, path);
    }

    @Override
    public void getPicture(HttpServletResponse response, String fileName) {
        String path = "D:\\WorkSpace\\TakeOut\\take_out\\picutrue\\" + fileName;
        ImageUtil.downloadFile(response, path);
    }

    @Override
    @Transactional
    public void addOne(SetMealDto setMealDto) {
        setMealDao.insert(setMealDto);

        List<SetMealDish> setMealDishes = setMealDto.getSetMealDishes();
        List<SetMealDish> collect = setMealDishes.stream().map((item) -> {
            item.setSetMealId(setMealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setMealDishService.save(collect);
    }

    @Override
    public List<SetMealDto> page(int page, int pageSize, String name) {
        LambdaQueryWrapper<SetMeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetMeal::getIsDeleted, 0);
        wrapper.likeRight(name != null, SetMeal::getName, name);
        wrapper.orderByDesc(SetMeal::getUpdateTime);
        List<SetMeal> setMeals = setMealDao.selectList(wrapper);
        List<SetMealDto> setMealDtos = setMeals.stream().map((item) -> {
            Category category = categoryService.findById(item.getCategoryId());
            SetMealDto setMealDto = new SetMealDto();
            setMealDto.setCategoryName(category.getName());
            BeanUtils.copyProperties(item, setMealDto);
            return setMealDto;
        }).collect(Collectors.toList());
        return setMealDtos;
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            SetMeal setMeal = new SetMeal();
            setMeal.setId(id);
            setMeal.setIsDeleted(1);
            setMealDao.updateById(setMeal);
            setMealDishService.deleteBySetMealId(id);
        }
    }

    @Override
    public void change(Long[] ids, Integer status) {
        for (Long id : ids) {
            SetMeal setMeal = new SetMeal();
            setMeal.setStatus(status);
            setMeal.setId(id);
            setMealDao.updateById(setMeal);
        }
    }

}
