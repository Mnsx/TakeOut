package top.mnsx.take_out.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.mnsx.take_out.entity.Category;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/9 13:56
 * @Description: 分类实例类
 */
@Mapper
public interface CategoryDao extends BaseMapper<Category> {
}
