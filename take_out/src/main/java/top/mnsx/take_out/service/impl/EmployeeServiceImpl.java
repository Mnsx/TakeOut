package top.mnsx.take_out.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mnsx.take_out.dao.EmployeeDao;
import top.mnsx.take_out.entity.Employee;
import top.mnsx.take_out.service.EmployeeService;
import top.mnsx.take_out.service.ex.EmployeeHasBanException;
import top.mnsx.take_out.service.ex.EmployeeNotExistException;
import top.mnsx.take_out.service.ex.InsertException;
import top.mnsx.take_out.service.ex.PasswordNotSuccessException;
import top.mnsx.take_out.utils.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/6 21:32
 * @Description:
 */
@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public Employee findEmployeeByUsername(String username) {
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, username);
        return employeeDao.selectOne(queryWrapper);
    }

    @Override
    public String judgeUserInfo(String username, String password) {
        // 通过MD5加密password
        password = MD5Util.inputPassToTPass(password, null);
        // 通过username查询员工
        log.info("start");
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, username);
        Employee employee = employeeDao.selectOne(queryWrapper);
        // 如果没有查询结果返回登录失败数据
        if (employee == null) {
            throw new EmployeeNotExistException();
        }
        // 密码比对，如果不一致则返回登录失败
        if (!employee.getPassword().equals(password)) {
            throw new PasswordNotSuccessException();
        }
        // 查看员工状态是否被禁用
        if (employee.getStatus() == 0) {
            throw new EmployeeHasBanException();
        }
        log.info("hello");
        // 处理员工数据
        employee.setPassword(null);
        // 登录成功，生成Token
        Map<String, String> map = new HashMap<>();
        map.put("loginTime", TimeUtil.LDTToString(LocalDateTime.now()));
        map.put("id", String.valueOf(employee.getId()));
        String token = JWTUtil.getToken(map);
        // 将员工id存放进入redis
        Boolean ifSuccess = redisUtil.hSet("onlineEmployee", String.valueOf(employee.getId()), JSON.toJSONString(employee));
        if (!ifSuccess) {
            throw new InsertException();
        }
        return token;
    }

    @Override
    public void clear(Integer id) {
        redisUtil.hDel("onlineEmployee", String.valueOf(id));
    }
}
