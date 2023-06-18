package com.vijaya.cipherwheelencoderalgo.domain;

import lombok.Data;

@Data
public class EncoderRequest {
    private int key;
    private String plainMessage;
    private String cipherWheel;
}
