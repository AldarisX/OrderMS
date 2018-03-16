package com.everygamer.service.expection;

public class DataCheckExpection extends RuntimeException {
    public DataCheckExpection(String message) {
        super(message);
    }
}
