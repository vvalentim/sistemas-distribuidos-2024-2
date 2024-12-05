package com.vvalentim.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.vvalentim.exceptions.RequestTypeNotSupported;
import com.vvalentim.exceptions.UnprocessableContentException;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.response.ResponsePayload;

public class MessageParser {
    private static final ObjectMapper mapper = new ObjectMapper();

    public RequestPayload deserialize(String message) throws RequestTypeNotSupported, UnprocessableContentException {
        try {
            ObjectReader reader = MessageParser.mapper.reader();
            JsonNode root = reader.readTree(message);
            RequestType requestType = null;

            String key = root.get("operacao").asText();

            requestType = RequestType.getFromKey(key);

            if (requestType == null) {
                throw new RequestTypeNotSupported("Request type '" + key + "' not found.", key);
            }

            return (RequestPayload) MessageParser.mapper.convertValue(root, requestType.payloadType);
        } catch (JsonProcessingException | NullPointerException e) {
            throw new UnprocessableContentException();
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
