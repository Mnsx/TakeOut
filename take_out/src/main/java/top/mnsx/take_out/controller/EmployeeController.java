package top.mnsx.take_out.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.mnsx.take_out.entity.Employee;
import top.mnsx.take_out.entity.ResultMap;
import top.mnsx.take_out.service.EmployeeService;
import top.mnsx.take_out.utils.JSONUtil;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/6 21:08
 * @Description: 控制层——员工类
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController extends BaseController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public String login(@RequestBody Employee employee) {
        String token = employeeService.judgeUserInfo(employee.getUsername(), employee.getPassword());
        Employee emp = employeeService.findEmployeeByUsername(employee.getUsername());
        emp.setPassword(token);

        return JSONUtil.mapToJson(ResultMap.ok(emp));
    }

    @PostMapping("/logout/{id}")
    public String logout(@PathVariable("id") Integer id) {
        employeeService.clear(id);

        return JSONUtil.mapToJson(ResultMap.ok());
    }

    @PostMapping
    public String addEmployee() {
        return null;
    }
}
