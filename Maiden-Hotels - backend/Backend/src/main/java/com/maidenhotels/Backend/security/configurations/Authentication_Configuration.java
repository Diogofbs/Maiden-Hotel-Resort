package com.maidenhotels.Backend.security.configurations;

import com.maidenhotels.Backend.security.SecurityEncoder;
import com.maidenhotels.Backend.security.jsonWT.JwtAuthenticationFilter;
import com.maidenhotels.Backend.security.jsonWT.JwtAuthorizationFilter;
import com.maidenhotels.Backend.security.jsonWT.JwtProperties;
import com.maidenhotels.Backend.security.tibcoToDB.UsersDetails_services;
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

   private static final String ADMIN = "1"; //Administrator
   private static final String OPERATOR = "3"; //Operator
   private static final String MANAGER = "2"; //Manager

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
                //.antMatchers("/**").permitAll()
                .antMatchers("/login").permitAll()
                                                    // --BACKOFFICE-- //
                .antMatchers("/Backoffices/BackofficeByParam", "/Backoffices/BackofficeUpdate").hasAnyRole(MANAGER, OPERATOR, ADMIN)
                .antMatchers("/Backoffices/**").hasRole(ADMIN)
                                                    // --ROLES-- //
                .antMatchers("/Roles/RoleDelete","/Roles/RoleCreate","/Roles/RoleUpdate").hasRole(ADMIN)
                .antMatchers("/Roles/**").hasAnyRole(MANAGER, OPERATOR, ADMIN)
                                                    // --HOTELS-- //
                .antMatchers("/Hotels/HotelDelete" ).hasRole(ADMIN)
                .antMatchers("/Hotels","/Hotels/HotelByParam").permitAll()
                .antMatchers("/Hotels/**" ).hasAnyRole(MANAGER,ADMIN)
                                                     // --ROOMS-- //
                .antMatchers("/Rooms/RoomDelete" ).hasRole(ADMIN)
                .antMatchers("/Rooms","/Rooms/RoomByParam").permitAll()
                .antMatchers("/Rooms/**" ).hasAnyRole(MANAGER,ADMIN)
                                                    // --SERVICES-- //
                .antMatchers("/Services/ServiceDelete" ).hasRole(ADMIN)
                .antMatchers("/Services","/Services/ServiceByParam").permitAll()
                .antMatchers("/Services/**" ).hasAnyRole(MANAGER,ADMIN)
                                                    // --HOTELS-SERVICES-- //
                .antMatchers("/ServicesHotel/ServicesHotelDelete" ).hasRole(ADMIN)
                .antMatchers("/ServicesHotel/ServicesHotelUpdate", "/ServicesHotel/ServicesHotelCreate" ).hasAnyRole(MANAGER, ADMIN)
                .antMatchers("/ServicesHotel","/ServicesHotel/ServicesHotelByParam").permitAll()
                .antMatchers("/ServicesHotel/**" ).hasAnyRole(MANAGER,ADMIN)
                                                    // --HOTELS-ROOMS-- //
                .antMatchers("/RoomsHotel/RoomsHotelDelete" ).hasRole(ADMIN)
                .antMatchers("/RoomsHotel/RoomsHotelUpdate", "/RoomsHotel/RoomsHotelCreate" ).hasAnyRole(MANAGER, ADMIN)
                .antMatchers("/RoomsHotel","/RoomsHotel/RoomsHotelByParam").permitAll()
                .antMatchers("/RoomsHotel/**" ).hasAnyRole(MANAGER,ADMIN)
                                                    // --CLIENT-- //
                .antMatchers("/Clients/ClientDelete" ).hasRole(ADMIN)
                .antMatchers("/Clients/ClientCreate").permitAll()
                .antMatchers("/Clients/ClientByID", "/Clients/ClientUpdate").hasAnyRole("CLIENT",ADMIN)
                .antMatchers("/Clients/**" ).hasAnyRole(MANAGER, ADMIN)
                                                    // --GUEST-- //
                .antMatchers("/Guests/GuestDelete" ).hasRole(ADMIN)
                .antMatchers("/Guests/GuestCreate").permitAll()
                .antMatchers("/Guests/GuestByID", "/Guests/GuestUpdate").hasAnyRole("CLIENT",OPERATOR, MANAGER, ADMIN)
                .antMatchers("/Guests/**" ).hasAnyRole(MANAGER,ADMIN)

                                                    // --BOOKINGS-- //
                .antMatchers("/Bookings/BookingDelete" ).hasAnyRole(ADMIN,MANAGER)
                .antMatchers("/Bookings","/Bookings/BookingInsertServices", "/Bookings/BookingDeleteServices", "/Bookings/BookingUpdate" ).hasAnyRole(OPERATOR, ADMIN,MANAGER)
                //.antMatchers("/Bookings/**" ).hasAnyRole(ADMIN,OPERATOR,"CLIENT")
                .antMatchers("/Bookings/**" ).permitAll()
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
