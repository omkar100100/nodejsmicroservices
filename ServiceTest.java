package reactive.customer.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration.WebFluxConfig;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import junit.framework.Assert;
import reactive.customer.Application;
import reactive.customer.config.CachingConfig;
import reactive.customer.config.SpringAsynchConfig;
import reactive.customer.config.SpringDataConfig;
import reactive.customer.domain.Employee;
import reactive.customer.handler.CustomerHandler;
import reactive.customer.router.CustomerRouter;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {  WebFluxConfig.class,
//		Application.class })//, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ContextConfiguration(classes={Application.class,CachingConfig.class,SpringAsynchConfig.class,SpringDataConfig.class})
@ContextConfiguration(classes={CustomerRouter.class,CustomerHandler.class,Application.class,CachingConfig.class,SpringAsynchConfig.class,SpringDataConfig.class})
@WebFluxTest
public class ServiceTest {

	private WebTestClient webTestClient;

	@Autowired
    private ApplicationContext context;
	
	@Before
	public void setUp() {
		webTestClient = WebTestClient.bindToApplicationContext(context).build();
	}

	// @Test
	public void saveEmployee() {

		Employee employee = new Employee();
		employee.setId(1);
		employee.setFirstname("Balu");
		employee.setLastname("Krishnan");

		webTestClient.post().uri("http://localhost:8080/saveEmp")
				// .contentType(MediaType.APPLICATION_JSON)
				// .accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(employee), Employee.class).exchange().expectStatus().isOk().expectHeader()
				.contentType(MediaType.APPLICATION_JSON);
		// .expectBody().isEmpty();
	}

	@Test
	public void testEmpList() {
		FluxExchangeResult<reactive.customer.domain.Employee> result = webTestClient.get()
				.uri("/listFluxEmps").accept(MediaType.APPLICATION_JSON).exchange()
				.returnResult(reactive.customer.domain.Employee.class);
		Assert.assertEquals(result.getStatus().value(), 200);
		List<Employee> users = result.getResponseBody().collectList().block();
		System.out.println(users.get(0).getFirstname());

	}
}