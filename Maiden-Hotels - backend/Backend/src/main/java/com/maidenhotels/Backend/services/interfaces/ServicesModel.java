package com.maidenhotels.Backend.services.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;

// Component autowired to all services.
// Methods use to do request and receive responses to/from TIBCO
// I - generic class (classes from TIBCO.generatedSchemas used) example: [Model Type] Airports, Airplanes, Clients, Flights, ...
// O - generic class (classes from TIBCO.generatedSchemas used) example: [Status Type] StatusAirport, StatusAirplane ...
@Component
public class ServicesModel<I,O> {

    @Autowired
    private Jaxb2Marshaller marshaller;

    private WebServiceTemplate template ;

    public WebServiceTemplate getTemplate() {
        template = new WebServiceTemplate(marshaller);
        return template;
    }

    public I getResult(String uri, String soapAction, I request) {
        template = new WebServiceTemplate(marshaller);
        template.setDefaultUri(uri);

        I dataOutput = (I) template.marshalSendAndReceive(request, message -> ((SoapMessage)message).setSoapAction(soapAction));
        return dataOutput;
    }

    public O getStatus(String uri, String soapAction, I request) {
        template = new WebServiceTemplate(marshaller);
        template.setDefaultUri(uri);

        O dataOutput = (O) template.marshalSendAndReceive(request, message -> ((SoapMessage)message).setSoapAction(soapAction));
        return dataOutput;
    }



}
