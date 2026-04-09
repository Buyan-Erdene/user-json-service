package com.example.userjson.config;

import com.example.userjson.client.SoapAuthClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

@Configuration
public class SoapClientConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.example.users");
        return marshaller;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller marshaller) {
        WebServiceTemplate template = new WebServiceTemplate();
        template.setMarshaller(marshaller);
        template.setUnmarshaller(marshaller);
        String soapUrl = System.getenv("SOAP_SERVICE_URL");
        if (soapUrl == null || soapUrl.isEmpty()) {
            soapUrl = "http://localhost:8081/ws";
        }
        template.setDefaultUri(soapUrl);
        
        return template;
    }

    @Bean
    public SoapAuthClient soapAuthClient(WebServiceTemplate webServiceTemplate) {
        return new SoapAuthClient(webServiceTemplate);
    }
}