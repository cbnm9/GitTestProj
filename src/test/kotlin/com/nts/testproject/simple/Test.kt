package com.nts.testproject.simple

import org.junit.jupiter.api.Test
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

class Test {

    @Test
    fun test() {

        val file = MockMultipartFile("fileList", "../../../../../../tmp/test.jpg",
            "image/jpeg", ByteArray(0))

        val multipartDataBuilder = MultipartBodyBuilder()

        multipartDataBuilder.part("fileList", file)
        multipartDataBuilder.part("fileTypeList", "FILETP001")
        multipartDataBuilder.part("merchantId", "LMM_20071312qa")

        val result = WebClient.builder().build()
            .post()
            .uri("http://localhost:8080/test")
            .header("Content-Type", "multipart/form-data")
            .body(BodyInserters.fromMultipartData(multipartDataBuilder.build()))
            .retrieve()
            .bodyToMono(ByteArray::class.java)
            .block()

        print("[TEST] result: ${result.toString()}")
    }
}
