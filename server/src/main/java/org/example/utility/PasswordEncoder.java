package org.example.utility;

public interface PasswordEncoder {
    byte[] getSalt();
    byte[] hash(byte[] password, byte[] salt);
}