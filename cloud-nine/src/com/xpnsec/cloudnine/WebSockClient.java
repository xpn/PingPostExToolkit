package com.xpnsec.cloudnine;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WebSockClient extends Endpoint {
    private Session session = null;
    private PingTools pingTools = null;

    public static void NewClient(URI endpointURI, String token) throws DeploymentException, IOException {

        ClientEndpointConfig.Builder configBuilder = ClientEndpointConfig.Builder.create();

        configBuilder.configurator(new ClientEndpointConfig.Configurator() {
            @Override
            public void beforeRequest(Map<String, List<String>> headers) {
                headers.put("Ping-Gateway-Instance-Hostname", Arrays.asList("b958e6be9c9d"));
                headers.put("Ping-Gateway-Instance-Id", Arrays.asList("a600d729-9d1d-48fc-9997-154343986b9a"));
                headers.put("Ping-Gateway-Instance-Version", Arrays.asList("3.0.4"));
                headers.put("Ping-Gateway-Token", Arrays.asList(token));
                headers.put("Ping-Gateway-Type", Arrays.asList("ldap-gateway"));
                headers.put("User-Agent", Arrays.asList("Jetty/9.4.46.v20220331"));
                headers.put("Accept-Encoding", Arrays.asList("gzip, deflate, br"));
                headers.put("Accept-Language", Arrays.asList("en-US,en;q=0.8,zh-CN;q=0.6,zh;q=0.4"));
                headers.put("Upgrade", Arrays.asList("websocket"));
                headers.put("Cache-Control", Arrays.asList("no-cache"));
                headers.put("Connection", Arrays.asList("Upgrade"));
                headers.put("Sec-WebSocket-Version", Arrays.asList("13"));
            }
        });

        // Connect to the websocket server
        ClientEndpointConfig clientConfig = configBuilder.build();
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.connectToServer(WebSockClient.class, clientConfig, endpointURI);
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        System.out.println("[*] Opening WebSocket");

        this.session = session;
        this.pingTools = new PingTools(session);
        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                pingTools.HandleIncomingMessage(message);
            }
        });

        try {
            this.session.getBasicRemote().sendText("{\"messageType\":\"initialize\",\"logMessages\":[],\"requestId\":\"be95e956-e840-4974-a7bd-88e7a13c5eb1\",\"data\":{\"instanceStats\":{\"unavailableDetails\":null,\"health\":\"HEALTHY\"}}}");
        } catch (Exception e) {
            System.out.println("[!] Error During Send: " + e.getMessage());
        }
    }
}
