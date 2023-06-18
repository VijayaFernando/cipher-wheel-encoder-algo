package com.vijaya.cipherwheelencoderalgo.controller;

import com.vijaya.cipherwheelencoderalgo.domain.EncodedMessageResponse;
import com.vijaya.cipherwheelencoderalgo.domain.EncoderRequest;
import com.vijaya.cipherwheelencoderalgo.service.CipherWheelEncoderService;
import com.vijaya.cipherwheelencoderalgo.util.Keywords;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.logging.LoggerFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CipherWheelEncoderControllerTest {

    @Mock
    private CipherWheelEncoderService cipherWheelEncoderService;
    @InjectMocks
    private CipherWheelEncoderController cipherWheelEncoderController;

    @ParameterizedTest
    @ValueSource(strings = {"","  ", "    "})
    void encodePlainText_WithEmptyPlainText_ShouldDisplayBadRequest() {

        // Arrange
        EncoderRequest encoderRequest = new EncoderRequest();
        encoderRequest.setKey(10);
        encoderRequest.setPlainMessage("");

        // Expected
        EncodedMessageResponse expectedEncodedMessageResponse = new EncodedMessageResponse();
        expectedEncodedMessageResponse.setStatus(Keywords.INVALID_PLAIN_TEXT);

        // Act
        ResponseEntity<EncodedMessageResponse> responseEntity = cipherWheelEncoderController.encodePlainText(encoderRequest);

        // Assert
        assertEquals(expectedEncodedMessageResponse.getStatus(), Objects.requireNonNull(responseEntity.getBody()).getStatus());
        assertNull(responseEntity.getBody().getEncodedMessage());
        verify(cipherWheelEncoderService, times(0)).encodeMessage(encoderRequest);
    }

    @Test
    void encodePlainText_WithNullPlainText_ShouldDisplayBadRequest() {

        // Arrange
        EncoderRequest encoderRequest = new EncoderRequest();
        encoderRequest.setKey(10);
        encoderRequest.setPlainMessage(null);

        // Expected
        EncodedMessageResponse expectedEncodedMessageResponse = new EncodedMessageResponse();
        expectedEncodedMessageResponse.setStatus(Keywords.INVALID_PLAIN_TEXT);

        // Act
        ResponseEntity<EncodedMessageResponse> responseEntity = cipherWheelEncoderController.encodePlainText(encoderRequest);

        // Assert
        assertEquals(expectedEncodedMessageResponse.getStatus(), Objects.requireNonNull(responseEntity.getBody()).getStatus());
        assertNull(responseEntity.getBody().getEncodedMessage());
        verify(cipherWheelEncoderService, times(0)).encodeMessage(encoderRequest);
    }

    @Test
    void encodePlainText_WithNullCipherWheel_ShouldDisplayBadRequest() {

        // Arrange
        EncoderRequest encoderRequest = new EncoderRequest();
        encoderRequest.setKey(10);
        encoderRequest.setPlainMessage("Hey");
        encoderRequest.setCipherWheel(null);

        // Expected
        EncodedMessageResponse expectedEncodedMessageResponse = new EncodedMessageResponse();
        expectedEncodedMessageResponse.setStatus(Keywords.INVALID_CIPHER_WHEEL);

        // Act
        ResponseEntity<EncodedMessageResponse> responseEntity = cipherWheelEncoderController.encodePlainText(encoderRequest);

        // Assert
        assertEquals(expectedEncodedMessageResponse.getStatus(), Objects.requireNonNull(responseEntity.getBody()).getStatus());
        assertNull(responseEntity.getBody().getEncodedMessage());
        verify(cipherWheelEncoderService, times(0)).encodeMessage(encoderRequest);
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "", "      "})
    void encodePlainText_WithEmptyCipherWheel_ShouldDisplayBadRequest() {

        // Arrange
        EncoderRequest encoderRequest = new EncoderRequest();
        encoderRequest.setKey(10);
        encoderRequest.setPlainMessage("Hey");
        encoderRequest.setCipherWheel(null);

        // Expected
        EncodedMessageResponse expectedEncodedMessageResponse = new EncodedMessageResponse();
        expectedEncodedMessageResponse.setStatus(Keywords.INVALID_CIPHER_WHEEL);

        // Act
        ResponseEntity<EncodedMessageResponse> responseEntity = cipherWheelEncoderController.encodePlainText(encoderRequest);

        // Assert
        assertEquals(expectedEncodedMessageResponse.getStatus(), Objects.requireNonNull(responseEntity.getBody()).getStatus());
        assertNull(responseEntity.getBody().getEncodedMessage());
        verify(cipherWheelEncoderService, times(0)).encodeMessage(encoderRequest);
    }

    @Test
    void encodePlainText_WithKeyAsZero_ShouldDisplayWarning() {

        // Arrange
        EncoderRequest encoderRequest = new EncoderRequest();
        encoderRequest.setKey(0);
        encoderRequest.setPlainMessage("Hey");
        encoderRequest.setCipherWheel("ABC");

        // Expected
        EncodedMessageResponse expectedEncodedMessageResponse = new EncodedMessageResponse();
        expectedEncodedMessageResponse.setStatus(Keywords.KEY_AS_ZERO);

        // Act
        ResponseEntity<EncodedMessageResponse> responseEntity = cipherWheelEncoderController.encodePlainText(encoderRequest);

        // Assert
        assertEquals(expectedEncodedMessageResponse.getStatus(), Objects.requireNonNull(responseEntity.getBody()).getStatus());
        assertEquals(encoderRequest.getPlainMessage(), responseEntity.getBody().getEncodedMessage());
        verify(cipherWheelEncoderService, times(0)).encodeMessage(encoderRequest);
    }

    @ParameterizedTest
    @CsvSource({
            "Hello World!, 3, ABC",
            "OpenAI, 5, ABD"
    })
    void encodePlainText_WithValidRequest_ShouldEncoded(String plainText, int key, String cipherWheel){

        // Arrange
        EncoderRequest encoderRequest = new EncoderRequest();
        encoderRequest.setKey(key);
        encoderRequest.setPlainMessage(plainText);
        encoderRequest.setCipherWheel(cipherWheel);

        // Act
        cipherWheelEncoderController.encodePlainText(encoderRequest);

        // Assert
        verify(cipherWheelEncoderService, times(1)).encodeMessage(encoderRequest);
    }
}
