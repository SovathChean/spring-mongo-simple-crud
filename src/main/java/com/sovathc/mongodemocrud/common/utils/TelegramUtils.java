package com.sovathc.mongodemocrud.common.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sovathc.mongodemocrud.user.web.vo.request.KhQrCallbackRequestVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
@AllArgsConstructor
public class TelegramUtils {
    private final RestTemplate restTemplate;
    private static final String TELEGRAM_ENDPOINT = "https://api.telegram.org/bot";
    private static final String SEND_MESSAGE = "sendMessage";
    private static final String PARSE_MODE = "MarkDown";
    private static final String TOKEN = "5937346031:AAEn6tnp4e5vMG1dJsYYgC2nNDu1EadbzLw";
    private static final String CHAT_ID = "-1001647159760";

    public void sendMessage(KhQrCallbackRequestVO request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        String reqMsg = mapper.writeValueAsString(request);
        String message = String.format("%s", "*KHQR payment callback*\r\n") +
                "Order Reference:             " + request.getOrderReferenceNo() + "\r\n" +
                "Transaction Id:                 " + request.getTransactionId() + "\r\n" +
                "Amount:                            " + request.getAmount() + "\r\n" +
                "Request Body:\n" + reqMsg + "\r\n";

        this.requestRestClient(message);
    }

    public void sendFailMessage(String errors, String content) {
        String message = String.format("%s", "*KHQR payment callback exception!*\r\n") +
                "Response Body:\t" + errors + "\r\n" +
                "Content:\n" + content + "\r\n";

        this.requestRestClient(message);
    }

    private void requestRestClient(String message) {
        try {
            MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
            queryParams.set("chat_id", CHAT_ID);
            queryParams.set("parse_mode", PARSE_MODE);
            queryParams.set("text", message);
            String url = String.format("%s%s/%s", TELEGRAM_ENDPOINT, TOKEN, SEND_MESSAGE);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<?> requestHttpEntity = new HttpEntity<>(headers);
            UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).queryParams(queryParams).build().encode();
            this.restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, requestHttpEntity, Object.class);
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
        }
    }
}
