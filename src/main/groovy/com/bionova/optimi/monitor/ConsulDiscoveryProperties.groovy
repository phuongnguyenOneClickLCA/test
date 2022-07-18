package com.bionova.optimi.monitor


class ConsulDiscoveryProperties {
    private List<String> tags
    private String healthCheckPath
    private String healthCheckInterval
    private String healthCheckTimeout
    private String healthCheckCriticalTimeout
    private String scheme
    private boolean preferIpAddress = true

    List<String> getTags() {
        return tags
    }

    void setTags(List<String> tags) {
        this.tags = tags
    }

    String getHealthCheckPath() {
        return healthCheckPath
    }

    void setHealthCheckPath(String healthCheckPath) {
        this.healthCheckPath = healthCheckPath
    }

    String getHealthCheckInterval() {
        return healthCheckInterval
    }

    void setHealthCheckInterval(String healthCheckInterval) {
        this.healthCheckInterval = healthCheckInterval
    }

    String getHealthCheckTimeout() {
        return healthCheckTimeout
    }

    void setHealthCheckTimeout(String healthCheckTimeout) {
        this.healthCheckTimeout = healthCheckTimeout
    }

    String getHealthCheckCriticalTimeout() {
        return healthCheckCriticalTimeout
    }

    void setHealthCheckCriticalTimeout(String healthCheckCriticalTimeout) {
        this.healthCheckCriticalTimeout = healthCheckCriticalTimeout
    }

    String getScheme() {
        return scheme
    }

    void setScheme(String scheme) {
        this.scheme = scheme
    }

    boolean isPreferIpAddress() {
        return preferIpAddress
    }

    void setPreferIpAddress(boolean preferIpAddress) {
        this.preferIpAddress = preferIpAddress
    }

    @Override
    String toString() {
        final StringBuffer sb = new StringBuffer("ConsulDiscoveryProperties{")
        sb.append("tags=").append(tags)
        sb.append(", healthCheckPath='").append(healthCheckPath).append('\'')
        sb.append(", healthCheckInterval='").append(healthCheckInterval).append('\'')
        sb.append(", healthCheckTimeout='").append(healthCheckTimeout).append('\'')
        sb.append(", healthCheckCriticalTimeout='").append(healthCheckCriticalTimeout).append('\'')
        sb.append(", scheme='").append(scheme).append('\'')
        sb.append(", preferIpAddress=").append(preferIpAddress)
        sb.append('}')
        return sb.toString()
    }
}
