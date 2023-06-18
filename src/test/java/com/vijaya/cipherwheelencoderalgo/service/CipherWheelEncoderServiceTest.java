package com.vijaya.cipherwheelencoderalgo.service;

import com.vijaya.cipherwheelencoderalgo.domain.EncodedMessageResponse;
import com.vijaya.cipherwheelencoderalgo.domain.EncoderRequest;
import com.vijaya.cipherwheelencoderalgo.util.Keywords;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CipherWheelEncoderServiceTest {

    @InjectMocks
    private CipherWheelEncoderService cipherWheelEncoderService;

    @ParameterizedTest
    @CsvSource({
            "Hello World!, 3, Khoor Zruog!",
            "OpenAI, 5, TujsFN"
    })
    void encodeMessage_WithEnglishAlphabet(String plainText, int key, String expectedEncodedMessage) {

        // Arrange
        EncoderRequest encoderRequest = new EncoderRequest();
        encoderRequest.setCipherWheel("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        encoderRequest.setKey(key);
        encoderRequest.setPlainMessage(plainText);

        // Act
        EncodedMessageResponse encodedMessageResponse = cipherWheelEncoderService.encodeMessage(encoderRequest);

        // Assert
        assertEquals(expectedEncodedMessage, encodedMessageResponse.getEncodedMessage());
        assertEquals(Keywords.SUCCESS, encodedMessageResponse.getStatus());
    }
}
