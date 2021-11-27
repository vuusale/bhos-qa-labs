package com.example.springproj8;

import com.nimbusds.srp6.SRP6CryptoParams;
import com.nimbusds.srp6.SRP6Exception;
import com.nimbusds.srp6.SRP6ServerSession;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    public static SRP6CryptoParams config;
    public static SRP6ServerSession serverSession;

    private static final Map<String, Map> database = new HashMap<>() {
        {
            put("vuusale", new HashMap<String, BigInteger>() {{
                put("salt", new BigInteger("1031322761846613224834536"));
                put("verifier", new BigInteger("2510174848470831614633167459448252804347764498751046077923431241562915250457436529316888461164208599573927059863638499424874945406214587637961689657442699"));
            }});
        }
    };

    public String step1(String username) {
        if (!database.containsKey(username)) {
            return "";
        }
        Map user = database.get(username);
        config = SRP6CryptoParams.getInstance();
        serverSession = new SRP6ServerSession(config);
        return serverSession.step1(username, (BigInteger) user.get("salt"), (BigInteger) user.get("verifier")).toString();
    }

    public String step2(BigInteger A, BigInteger M1) throws SRP6Exception {
        try{
            return serverSession.step2(A, M1).toString();
        } catch (SRP6Exception e) {
            return "";
        }
    }
}
