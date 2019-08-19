package com.rezorl.jwt.springbootjwtsecurityjpaliquibase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootJwtSecurityJpaLiquibaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJwtSecurityJpaLiquibaseApplication.class, args);
    }



    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

/*    @EventListener(ApplicationReadyEvent.class)
    public void run() throws Exception {

        AppUser admin = new AppUser();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setEmail("admin@email.com");
        admin.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN, Role.ROLE_CLIENT)));

        userService.signup(admin);

        AppUser client = new AppUser();
        client.setUsername("client");
        client.setPassword("client");
        client.setEmail("client@email.com");
        client.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));

        userService.signup(client);
    }*/

}
