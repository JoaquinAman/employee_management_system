package com.example.employee.services;

import com.example.employee.entity.EmployeeEntity;
import com.example.employee.model.Employee;
import com.example.employee.repository.IEmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private IEmployeeRepository employeeRepository;
    public EmployeeServiceImpl(IEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        System.out.println("llega service");
        EmployeeEntity employeeEntity = new EmployeeEntity();
        BeanUtils.copyProperties(employee, employeeEntity);
        employeeRepository.save(employeeEntity);
        System.out.println("guarda entity");
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        List<Employee> employees = employeeEntities
                .stream()
                .map(emp -> new Employee(
                        emp.getId(),
                        emp.getFirstName(),
                        emp.getLastName(),
                        emp.getEmailId()))
                .collect(Collectors.toList());

        return employees;
    }

    @Override
    public boolean deleteEmployee(Long id) {
        EmployeeEntity employee = employeeRepository.findById(id).get();
        employeeRepository.delete(employee);
        return true;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).get();
        System.out.println("employeeEntity id: " + employeeEntity.getId());
        System.out.println("employeeEntity firstName: " + employeeEntity.getFirstName());

        Employee employee = new Employee();

        employee = toEmployee(employeeEntity);

        System.out.println("employee id: " + employee.getId());
        System.out.println("employee firstName: " + employee.getFirstName());
        return employee;
    }
    private Employee toEmployee(EmployeeEntity employeeEntity) {
        Employee employee = new Employee();

        BeanUtils.copyProperties(employeeEntity, employee);
        return employee;

    }
    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).get();
        BeanUtils.copyProperties(employee, employeeEntity);
        employeeRepository.save(employeeEntity);
        return employee;
    }
}
