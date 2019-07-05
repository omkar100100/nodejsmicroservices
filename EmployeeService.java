package reactive.customer.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import reactive.customer.domain.*;

public interface EmployeeService {
	
	public Employee findEmployeeByid(Integer id);
	public List<Employee> findEmployeeByDeptid(Integer deptid);
	public List<Employee> findEmployeeByFirstname(String firstname);
	public List<Employee> findEmployeeByLastname(String lastname);
	public List<Employee> findEmployeeByAge(Integer age);
	public List<Employee> findAllEmps();
	
	public void saveEmployeeRec(Employee emp);
	
	public CompletableFuture<List<Employee>> readEmployees();
	public Future<Employee>  readEmployee(Integer id);
}
