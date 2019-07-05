package reactive.customer.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactive.customer.domain.Employee;
import reactive.customer.handler.CustomerHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerHandler customerHandler;
	
	@RequestMapping("/objectSampleRest")
	public String exposeString() {
		return "Hello World";
	}
	
	@GetMapping("/monoSampleRest")
	public Mono<String> exposeMono() {
		return Mono.just("Hello World");
	}
	
	
	@GetMapping("/customer")
	public Mono<ServerResponse> getCustomers(ServerRequest request) {
		return customerHandler.empList(request);
	}
	
//	@GetMapping("/selectReactEmps")
//	public Flux<Employee> selectReactEmps() {
//		return Flux.fromIterable(employeeServiceImpl.findAllEmps());
//	}
//	
//
//	@GetMapping("/selectReactEmp/{id}")
//	public Mono<Employee> selectReactEmpDetail(@PathVariable("id") Integer id) {
//		return Mono.justOrEmpty(employeeServiceImpl.findEmployeeByid(id)).defaultIfEmpty(new Employee());
//	}
//	
//	@PostMapping("/saveReactEmp")
//	public Mono<Void> saveReactEmpDetail(@RequestBody Employee dept) {
//		 return Mono.justOrEmpty(dept).doOnNext(employeeServiceImpl::saveEmployeeRec).then();
//	}
	

}
