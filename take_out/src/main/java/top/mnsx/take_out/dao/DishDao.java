package top.mnsx.take_out.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.mnsx.take_out.entity.Dish;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/9 15:45
 * @Description:
 */
@Mapper
public interface DishDao extends BaseMapper<Dish> {
}
