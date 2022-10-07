package top.mnsx.take_out.service;

import top.mnsx.take_out.entity.Employee;

import java.util.List;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/6 21:03
 * @Description: 逻辑层——员工接口
 */
public interface EmployeeService {
    Employee findEmployeeByUsername(String username);

    List<Employee> findEmployeeByName(String name);

    String judgeUserInfo(String username, String password);

    void clear(Long id);

    void addEmployee(Employee employee);

    void update(Employee employee);

    Employee getById(Long id);
}
