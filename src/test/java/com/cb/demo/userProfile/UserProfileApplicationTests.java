package com.cb.demo.userProfile;

import com.couchbase.client.java.Bucket;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@AutoConfigureWebTestClient
public class UserProfileApplicationTests {

    @Autowired
    private Bucket bucket;

    @Before
    @After
    public void deleteAll(){
        bucket.bucketManager().flush();
    }
}
