package com.lhind.util;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ExcelDownloadableResponse {
    public static ResponseEntity<Resource> excel(String fileName, Resource body) {
        return media(fileName, body, MediaType.APPLICATION_OCTET_STREAM, ".xlsx");
    }

    private static ResponseEntity<Resource> media(String fileName, Resource body, MediaType type, String fileType) {
        return ResponseEntity
                .ok()
                .contentType(type)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + fileType + "\"")
                .body(body);
    }
}
