package top.mnsx.take_out.service;

import top.mnsx.take_out.entity.Category;

import java.util.List;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/9 13:57
 * @Description: 逻辑层分类
 */
public interface CategoryService {
    void addOne(Category category);

    List<Category> findByPage();

    void deleteById(Long id);

    void modifyById(Category category);

    List<Category> list(Category category);

    Category findById(Long id);
}
