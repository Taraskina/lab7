package org.example.utility;

public interface PasswordDecoder {
    String bytesToString(byte[] bytes);
    boolean isExpectedPassword(byte[] password, byte[] salt, byte[] expectedHash);
}