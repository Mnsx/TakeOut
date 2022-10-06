package top.mnsx.take_out.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.mnsx.take_out.entity.Employee;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/6 21:02
 * @Description: 持久层——员工接口
 */
@Mapper
public interface EmployeeDao extends BaseMapper<Employee> {
}
