package com.bionova.optimi.util.monitor

enum ConsulTag {
    CONTEXT_PATH("contextPath"),
    SECURE("secure"),
    VERSION("version"),
    STARTED_AT("startedAt")

    private final String name

    private ConsulTag(String name) {
        this.name = name
    }

    String getName() {
        return this.name
    }

    String asTag(String value) {
        return String.format("%s=%s", this.name, value)
    }
}