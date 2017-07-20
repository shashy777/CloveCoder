package com.clove.clovewerable;

import java.io.Serializable;

/**
 * @author unascribed
 */
public class RegistrationValidationBean implements Serializable {

    private static final long serialVersionUID = 3812850972631747663L;

    private String expToken;

    private String userId;

    public String getExpToken() {
        return expToken;
    }

    public void setExpToken(String expToken) {
        this.expToken = expToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
