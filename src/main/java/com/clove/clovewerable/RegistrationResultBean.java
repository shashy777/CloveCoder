package com.clove.clovewerable;

import java.io.Serializable;

/**
 * @author unascribed
 */
public class RegistrationResultBean implements Serializable {

    private static final long serialVersionUID = -7788619177798333712L;

    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
