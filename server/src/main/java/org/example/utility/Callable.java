package org.example.utility;

import org.example.main.responce.Response;

public interface Callable {
    Response calling(String args[], String v);
}
