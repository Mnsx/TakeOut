package top.mnsx.take_out.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mnsx.take_out.dao.EmployeeDao;
import top.mnsx.take_out.entity.Employee;
import top.mnsx.take_out.service.EmployeeService;
import top.mnsx.take_out.service.ex.*;
import top.mnsx.take_out.utils.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
    public List<Employee> findEmployeeByName(String name) {
        if (name.equals("null")) {
            name = "";
        }
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.likeRight(Employee::getName, name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        return employeeDao.selectList(queryWrapper);
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
        map.put("info", JSON.toJSONString(employee));
        String token = JWTUtil.getToken(map);
        // 将员工id存放进入redis
        Boolean ifSuccess = redisUtil.hSet("onlineEmployee", String.valueOf(employee.getId()), JSON.toJSONString(employee), (long) 60 * 60 * 24);
        if (!ifSuccess) {
            throw new InsertException();
        }
        return token;
    }

    @Override
    public void clear(Long id) {
        redisUtil.hDel("onlineEmployee", String.valueOf(id));
    }

    @Override
    public void addEmployee(Employee employee) {
        // 默认密码加密
        employee.setPassword(MD5Util.inputPassToTPass("123123", null));
        // 获取当前登录用户id
        Long id = ((Employee) ThreadLocalUtil.get()).getId();

        // 判断用户名是否存在
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeDao.selectOne(queryWrapper);
        if (emp != null) {
            throw new EmployeeHasExistException();
        }

        // 设置创建事件
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        // 设置创建者信息
        employee.setCreateUser(id);
        employee.setUpdateUser(id);
        // main
        employeeDao.insert(employee);
    }

    @Override
    public void update(Employee employee) {
        Long id = ((Employee) ThreadLocalUtil.get()).getId();
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(id);
        employeeDao.updateById(employee);
    }

    @Override
    public Employee getById(Long id) {
        return employeeDao.selectById(id);
    }

}
