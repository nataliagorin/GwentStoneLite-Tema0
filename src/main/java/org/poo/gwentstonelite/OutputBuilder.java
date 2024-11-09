package org.poo.gwentstonelite;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public final class OutputBuilder {
    private ArrayNode output;
    private ObjectMapper objMapper;

    public OutputBuilder(final ArrayNode output) {
        this.output = output;
        this.objMapper = new ObjectMapper();
    }
}
