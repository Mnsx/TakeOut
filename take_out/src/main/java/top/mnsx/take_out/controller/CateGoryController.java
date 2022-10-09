package top.mnsx.take_out.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.mnsx.take_out.component.ResultMap;
import top.mnsx.take_out.entity.Category;
import top.mnsx.take_out.service.CategoryService;
import top.mnsx.take_out.utils.JSONUtil;
import top.mnsx.take_out.vo.DataVo;

import java.util.List;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/9 14:00
 * @Description:
 */
@RestController
@CrossOrigin
@RequestMapping("/category")
public class CateGoryController extends BaseController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public String saveOne(@RequestBody Category category) {
        categoryService.addOne(category);

        return JSONUtil.mapToJson(ResultMap.ok());
    }

    @GetMapping("/page/{page}/{pageSize}")
    public String page(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Category> categories = categoryService.findByPage();
        PageInfo<Category> pageInfo = new PageInfo<>(categories);
        long total = pageInfo.getTotal();
        DataVo<Category> dataVo = new DataVo<>(categories, total);

        return JSONUtil.mapToJson(ResultMap.ok(dataVo));
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        categoryService.deleteById(id);

        return JSONUtil.mapToJson(ResultMap.ok());
    }

    @PutMapping
    public String update(@RequestBody Category category) {
        categoryService.modifyById(category);

        return JSONUtil.mapToJson(ResultMap.ok());
    }

    @GetMapping("/list")
    public String list(@RequestParam("type") Integer type) {
        Category temp = new Category();
        temp.setType(type);
        List<Category> list = categoryService.list(temp);

        return JSONUtil.mapToJson(ResultMap.ok(list));
    }
}
