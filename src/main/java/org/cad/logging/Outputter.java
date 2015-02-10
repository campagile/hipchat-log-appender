package org.cad.logging;

import java.io.IOException;

public interface Outputter {

    void write(String output) throws IOException;
}
