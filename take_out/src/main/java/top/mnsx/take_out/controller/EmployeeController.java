package top.mnsx.take_out.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.mnsx.take_out.annotation.UnAuth;
import top.mnsx.take_out.entity.Employee;
import top.mnsx.take_out.component.ResultMap;
import top.mnsx.take_out.service.EmployeeService;
import top.mnsx.take_out.utils.JSONUtil;
import top.mnsx.take_out.vo.DataVo;

import java.util.List;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/6 21:08
 * @Description: 控制层——员工类
 */
@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController extends BaseController {
    @Autowired
    private EmployeeService employeeService;

    @UnAuth
    @PostMapping("/login")
    public String login(@RequestBody Employee employee) {
        String token = employeeService.judgeUserInfo(employee.getUsername(), employee.getPassword());
        Employee emp = employeeService.findEmployeeByUsername(employee.getUsername());
        emp.setPassword(token);

        return JSONUtil.mapToJson(ResultMap.ok(emp));
    }

    @UnAuth
    @PostMapping("/logout/{id}")
    public String logout(@PathVariable("id") Long id) {
        employeeService.clear(id);

        return JSONUtil.mapToJson(ResultMap.ok());
    }

    @PostMapping
    public String addEmployee(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);

        return JSONUtil.mapToJson(ResultMap.ok());
    }

    @GetMapping("/page/{page}/{pageSize}/{name}")
    public String page(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize, @PathVariable("name") String name) {
        // 分页查询
        PageHelper.startPage(page, pageSize);
        // 查询
        List<Employee> employees = employeeService.findEmployeeByName(name);
        // 获取分页数据
        PageInfo<Employee> pageInfo = new PageInfo<>(employees);
        long total = pageInfo.getTotal();
        // 封装数据
        DataVo<Employee> dataVo = new DataVo<>(employees, total);

        return JSONUtil.mapToJson(ResultMap.ok(dataVo));
    }

    @PutMapping
    public String update(@RequestBody Employee employee) {
        employeeService.update(employee);

        return JSONUtil.mapToJson(ResultMap.ok());
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") Long id) {
        Employee employee = employeeService.getById(id);

        return JSONUtil.mapToJson(ResultMap.ok(employee));
    }
}
