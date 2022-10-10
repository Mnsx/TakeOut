package top.mnsx.take_out.service;

import org.springframework.web.multipart.MultipartFile;
import top.mnsx.take_out.dto.SetMealDto;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/9 15:48
 * @Description:
 */
public interface SetMealService {
    Integer getCountByCategoryId(Long id);

    String addPicture(MultipartFile multipartFile);

    void getPicture(HttpServletResponse response, String fileName);

    void addOne(SetMealDto setMealDto);

    List<SetMealDto> page(int page, int pageSize, String name);

    void delete(Long[] ids);

    void change(Long[] ids, Integer status);
}
