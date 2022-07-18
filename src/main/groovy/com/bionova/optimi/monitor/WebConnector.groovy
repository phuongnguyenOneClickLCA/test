package com.bionova.optimi.monitor


class WebConnector {
    private String scheme
    private Integer port

    WebConnector(String scheme, Integer port) {
        this.scheme = scheme
        this.port = port
    }

    String getScheme() {
        return scheme
    }

    Integer getPort() {
        return port
    }

    @Override
    String toString() {
        final StringBuffer sb = new StringBuffer()
        sb.append("Scheme '").append(scheme).append("', port '").append(port).append('\'')
        return sb.toString()
    }
}
