package com.hyundai.eventlogging2;

import java.io.IOException;

import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PropertySource("file:config/config.properties")
public class WebhookController {

    Config config = Config.getConfig();

    // @GetMapping("/**")
    // public ResponseEntity<String> blockGETRequest() {
    //     log.warn("There is no service for GET method.");
    //     return new ResponseEntity<>("There is no service for GET method.", HttpStatus.METHOD_NOT_ALLOWED);
    // }

    @PostMapping("${webhook.cloud.server.uri:webhook_cloud}")
    public ResponseEntity<String> cloudWebhook(@RequestBody WebhookDto dto) throws IOException {
        dto.nullReplace(dto);
        FilePrinterCloud printer = new FilePrinterCloud();
        printer.print(dto);
        return new ResponseEntity<>("Receive Complete.", HttpStatus.OK);
    }

    @PostMapping("${webhook.api.server.uri:webhook_api}")
    public ResponseEntity<String> apiWebhook(@RequestBody WebhookDto dto) throws IOException {
        dto.nullReplace(dto);
        FilePrinterApi printer = new FilePrinterApi();
        printer.print(dto);
        return new ResponseEntity<>("Receive Complete.", HttpStatus.OK);
    }
}
