package pt.galina.chap_17_jmxmonitoring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.galina.chap_17_jmxmonitoring.handler.*;
import pt.galina.chap_17_jmxmonitoring.router.*;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterFunctionConfig {

    private final DesignTacoRouter designTacoRouter;
    private final DiscountRouter discountRouter;
    private final HomeRouter homeRouter;
    private final LoginRouter loginRouter;
    private final OrderRouter orderRouter;
    private final RegistrationRouter registrationRouter;
    private final UserRouter userRouter;

    public RouterFunctionConfig(DesignTacoRouter designTacoRouter,
                                DiscountRouter discountRouter,
                                HomeRouter homeRouter,
                                LoginRouter loginRouter,
                                OrderRouter orderRouter,
                                RegistrationRouter registrationRouter,
                                UserRouter userRouter) {
        this.designTacoRouter = designTacoRouter;
        this.discountRouter = discountRouter;
        this.homeRouter = homeRouter;
        this.loginRouter = loginRouter;
        this.orderRouter = orderRouter;
        this.registrationRouter = registrationRouter;
        this.userRouter = userRouter;
    }


    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .add(designTacoRouter.designTacoRoutes())
                .add(discountRouter.route())
                .add(homeRouter.homeRoute())
                .add(loginRouter.loginRoute())
                .add(orderRouter.orderRoutes())
                .add(registrationRouter.registrationRoutes())
                .add(userRouter.userRoutes())
                .build();
    }
}
