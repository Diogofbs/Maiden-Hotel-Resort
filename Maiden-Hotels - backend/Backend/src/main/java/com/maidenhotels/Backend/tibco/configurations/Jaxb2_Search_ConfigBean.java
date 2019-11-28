package com.maidenhotels.Backend.tibco.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

//Set the packages to search for classes with JAXB2 annotations in the classpath
// JAXB2 classes - created by TIBCO WSDL

@Configuration
public class Jaxb2_Search_ConfigBean {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("com.maidenhotels.Backend.tibco.schemas");
        return marshaller;
    }

}
