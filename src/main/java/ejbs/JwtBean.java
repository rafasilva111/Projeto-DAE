package ejbs;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSASSASigner;
import javax.ejb.Stateless;
@Stateless(name = "JwtEJB")
public class JwtBean {
    static {
        FileInputStream fis = null;
        char[] password = "jwtsecret".toCharArray();
        String alias = "alias";
        PrivateKey pk = null;
        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            String configDir = System.getProperty("jboss.server.config.dir");

            // check .env
            String keystorePath = configDir + File.separator + "jwt.keystore";
            fis = new FileInputStream(keystorePath);
            ks.load(fis, password);
            Key key = ks.getKey(alias, password);
            if (key instanceof PrivateKey) {
                pk = (PrivateKey) key;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {}
            }
        }
        privateKey = pk;
    }
    private static final PrivateKey privateKey;
    private static final int TOKEN_VALIDITY = 14400;
    private static final String CLAIM_ROLES = "groups";
    private static final String ISSUER = "quickstart-jwt-issuer"; // check .env
    private static final String AUDIENCE = "jwt-audience"; // check .env
    public String createJwt(final String subject, final String[] roles) throws
            Exception {
        JWSSigner signer = new RSASSASigner(privateKey);
        JsonArrayBuilder rolesBuilder = Json.createArrayBuilder();
        for (String role : roles) { rolesBuilder.add(role); }
        JsonObjectBuilder claimsBuilder = Json.createObjectBuilder()
                .add("sub", subject)
                .add("iss", ISSUER)
                .add("aud", AUDIENCE)
                .add(CLAIM_ROLES, rolesBuilder.build())
                .add("exp", ((System.currentTimeMillis() / 1000) +
                        TOKEN_VALIDITY));
        JWSObject jwsObject = new JWSObject(new
                JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(new JOSEObjectType("jwt")).build(),
                new Payload(claimsBuilder.build().toString()));
        jwsObject.sign(signer);
        return jwsObject.serialize();
    }
}
