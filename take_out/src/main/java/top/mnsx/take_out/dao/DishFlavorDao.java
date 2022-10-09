package top.mnsx.take_out.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.mnsx.take_out.controller.BaseController;
import top.mnsx.take_out.entity.DishFlavor;
import top.mnsx.take_out.service.DishFlavorService;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/9 21:37
 * @Description:
 */
@Mapper
public interface DishFlavorDao extends BaseMapper<DishFlavor> {
}
