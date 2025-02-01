package com.vvalentim.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.vvalentim.server.exceptions.InvalidPayloadFieldType;
import com.vvalentim.server.exceptions.RequestTypeNotSupported;
import com.vvalentim.server.exceptions.UnprocessableContentException;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.response.ResponsePayload;

public class MessageParser {
    private static final ObjectMapper mapper = new ObjectMapper();

    public RequestPayload deserialize(String message) throws RequestTypeNotSupported, UnprocessableContentException, InvalidPayloadFieldType {
        RequestType requestType = null;

        try {
            ObjectReader reader = MessageParser.mapper.reader();
            JsonNode root = reader.readTree(message);
            JsonNodeType requestNodeType = root.get("operacao").getNodeType();
            String requestTypeText;

            if (requestNodeType != JsonNodeType.STRING) {
                throw new NullPointerException();
            }

            requestTypeText = root.get("operacao").asText();
            requestType = RequestType.getFromKey(requestTypeText);

            if (requestType == null) {
                throw new RequestTypeNotSupported(requestTypeText);
            }

            return (RequestPayload) MessageParser.mapper.convertValue(root, requestType.payloadType);
        } catch (JsonProcessingException | NullPointerException e) {
            throw new UnprocessableContentException();
        } catch (IllegalArgumentException e) {
            assert requestType != null;

            throw new InvalidPayloadFieldType(requestType.jsonKey);
        }
    }

    public String serialize(ResponsePayload responsePayload) {
        try {
            ObjectWriter writer = MessageParser.mapper.writer();

            return writer.writeValueAsString(responsePayload);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
