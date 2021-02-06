package demo.rt.tools.jmx.properties;

import lombok.Data;

@Data
public class ObjectName {

    private String canonicalName;
    private boolean pattern;
    private boolean domainPattern;
    private boolean propertyPattern;
    private boolean propertyListPattern;
    private boolean propertyValuePattern;
    private String domain;
    private KeyPropertyList keyPropertyList;
    private String keyPropertyListString;
    private String canonicalKeyPropertyListString;

    @Data
    public static class KeyPropertyList {
        private String type;
    }
}
