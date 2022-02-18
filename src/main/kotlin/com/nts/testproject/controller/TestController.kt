package com.nts.testproject.controller

import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

@RestController
class TestController {

    @PostMapping("/test")
    fun fileUpload(@RequestParam("fileList") fileList: List<MultipartFile>,
                   @RequestParam("fileTypeList") fileTypeList: List<String>,
                   @RequestParam merchantId: String): String {

        println("[TEST] name: ${fileList[0].name}")
        println("[TEST] originName : ${fileList[0].originalFilename}")
        println("[TEST] fileType: ${fileTypeList[0]}")
        println("[TEST] merchantId: $merchantId")

        return "Success"
    }

    @PostMapping("/pass")
    fun pass(
        @RequestParam("fileList") fileList: List<MultipartFile>,
        @RequestParam("fileTypeList") fileTypeList: List<String>,
        @RequestParam merchantId: String
    ): String {

        val multipartDataBuilder = MultipartBodyBuilder()

        for (file in fileList) {
            val multipartFile = MockMultipartFile(
                file.name,
                file.originalFilename?.replace("?", "/")?.replace(">", "."),
                "image/jpeg",
                file.inputStream
            )

            multipartDataBuilder.part("fileList", multipartFile.resource)
        }

        for (fileType in fileTypeList) {
            multipartDataBuilder.part("fileTypeList", fileType)
        }

        multipartDataBuilder.part("merchantId", merchantId)

        val multipartData = multipartDataBuilder.build()

        val result = WebClient.builder()
            .build()
            .post()
            .uri("http://admin-partner-pay.linecorp-rc.com/v1/line-man/upload")
            .header("Content-Type", "multipart/form-data")
            .body(BodyInserters.fromMultipartData(multipartData))
            .retrieve()
            .bodyToMono(String::class.java)
            .block()

        return "[TEST] result: $result"
    }
}
