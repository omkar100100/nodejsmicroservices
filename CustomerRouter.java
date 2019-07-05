package reactive.customer.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;

import reactive.customer.handler.CustomerHandler;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@Configuration
@EnableWebFlux
public class CustomerRouter {
	
	@Autowired
	private CustomerHandler empDataHandler;
		
	@Bean
	public RouterFunction<ServerResponse> employeeServiceBox() {
	    return 		route(GET("/listFluxEmps"), empDataHandler::empList)
	    		   .andRoute(GET("/selectEmpById/{id}"), empDataHandler::empById)
	    		   .andRoute(POST("/selectFluxEmps"), empDataHandler::chooseFluxEmps)
	    		   .andRoute(POST("/saveEmp"), empDataHandler::saveEmployee);
	    
//	    return RouterFunctions.route()
//                .path("/customer", builder -> builder
//                        .POST("", accept(MediaType.APPLICATION_JSON), empDataHandler::saveEmployee)
//                        .GET("/{id}", accept(MediaType.APPLICATION_JSON), empDataHandler::empById)
//                        .GET("", accept(MediaType.APPLICATION_JSON),empDataHandler::empList))
//                .build();
	    
	}
}
