package com.MaidenAirlineProject.security.configurations;

import com.MaidenAirlineProject.security.SecurityEncoder;
import com.MaidenAirlineProject.security.jsonWT.JwtAuthenticationFilter;
import com.MaidenAirlineProject.security.jsonWT.JwtAuthorizationFilter;
import com.MaidenAirlineProject.security.jsonWT.JwtProperties;
import com.MaidenAirlineProject.security.tibcoToDB.UsersDetails_services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Authentication_Configuration extends WebSecurityConfigurerAdapter {

   @Autowired
    private SecurityEncoder securityEncoder;
   @Autowired
   private UsersDetails_services usersDetails_services;

   private static final String ADMIN = "2"; //Administrator
   private static final String CLIENT_MANAGER = "7"; // Client_Manager
   private static final String MANAGER_OPERATOR = "1"; // Manager_Operator

    @Bean
    public CorsFilter corsFilter() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // Allow all origins acessing the controllers : DANGEROUS
        config.addAllowedOrigin("*"); // TODO: lock down before deploying
        config.addAllowedHeader("*");
        //Expose: Authorization (Token exposed in body, so I think it's not necessary expose this)
        config.addExposedHeader(JwtProperties.HEADER_STRING);
        // Allow methods: get,post,options,put,delete....
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // add jwt filters (1. authentication, 2. authorization)
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), usersDetails_services))
                .authorizeRequests()
                // configure access rules
                .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                .antMatchers("/login").permitAll()
                                                    // --BACKOFFICE-- //
                .antMatchers("/Backoffices/BackofficeByUsername").authenticated()//Wrong -- you have to change
                .antMatchers("/Backoffices/**").hasRole(ADMIN)
                                                    // --ROLES-- //
                .antMatchers("/Roles/RolesDelete","/Roles/RolesCreate","/Roles/RolesUpdate").hasRole(ADMIN)
                .antMatchers("/Roles/**").authenticated()
                                                    // --BOOKING TYPES-- //
                .antMatchers("/BookingTypes/BookingTypeDelete").hasRole(ADMIN)
                .antMatchers("/BookingTypes/BookingTypeCreate","/BookingTypes/BookingTypeUpdate" ).hasAnyRole(ADMIN,MANAGER_OPERATOR)
                .antMatchers("/BookingTypes/**" ).permitAll()
                                                    // --CLIENT TYPES-- //
                .antMatchers("/ClientTypes/ClientTypeDelete").hasRole(ADMIN)
                .antMatchers("/ClientTypes/ClientTypeCreate","/ClientTypes/ClientTypeUpdate" ).hasAnyRole(ADMIN,MANAGER_OPERATOR)
                .antMatchers("/ClientTypes/**" ).permitAll()
                                                    // --FLIGHTS-- //
                .antMatchers("/Flights/FlightDelete" ).hasRole(ADMIN)
                .antMatchers("/Flights","/Flights/FlightByID","/Flights/FlightByDateAndLocal" ).permitAll()
                .antMatchers("/Flights/**" ).hasAnyRole(MANAGER_OPERATOR,ADMIN)
                                                    // --AIRPLANES-- //
                .antMatchers("/Airplanes/AirplaneDelete" ).hasRole(ADMIN)
                .antMatchers("/Airplanes","/Airplanes/AirplanesByID" ).permitAll()
                .antMatchers("/Airplanes/**" ).hasAnyRole(MANAGER_OPERATOR,ADMIN)
                                                    // --AIRPORTS-- //
                .antMatchers("/Airports/AirportDelete" ).hasRole(ADMIN)
                .antMatchers("/Airports","/Airports/AirportByID" ).permitAll()
                .antMatchers("/Airports/**" ).hasAnyRole(MANAGER_OPERATOR,ADMIN)
                                                    // --CLIENT-- //
                .antMatchers("/Clients/ClientDelete" ).hasRole(ADMIN)
                .antMatchers("/Clients/ClientCreate").permitAll()
                .antMatchers("/Clients/ClientByID", "/Clients/ClientUpdate","/Clients/ClientByEmail"  ).hasAnyRole("CLIENT",ADMIN)
                .antMatchers("/Clients/**" ).hasAnyRole(CLIENT_MANAGER,ADMIN)
                                                    // --BOOKINGS-- //
                .antMatchers("/Bookings/BookingDelete" ).hasAnyRole(ADMIN,CLIENT_MANAGER)
                .antMatchers("/Bookings" ).hasAnyRole(CLIENT_MANAGER,ADMIN)
                .antMatchers("/Bookings/**" ).hasAnyRole(ADMIN,CLIENT_MANAGER,"CLIENT")
                .anyRequest().authenticated();
    }

    @Bean
    AuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(securityEncoder.passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(usersDetails_services);
        return daoAuthenticationProvider;
    }
}
