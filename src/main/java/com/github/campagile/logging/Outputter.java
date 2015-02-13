package com.github.campagile.logging;

import java.io.IOException;

public interface Outputter {

    void write(String output) throws IOException;
}
