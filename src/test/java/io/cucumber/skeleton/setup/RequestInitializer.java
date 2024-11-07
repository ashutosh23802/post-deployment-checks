package io.cucumber.skeleton.setup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestInitializer {
    static final Logger logger = LogManager.getLogger(RequestInitializer.class.getName());
    protected Map<String, String>  allApiConfigs;
    private final String service;
    private final String env;

    protected RequestInitializer(){
        env = System.getProperty("api.testEnvironment");
        service = System.getProperty("api.serviceName");
        allApiConfigs = loadApiConfigDetails(service);
    }

    private  Map<String, String> loadApiConfigDetails(String service) {
        Yaml yaml = new Yaml();
        InputStream inputStream = RequestInitializer.class
                .getClassLoader()
                .getResourceAsStream("api-detail-" + env +".yaml");
        Map<String, Object> allApiConfigs = yaml.load(inputStream);
        return  ((ArrayList<Map<String,String>>) allApiConfigs.get(service))
                .stream()
                 .flatMap(map -> map.entrySet()
                .stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    protected String getApiEndpoint(){
        String hostName = allApiConfigs.get("hostName");
        String path = allApiConfigs.get("path");
        return hostName + path;
    }

}
