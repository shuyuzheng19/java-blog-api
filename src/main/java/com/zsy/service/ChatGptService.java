package com.zsy.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zsy.exception.CustomException;
import com.zsy.exception.ErrorEnum;
import com.zsy.model.request.GptRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author 郑书宇
 * @create 2023/8/31 10:54
 * @desc
 */
@Service
@Slf4j
public class ChatGptService {

    @Value("${gpt.token}")
    private String TOKEN;

    @Value("${gpt.model}")
    private String MODEL;

    private static final String CHAT_GPT_API="https://ai.fakeopen.com/api/conversation";

    public void resetToken(String token){
        this.TOKEN=token;
    }

    public SseEmitter chat(String message) throws IOException {

        GptRequest request = GptRequest.of(Arrays.asList(message));

        request.setModel(MODEL);

        URL url = new URL(CHAT_GPT_API);

        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection(new Proxy(Proxy.Type.HTTP,new InetSocketAddress("127.0.0.1",10809)));

        connection.setReadTimeout(5000);

        connection.setConnectTimeout(5000);

        connection.setRequestMethod("POST");

        connection.addRequestProperty("Content-Type","application/json;charset=utf-8");

        connection.addRequestProperty("host","ai.fakeopen.com");

        connection.addRequestProperty("Authorization","Bearer "+TOKEN);

        connection.setDoOutput(true);

        new ObjectMapper().writeValue(connection.getOutputStream(),request);

        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        new Thread(() -> {
            try (BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {

                    String data= null;

                    try{
                        data=line.split("data: ")[1].trim();
                        emitter.send(data);
                    }catch (ArrayIndexOutOfBoundsException e){
                        continue;
                    }
                }
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        }).start();

        log.info("向GPT提问: {}",message);

        return emitter;
    }
}
