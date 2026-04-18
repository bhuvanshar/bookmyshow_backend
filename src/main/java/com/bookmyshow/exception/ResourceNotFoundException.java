package com.bookmyshow.exception; public class ResourceNotFoundException extends RuntimeException {
    private final String resourceName;
    private final String fieldName;
    private final Object fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public String getResourceName() { return this.resourceName; }
    public String getFieldName() { return this.fieldName; }
    public Object getFieldValue() { return this.fieldValue; }

}
