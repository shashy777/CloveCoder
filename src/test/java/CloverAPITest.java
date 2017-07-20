
import com.clove.clovewerable.ClovewearableRestURIConstants;
import com.clove.clovewerable.RegistrationResultBean;
import com.clove.clovewerable.RegistrationValidationBean;
import com.clove.clovewerable.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 * @author unascribed
 */
public class CloverAPITest {

    private final static String CLOVER_URL = "http://localhost:8081/Clovewerable";

    private static final String TEST_USER_ID = "user.id1";

    public static void main(String[] args) {

        CloverAPITest.testRegisterUser();
        CloverAPITest.testGetRegisteredUser();
        CloverAPITest.testCheck_RegistrationExpired();
        CloverAPITest.testCheck_Registration_notExpired();
    }

    private static void testRegisterUser() {

        RestTemplate restTemplate = new RestTemplate();
        User user = new User();
        user.setName("user1");
        user.setEmail("email");
        user.setPincode("00000");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        RegistrationResultBean response = restTemplate.postForObject(CLOVER_URL + ClovewearableRestURIConstants.CREATE_REG, user, RegistrationResultBean.class);

        System.out.println("==================================================");
        System.out.println("Registration response (SUCESS >>>>>>>>>> )= " + response.getResult());
        System.out.println("==================================================");
    }

    private static void testGetRegisteredUser() {

        RestTemplate restTemplate = new RestTemplate();
        User user = restTemplate.getForObject((CLOVER_URL + "/rest/clovewearable/getRegisteredUser/" + TEST_USER_ID), User.class);

        // JSON OBJECT
        ObjectMapper mapper = new ObjectMapper();
        try {
            String userAsJSONStr = mapper.writeValueAsString(user);
            System.out.println("==================================================");
            System.out.println("REGISTERD USER DETAILS >>>>>>>>>>>>> : " + userAsJSONStr);
            System.out.println("==================================================");
        } catch (JsonProcessingException ex) {
            Logger.getLogger(CloverAPITest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void testCheck_Registration_notExpired() {

        RestTemplate restTemplate = new RestTemplate();

        // This is the url obtained in the email.
        String regToken = DateTime.now().getMillisOfSecond() + "";

        RegistrationValidationBean rvb = new RegistrationValidationBean();
        rvb.setUserId(TEST_USER_ID);
        rvb.setExpToken(regToken);

        User user = restTemplate.postForObject((CLOVER_URL + ClovewearableRestURIConstants.VALIDATE_REG_USER), rvb, User.class);

        ObjectMapper mapper = new ObjectMapper();
        try {
            String userAsJSONStr = mapper.writeValueAsString(user);
            System.out.println("==================================================");
            System.out.println("Json object [LINK *NOT* EXPIRED >>>>>>>>>>> ]: " + userAsJSONStr);
            System.out.println("==================================================");
        } catch (JsonProcessingException ex) {
            Logger.getLogger(CloverAPITest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void testCheck_RegistrationExpired() {

        RestTemplate restTemplate = new RestTemplate();

        // This is the url obtained in the email.
        String regToken = DateTime.now().getMillisOfSecond() + "";

        RegistrationValidationBean rvb = new RegistrationValidationBean();
        rvb.setUserId(TEST_USER_ID + "2");
        rvb.setExpToken(regToken);

        User user = restTemplate.postForObject((CLOVER_URL + ClovewearableRestURIConstants.VALIDATE_REG_USER), rvb, User.class);

        ObjectMapper mapper = new ObjectMapper();
        try {
            String userAsJSONStr = mapper.writeValueAsString(user);
            System.out.println("==================================================");
            System.out.println("Json object [LINK EXPIRED >>>>>>>>>>> ]: " + userAsJSONStr);
            System.out.println("==================================================");
        } catch (JsonProcessingException ex) {
            Logger.getLogger(CloverAPITest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
