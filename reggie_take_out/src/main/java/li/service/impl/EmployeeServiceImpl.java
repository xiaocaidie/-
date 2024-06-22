package li.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import li.entity.Employee;
import li.mapper.EmployeeMapper;
import li.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
