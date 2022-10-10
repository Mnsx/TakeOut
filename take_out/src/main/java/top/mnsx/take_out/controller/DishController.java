package top.mnsx.take_out.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.mnsx.take_out.annotation.UnAuth;
import top.mnsx.take_out.component.ResultMap;
import top.mnsx.take_out.dto.DishDto;
import top.mnsx.take_out.entity.Category;
import top.mnsx.take_out.entity.Dish;
import top.mnsx.take_out.entity.DishFlavor;
import top.mnsx.take_out.service.CategoryService;
import top.mnsx.take_out.service.DishFlavorService;
import top.mnsx.take_out.service.DishService;
import top.mnsx.take_out.utils.JSONUtil;
import top.mnsx.take_out.vo.DataVo;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/9 17:16
 * @Description:
 */
@RestController
@RequestMapping("/dish")
@CrossOrigin
@Slf4j
public class DishController extends BaseController {
    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishFlavorService dishFlavorService;

    @UnAuth
    @PostMapping("/upload")
    public String addPicture(@RequestParam("file") MultipartFile multipartFile) {
        String fileName = dishService.addPicture(multipartFile);

        return JSONUtil.mapToJson(ResultMap.ok(fileName));
    }

    @GetMapping("/download/{fileName}")
    public String showPicture(HttpServletResponse response, @PathVariable("fileName") String fileName) {
        dishService.getPicture(response, fileName);

        return JSONUtil.mapToJson(ResultMap.ok(fileName));
    }

    @PostMapping
    public String addOne(@RequestBody DishDto dishDto) {
        dishService.addOne(dishDto);

        return JSONUtil.mapToJson(ResultMap.ok());
    }

    @GetMapping("/page/{page}/{pageSize}/{name}")
    public String page(@PathVariable("page") Integer page, @PathVariable("pageSize") Integer pageSize, @PathVariable("name") String name) {
        PageHelper.startPage(page, pageSize);
        List<Dish> dishes = dishService.page(name.equals("null") ? null : name);
        PageInfo<Dish> pageInfo = new PageInfo<>(dishes);
        long total = pageInfo.getTotal();
        List<DishDto> dishDtos = dishes.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            Long categoryId = item.getCategoryId();
            Category category = categoryService.findById(categoryId);
            String categoryName = category.getName();
            dishDto.setCategoryName(categoryName);
            BeanUtils.copyProperties(item, dishDto);
            return dishDto;
        }).collect(Collectors.toList());
        DataVo<DishDto> dataVo = new DataVo<>(dishDtos, total);
        return JSONUtil.mapToJson(ResultMap.ok(dataVo));
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id) {
        Dish dish = dishService.getById(id);
        Long categoryId = dish.getCategoryId();
        Category category = categoryService.findById(categoryId);
        String name = category.getName();
        List<DishFlavor> byDishId = dishFlavorService.getByDishId(dish.getId());
        DishDto dishDto = new DishDto();
        dishDto.setCategoryName(name);
        BeanUtils.copyProperties(dish, dishDto);
        dishDto.setFlavors(byDishId);

        return JSONUtil.mapToJson(ResultMap.ok(dishDto));
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id, @RequestBody DishDto dishDto) {
        dishDto.setId(id);
        dishService.updateOne(dishDto);

        return JSONUtil.mapToJson(ResultMap.ok());
    }

    @PostMapping("/status/{status}")
    public String changeStatus(@PathVariable("status") Integer status, @RequestParam("ids") Integer[] ids) {
        dishService.changeStatus(status, ids);

        return JSONUtil.mapToJson(ResultMap.ok());
    }

    @DeleteMapping
    public String delete(@RequestParam("ids") Integer[] ids) {
        dishService.delete(ids);

        return JSONUtil.mapToJson(ResultMap.ok());
    }

    @GetMapping("/list")
    public String list(Dish dish) {
        List<Dish> list = dishService.list(dish);

        return JSONUtil.mapToJson(ResultMap.ok(list));
    }
}
