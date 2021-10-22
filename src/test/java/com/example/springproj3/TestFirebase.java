package com.example.springproj3;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestFirebase {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void testInsert() {
        Dummy dummy = new Dummy("random", "NoWhere", 123);
        HttpEntity<Dummy> insertRequest = new HttpEntity<>(dummy, headers);

        restTemplate.postForObject(
                createURLWithPort("/insertDummy"), insertRequest, String.class);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(createURLWithPort("/getDummy"))
                .queryParam("name", "random");

        HttpEntity<String> getRequest = new HttpEntity<>(headers);
        ResponseEntity<String> getResponse = restTemplate.exchange(
                builder.toUriString(), HttpMethod.GET, getRequest, String.class);
        String str = "{\"type\": \"service_account\",\"project_id\": \"bhos-qa-labs\",\"private_key_id\": \"71fbaceed637ad20a4e64046bee9a19168c38b8e\",\"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDABbf7lS3MNSGf\\nVmU+Q4eyjxbbCR/lG5e6+7W/7IUcLBte607BTBdarR8q33gRVs/D6pS+TI0VWsSg\\nWRZ+3RbjrdscXOOI+pH2FzE9grPYEw/kKrg2WNOm4nVJJtH35oi10gouGy7yhbQ6\\nCmgMlq7gsJ1esjSmsb//CvWPtO5gwTO4cDuN86sYsMUj9pQVdYVwMnNkIAVqmv6y\\n4S/+vfgF/KzGj0G5oOHYty6FnDjzePtrsK3cJOiYxuhVYIxA/UdoUPgOOee6RUpp\\nOsBnJRHOwWU4IxelbzzqQeVn1zfiNHGUFkco6jeDlQqwIfcLsb17Vgc/nOwcRXXl\\nGqclJwCbAgMBAAECggEAA063l+4xCaZe81y0+HdErdT/3NX70gbcRp8udgpce4pF\\nRFESi3bcEB8lXut38wcI0wJx6Tv089cbJFjCzjk8d+fpqnm3it7/SoUDU7FhRs6r\\nkD7CBfRb1fs0zi4uQw0uVulMvbHvOUrRwSkgGGugBdIf6op+ADOSHtVywSdY4UNq\\n+fHBO0p+GNvq3BP+Qva+IEZ5GL4nfM2D1OsKUcnML5qk9N3r8aMBTtLu106CUwoV\\nh/6fAvN3cIm44UONrUCSbbSq/utqcDKiir7gfL6C6ofMmWLWJe4bwMb1BXRvul0M\\nnKMTxUr6POpvmEAzQi7DqnvV5sNjunokeUq327aWwQKBgQD5xvu3ffa9y3IsjCMo\\nOkBZJQkjujvRQv7EAW9XEg/o1W+HclIj9guKSyy0hZV/XgX/LTppYjKXWEq3ktLA\\nj4n7IuCYUNjbwxuYfi4ecmkJpXyY6HB4zzBT/i5ncTS8feVg2sNWnIqYyKMCMi4Q\\nU77A+d9xRSukshP4Pl27jizBxwKBgQDEzmOQGGfYAxJm2HGvaTUd6CYirR3rxgi/\\n0qS5RhOjo+DO7cwRKhaJRMXr4qT1Y6fZ3/F61W8NyhrCReSkdNTcwFgm+I8lNsCc\\ndGti4J92f6QyHiwva8R+/l4vPJUVlrFc047QGCnfNUEfqK6LwI/JO5Wgj5YlC8Mn\\nssL2KZ2KjQKBgQDM1NE3+KSPoHdQ8Nsj8iEy+Zw1IFRNl5M1Sbf0v2sslVuQx790\\nniF4l41hvOqMOJ7SVFTCdzTlyBH92Pnbz9pniIKGZkZwcd//ffp0HX7l2kADZRpW\\n+BG5z4hebAfDS7r/Ymcub9F42ZBUDLKZ0RNtTXxfd0oDFcYqnXpghIdPoQKBgBzY\\nu5M1YJMy8BGPDCrP2u9FRcGzaj1Sy5mCQI6/jMVhkeQCyvSBHMiGAhSvJcqqDsJK\\nptSAUETPKNjs8bSBQ2oP71vW9vRXcCf+kHbvwa/WybRm48cXnreWhSubT6bYbarT\\nf41U3V5QKfl/+gWV9f1i9G2SXkmTzckV1KnMM8FJAoGBAKJdF0jeuIHIJPUI+gYC\\n1WOr/3WqMzXlYdfJbNQA1CCuX+xBk8uqVFEp6ItE5MD6Hvd6vHQQmgrOt7gYqTpe\\nSq1WXbuMXuvJM9PFL/2BYVZJ/BES6pr/sAlbMYPI4FxW1okmWgp0zjdnBYfRhhjt\\nHZzEbkfB/v/DHrce4rBVrC5y\\n-----END PRIVATE KEY-----\\n\",\"client_email\": \"firebase-adminsdk-ebidy@bhos-qa-labs.iam.gserviceaccount.com\",\"client_id\": \"107473818744212979118\",\"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\"token_uri\": \"https://oauth2.googleapis.com/token\",\"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-ebidy%40bhos-qa-labs.iam.gserviceaccount.com\"}";
        assertEquals(str,System.getenv("FIREBASE_CREDENTIALS"));

//        assertEquals(getResponse.getBody(), dummy.toString());
    }
}
