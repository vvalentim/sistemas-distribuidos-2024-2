package com.vvalentim.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.errors.ResponseGenericError;

public class MessageParser {
    private static final ObjectMapper mapper = new ObjectMapper();

    public ResponsePayload deserialize(String message, Class<?> expectedResponse) {
        ResponsePayload payload = null;

        try {
            ObjectReader reader = MessageParser.mapper.reader();
            JsonNode root = reader.readTree(message);
            JsonNodeType statusNodeType = root.get("status").getNodeType();
            int status;

            if (statusNodeType != JsonNodeType.NUMBER) {
                throw new NullPointerException();
            }

            status = root.get("status").asInt();

            if (status > 199 && status < 300) {
                payload = (ResponsePayload) MessageParser.mapper.convertValue(root, expectedResponse);
            } else {
                payload = MessageParser.mapper.convertValue(root, ResponseGenericError.class);
            }

        } catch (Exception e) {
            System.out.println("Parsing error: " + e.getMessage());
        }

        return payload;
    }

    public String serialize(RequestPayload payload) {
        try {
            ObjectWriter writer = MessageParser.mapper.writer();

            return writer.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
