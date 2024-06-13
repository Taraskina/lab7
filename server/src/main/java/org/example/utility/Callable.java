package org.example.utility;

import org.example.main.Response;

public interface Callable {
    Response calling(String args[], String v);
}
