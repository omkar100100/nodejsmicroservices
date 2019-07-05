package reactive.customer.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import reactive.customer.domain.Employee;
import reactive.customer.service.EmployeeService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Component
public class CustomerHandler {

	@Autowired
	private EmployeeService employeeServiceImpl;

	public Mono<ServerResponse> empList(ServerRequest request) {
		Flux<Employee> flux = Flux.fromIterable(employeeServiceImpl.findAllEmps());
		return ok().contentType(MediaType.APPLICATION_STREAM_JSON).body(flux, Employee.class);
	}

	public Mono<ServerResponse> empById(ServerRequest request) {
		Scheduler scheduler = Schedulers.newSingle("sub_thread");
		Mono<Employee> emp = Mono
				.defer(() -> Mono.justOrEmpty(
						employeeServiceImpl.findEmployeeByid(Integer.parseInt(request.pathVariable("id")))))
				.subscribeOn(scheduler);
		return ok().contentType(MediaType.APPLICATION_STREAM_JSON).body(emp, Employee.class)
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> chooseFluxEmps(ServerRequest request) {
		Scheduler scheduler = Schedulers.newSingle("sub_thread");
		Mono<Employee> emp = Mono
				.defer(() -> Mono.justOrEmpty(
						employeeServiceImpl.findEmployeeByid(Integer.parseInt(request.pathVariable("id")))))
				.subscribeOn(scheduler);
		return ok().contentType(MediaType.APPLICATION_STREAM_JSON).body(emp, Employee.class)
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> saveEmployee(ServerRequest request) {
		Scheduler subWorker = Schedulers.newSingle("sub-thread");
		Mono<Employee> emp = request.bodyToMono(Employee.class).doOnNext(employeeServiceImpl::saveEmployeeRec)
				.subscribeOn(subWorker);
		return ok().contentType(MediaType.APPLICATION_STREAM_JSON).build(emp.then());
	}
}
