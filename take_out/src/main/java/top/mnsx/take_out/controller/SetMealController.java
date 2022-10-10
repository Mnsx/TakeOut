package top.mnsx.take_out.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.mnsx.take_out.annotation.UnAuth;
import top.mnsx.take_out.component.ResultMap;
import top.mnsx.take_out.dto.SetMealDto;
import top.mnsx.take_out.entity.SetMeal;
import top.mnsx.take_out.service.SetMealDishService;
import top.mnsx.take_out.service.SetMealService;
import top.mnsx.take_out.utils.JSONUtil;
import top.mnsx.take_out.vo.DataVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/10 14:11
 * @Description:
 */
@RestController
@RequestMapping("/setMeal")
@Slf4j
public class SetMealController {
    @Autowired
    private SetMealDishService setMealDishService;
    @Autowired
    private SetMealService setMealService;

    @UnAuth
    @PostMapping("/upload")
    public String addPicture(@RequestParam("file") MultipartFile multipartFile) {
        String fileName = setMealService.addPicture(multipartFile);

        return JSONUtil.mapToJson(ResultMap.ok(fileName));
    }

    @GetMapping("/download/{fileName}")
    public String showPicture(HttpServletResponse response, @PathVariable("fileName") String fileName) {
        setMealService.getPicture(response, fileName);

        return JSONUtil.mapToJson(ResultMap.ok(fileName));
    }

    @PostMapping
    public String save(@RequestBody SetMealDto setMealDto) {
        setMealService.addOne(setMealDto);

        return JSONUtil.mapToJson(ResultMap.ok());
    }

    @GetMapping("/page")
    public String page(int page, int pageSize, String name) {
        PageHelper.startPage(page, pageSize);
        List<SetMealDto> setMealDtos = setMealService.page(page, pageSize, name);

        PageInfo<SetMealDto> pageInfo = new PageInfo<>(setMealDtos);
        long total = pageInfo.getTotal();

        DataVo<SetMealDto> objectDataVo = new DataVo<>(setMealDtos, total);

        return JSONUtil.mapToJson(ResultMap.ok(objectDataVo));
    }

    @DeleteMapping
    public String delete(Long[] ids) {
        setMealService.delete(ids);

        return JSONUtil.mapToJson(ResultMap.ok());
    }

    @PostMapping("/status/{status}")
    public String change(@PathVariable("status") Integer status, @RequestParam("ids") Long[] ids) {
        setMealService.change(ids, status);

        return JSONUtil.mapToJson(ResultMap.ok());
    }
}
