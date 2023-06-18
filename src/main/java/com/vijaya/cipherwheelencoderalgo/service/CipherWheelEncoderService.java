package com.vijaya.cipherwheelencoderalgo.service;

import com.vijaya.cipherwheelencoderalgo.domain.EncodedMessageResponse;
import com.vijaya.cipherwheelencoderalgo.domain.EncoderRequest;
import com.vijaya.cipherwheelencoderalgo.util.Keywords;
import org.springframework.stereotype.Service;

@Service
public class CipherWheelEncoderService {
    public EncodedMessageResponse encodeMessage(EncoderRequest encoderRequest) {
        StringBuilder encodedMessageText = new StringBuilder();

        for (char ch : encoderRequest.getPlainMessage().toCharArray()) {

            if (Character.isLetter(ch)) {
                char encodedChar = encryptionProcess(ch, encoderRequest.getKey(), encoderRequest.getCipherWheel());
                encodedMessageText.append(encodedChar);

            } else {
                encodedMessageText.append(ch);
            }
        }

        EncodedMessageResponse encodedMessageResponse = new EncodedMessageResponse();
        encodedMessageResponse.setEncodedMessage(encodedMessageText.toString());
        encodedMessageResponse.setStatus(Keywords.SUCCESS);

        return encodedMessageResponse;
    }

    private char encryptionProcess(char ch, int key, String cipherWheel) {

        char upperCh = Character.toUpperCase(ch);
        int position = upperCh - 'A'; //determine the letter position

        int encodedPosition = (position + key) % cipherWheel.length(); //or 26 for english alphabet
        char encodedChar = cipherWheel.charAt(encodedPosition);

        return Character.isLowerCase(ch) ? Character.toLowerCase(encodedChar) : encodedChar;
    }
}
