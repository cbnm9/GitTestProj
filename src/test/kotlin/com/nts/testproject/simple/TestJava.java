package com.nts.testproject.simple;

import org.junit.jupiter.api.Test;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

public class TestJava {

    @Test
    public void testSomething() {

        MockMultipartFile file = new MockMultipartFile("fileList", "../../../../../../tmp/test.jpg",
                "image/jpeg", new byte[0]);

        MultipartBodyBuilder multipartDataBuilder = new MultipartBodyBuilder();

        multipartDataBuilder.part("fileList", file);
        multipartDataBuilder.part("fileTypeList", "FILETP001");
        multipartDataBuilder.part("merchantId", "LMM_20071312qa");

        String result = WebClient.builder().build()
                .post()
                .uri("https://igw-pay.line-apps-beta.com/merchant-api/v1/line-man/upload")
                .header("Content-Type", "multipart/form-data")
                .body(BodyInserters.fromMultipartData(multipartDataBuilder.build()))
                .retrieve()
                .bodyToMono(String.class)
            .block();

        System.out.println("[TEST] result: " + result);

        Assert.isTrue(true);
    }
}
