package top.mnsx.take_out.service;

import org.springframework.web.multipart.MultipartFile;
import top.mnsx.take_out.dto.DishDto;
import top.mnsx.take_out.entity.Dish;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/9 15:45
 * @Description:
 */
public interface DishService {
    Integer getCountByCategoryId(Long id);

    String addPicture(MultipartFile multipartFile);

    void getPicture(HttpServletResponse response, String fileName);

    void addOne(DishDto dishDto);

    List<Dish> page(String name);

    Dish getById(Long id);

    void updateOne(DishDto dishDto);
}
