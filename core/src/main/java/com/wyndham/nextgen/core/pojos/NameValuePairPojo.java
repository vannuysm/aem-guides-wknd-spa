package com.wyndham.nextgen.core.pojos;

/**
 *
 * The type Name value pair pojo.
 */
public class NameValuePairPojo {

    /**
     * The Name.
     */
    String name;
    /**
     * The Value.
     */
    String value;

    boolean isDefault;
    /**
     * Instantiates a new Name value pair pojo.
     *
     * @param name  the name
     * @param value the value
     */
    public NameValuePairPojo(String name, String value, boolean isDefault) {
        this.name = name;
        this.value = value;
        this.isDefault = isDefault;
    }

    public NameValuePairPojo(){}

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(String value) {
        this.value = value;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

}
