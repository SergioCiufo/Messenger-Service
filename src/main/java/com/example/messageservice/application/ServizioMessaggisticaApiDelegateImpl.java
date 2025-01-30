package com.example.messageservice.application;

import com.example.messageService.generated.application.api.ServizioMessaggisticaApiDelegate;
import com.example.messageService.generated.application.model.RetrieveMessages200ResponseInner;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ServizioMessaggisticaApiDelegateImpl implements ServizioMessaggisticaApiDelegate {
    @Override
    public ResponseEntity<List<RetrieveMessages200ResponseInner>> retrieveMessages() {
        return ResponseEntity.ok(new ArrayList<RetrieveMessages200ResponseInner>());
    }
}
