package com.cromxt.bucket.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@Service
public class ApplicationHostNetworkFinder {


    private final Boolean APPLICATION_HOSTNAME_AUTO_DISCOVERY;
    private final Integer APPLICATION_PORT;


    public ApplicationHostNetworkFinder(
            Environment environment
    ) {
        this.APPLICATION_HOSTNAME_AUTO_DISCOVERY = environment.getProperty("BUCKET_CONFIG_MACHINE_IP_AUTO_DISCOVERY", Boolean.class);
        this.APPLICATION_PORT = environment.getProperty("BUCKET_CONFIG_GRPC_SERVICE_PORT", Integer.class);
    }

    public String getApplicationHostname() {
        String hostName;
        if(APPLICATION_HOSTNAME_AUTO_DISCOVERY) {
            try {
                InetAddress ip = InetAddress.getLocalHost();
                hostName = ip.getHostAddress();
            } catch (UnknownHostException e) {
                log.error(e.getMessage(), e);
                hostName = "localhost";
                log.warn("Bucket starts with {}", "localhost");
            }
        }else{
            hostName = "localhost";
        }
        return hostName;
    }
    public Integer getApplicationPort() {
        return APPLICATION_PORT;
    }
}
