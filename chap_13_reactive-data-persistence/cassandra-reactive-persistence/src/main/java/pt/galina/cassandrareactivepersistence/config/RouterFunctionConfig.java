package pt.galina.cassandrareactivepersistence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.galina.cassandrareactivepersistence.router.*;

@Configuration
public class RouterFunctionConfig {

    private final DesignTacoRouter designTacoRouter;
    private final OrderRouter orderRouter;
    private final RegistrationRouter registrationRouter;
    private final UserRouter userRouter;
    private final LoginRouter loginRouter;
    private final HomeRouter homeRouter;

    public RouterFunctionConfig(DesignTacoRouter designTacoRouter,
                                OrderRouter orderRouter,
                                RegistrationRouter registrationRouter,
                                UserRouter userRouter,
                                LoginRouter loginRouter, HomeRouter homeRouter) {
        this.designTacoRouter = designTacoRouter;
        this.orderRouter = orderRouter;
        this.registrationRouter = registrationRouter;
        this.userRouter = userRouter;
        this.loginRouter = loginRouter;
        this.homeRouter = homeRouter;
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .add(designTacoRouter.designTacoRoutes())
                .add(orderRouter.orderRoutes())
                .add(registrationRouter.registrationRoutes())
                .add(userRouter.userRoutes())
                .add(loginRouter.loginRoute())
                .add(homeRouter.homeRoute())
                .build();
    }
}
