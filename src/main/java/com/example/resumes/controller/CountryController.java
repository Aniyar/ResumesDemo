package com.example.resumes.controller;


import com.example.resumes.repository.CountryRepository;
import io.spring.guides.gs_producing_web_service.GetCountryRequest;
import io.spring.guides.gs_producing_web_service.GetCountryResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;


@Endpoint
@Controller
@AllArgsConstructor
@Slf4j
public class CountryController {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private final CountryRepository countryRepository;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public JAXBElement<GetCountryResponse> getCountry(@RequestPayload JAXBElement<GetCountryRequest> requestElement) {
        GetCountryRequest request = requestElement.getValue();
        GetCountryResponse response = new GetCountryResponse();
        response.setCountry(countryRepository.findCountry(request.getName()));
        QName qname = new QName("http://spring.io/guides/gs-producing-web-service", "getCountryResponse");
        return new JAXBElement<>(qname, GetCountryResponse.class, response);}
}
