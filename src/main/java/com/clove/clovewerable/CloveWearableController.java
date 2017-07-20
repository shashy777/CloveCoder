package com.clove.clovewerable;

import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author unascribed
 */
@Controller
public class CloveWearableController {

    /**
     * Register User Service
     *
     * @param user
     * @return
     */
    @RequestMapping(value = ClovewearableRestURIConstants.CREATE_REG, method = RequestMethod.POST)
    public @ResponseBody
    RegistrationResultBean registerUser(@RequestBody User user) {

        RegistrationResultBean toReturn = new RegistrationResultBean();

        System.err.println("regUser ------ " + user.toString());
        // make your Db connection here, to save user and get userid

        // MOCK RESULT.
        if (user.getName().equals("user1")) {
            toReturn.setResult("user1.id");
        } else {
            toReturn.setResult("FAILURE");
        }

        return toReturn;
    }

    /**
     * Fetch registered User by loginId Service
     *
     * @param userLoginId
     * @return
     */
    @RequestMapping(value = ClovewearableRestURIConstants.GET_REG_USER, method = RequestMethod.GET)
    public @ResponseBody
    User getRegisteredUser(@PathVariable("loginid") String userLoginId) {

        System.err.println("Fetch user by  ID=" + userLoginId);

        /**
         * Fetch User details from the DB by "loginId"
         */
        // MOCK USER
        User usr = new User();
        createDummyUser(usr);
        usr.setRegToken(generateTokenAndSaveToDB());

        /**
         * To send an email, user Java mail API, or spring templates.
         */
        return usr;
    }

    /**
     * Check link expiration service
     *
     * @param registrationValidationBean
     * @return
     */
    @RequestMapping(value = ClovewearableRestURIConstants.VALIDATE_REG_USER, method = RequestMethod.POST)
    public @ResponseBody
    User checkUserRegExpirationLink(@RequestBody RegistrationValidationBean registrationValidationBean) {

        System.err.println("Userid =" + registrationValidationBean.getUserId() + " - Token " + registrationValidationBean.getExpToken());

        /**
         * Compare the Token (timestamp parsed) obtained from the request, with that actual stored in the DB (to make
         * sure it is not hijacked) DB should return a 'expired or not' boolean value !
         */
        User toReturn = new User();

        // MOCK RESULT, use userid to toggle result, for testing purpose
        Boolean isExpired = !registrationValidationBean.getUserId().equalsIgnoreCase("user.id1");
        if (!isExpired) {
            createDummyUser(toReturn);
        } else {
            toReturn.setErrMsg("FAILURE, Link Expired");
        }
        return toReturn;
    }

    private void createDummyUser(User toReturn) {

        // Fetch User details from the DB
        toReturn.setLoginId("user.id1");
        toReturn.setName("D USER");
        toReturn.setEmail("email");
        toReturn.setPincode("999999");
    }

    private String generateTokenAndSaveToDB() {

        String expToken = DateTime.now().getMillisOfSecond() + "";
        /**
         * Save the token to the Db with the required expiration time (15 min in the case)
         */
        return expToken;
    }
}
