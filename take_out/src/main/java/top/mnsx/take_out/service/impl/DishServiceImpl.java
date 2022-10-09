package top.mnsx.take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import top.mnsx.take_out.dao.DishDao;
import top.mnsx.take_out.dto.DishDto;
import top.mnsx.take_out.entity.Dish;
import top.mnsx.take_out.entity.DishFlavor;
import top.mnsx.take_out.service.DishFlavorService;
import top.mnsx.take_out.service.DishService;
import top.mnsx.take_out.service.ex.DishNameHasExistException;
import top.mnsx.take_out.utils.ImageUtil;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/9 15:45
 * @Description:
 */
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishDao dishDao;
    @Autowired
    private DishFlavorService dishFlavorService;

    @Override
    public Integer getCountByCategoryId(Long id) {
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dish::getCategoryId, id);
        return dishDao.selectCount(wrapper);
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
    public void addOne(DishDto dishDto) {
        String name = dishDto.getName();
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dish::getName, name);
        Dish dish = dishDao.selectOne(wrapper);
        if (dish != null) {
            throw new DishNameHasExistException();
        }
        dishDao.insert(dishDto);
        Long id = dishDto.getId();
        List<DishFlavor> dishFlavors = dishDto.getFlavors().stream().map((item) -> {
            item.setDishId(id);
            return item;
        }).collect(Collectors.toList());
        for (DishFlavor dishFlavor : dishFlavors) {
            dishFlavorService.addOne(dishFlavor);
        }
    }

    @Override
    @Transactional
    public List<Dish> page(String name) {
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeRight(name != null, Dish::getName, name);
        List<Dish> dishes = dishDao.selectList(wrapper);
        return dishes;
    }

    @Override
    public Dish getById(Long id) {
        return dishDao.selectById(id);
    }

    @Override
    public void updateOne(DishDto dishDto) {
        dishDao.updateById(dishDto);
        dishFlavorService.deleteByDishId(dishDto.getId());
        List<DishFlavor> flavors = dishDto.getFlavors();
        List<DishFlavor> dishFlavors = dishDto.getFlavors().stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
        for (DishFlavor dishFlavor : dishFlavors) {
            dishFlavorService.addOne(dishFlavor);
        }
    }
}
