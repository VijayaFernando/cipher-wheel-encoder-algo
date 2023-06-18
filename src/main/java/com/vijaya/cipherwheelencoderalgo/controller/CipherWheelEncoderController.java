package com.vijaya.cipherwheelencoderalgo.controller;

import com.vijaya.cipherwheelencoderalgo.domain.EncodedMessageResponse;
import com.vijaya.cipherwheelencoderalgo.domain.EncoderRequest;
import com.vijaya.cipherwheelencoderalgo.service.CipherWheelEncoderService;
import com.vijaya.cipherwheelencoderalgo.util.Keywords;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CipherWheelEncoderController {

    private final CipherWheelEncoderService cipherWheelEncoderService;

    @GetMapping("/encode")
    public ResponseEntity<EncodedMessageResponse> encodePlainText(@RequestBody EncoderRequest encoderRequest) {

        log.info("CipherWheelEncoderController - encodePlainText method, Request : {}", encoderRequest.toString());

        //region Validations
        if (Objects.isNull(encoderRequest.getPlainMessage()) || encoderRequest.getPlainMessage().isEmpty()) {
            log.error("Invalid Plain Text.");
            EncodedMessageResponse encodedMessageResponse = new EncodedMessageResponse();
            encodedMessageResponse.setStatus(Keywords.INVALID_PLAIN_TEXT);
            return new ResponseEntity<>(encodedMessageResponse, HttpStatus.BAD_REQUEST);
        }

        if (Objects.isNull(encoderRequest.getCipherWheel()) || encoderRequest.getCipherWheel().isEmpty()) {
            log.error("Invalid Cipher");
            EncodedMessageResponse encodedMessageResponse = new EncodedMessageResponse();
            encodedMessageResponse.setStatus(Keywords.INVALID_CIPHER_WHEEL);
            return new ResponseEntity<>(encodedMessageResponse, HttpStatus.BAD_REQUEST);
        }

        if (encoderRequest.getKey() == 0) {
            log.warn("key is zero");
            EncodedMessageResponse encodedMessageResponse = new EncodedMessageResponse();
            encodedMessageResponse.setEncodedMessage(encoderRequest.getPlainMessage());
            encodedMessageResponse.setStatus(Keywords.KEY_AS_ZERO);

            return new ResponseEntity<>(encodedMessageResponse, HttpStatus.OK);
        }
        //endregion Validations

        EncodedMessageResponse encodedMessageResponse = cipherWheelEncoderService.encodeMessage(encoderRequest);
        log.info("CipherWheelEncoderController - encodePlainText method, Response : {}", encodedMessageResponse.toString());
        return new ResponseEntity<>(encodedMessageResponse, HttpStatus.OK);
    }
}
