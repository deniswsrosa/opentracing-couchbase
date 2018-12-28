package com.cb.demo.userProfile;

import com.cb.demo.userProfile.model.UserEntity;
import com.cb.demo.userProfile.service.UserService;
import com.cb.demo.userProfile.service.vo.SimpleUserVO;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@AutoConfigureWebTestClient
public class UserServiceIntegrationTest extends UserProfileApplicationTests {


    @Autowired
    private UserService userService;

    @Test
    public void listActiveUsersTest() throws Exception {

        createUsers(20, true, "US");
        new CountDownLatch(1).await(15000, TimeUnit.MILLISECONDS);
        List<SimpleUserVO> users =  userService
                .listActiveUsers(  true, "US", 100, 0);
        Assert.assertThat(users, Matchers.hasSize(20));
    }

    private void createUsers(int qt, boolean enabled, String country) {

        for(int i=0; i < qt; i++){

            UserEntity user = new UserEntity();
            user.setId("user-"+i);
            user.setEnabled(enabled);
            user.setCountryCode(country);
            userService.save(user);
        }
    }
//
//
//
//    @Test
//    public void findByFirstNameIgnoresCaseTest3() throws Exception {
//
//        Flux<UserEntity> flux = reactiveUserRepository.findByFirstNameLike("Denis%");
//        flux.subscribe(
//                val -> log.info("-----------------Subscriber received: {}", val.getFirstName()), val ->log.info("|||||||||||||||||||||deu erro "+val));
//
//        Thread.sleep(50000);
//    }
//
//    @Test
//    public void ftsListActiveUsers() throws Exception {
//        Instant start = Instant.now();
//        List<SimpleUserVO> users =  userService
//                .ftsListActiveUsers("Alex", true, "US", 40, 0);
//        Instant finish = Instant.now();
//        for(SimpleUserVO user: users){
//            System.out.println(user);
//        }
//        System.out.println("Total time: "+ Duration.between(start, finish).toMillis());
//        System.out.println("Number os users returned = "+users.size() );
//
//        System.out.println(users);
//    }
//
//
//
//
//    @Test
//    public void testWebFlux() throws Exception {
//
//        webTestClient.get().uri("/findByUsername?username=denis2")
//                .accept(MediaType.APPLICATION_JSON_UTF8)
//                .exchange()
//                .expectStatus().isOk()
//                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
//                .expectBodyList(UserEntity.class);
//
////        Flux<UserEntity> flux = reactiveUserRepository.findByFirstNameLike("Denis%");
////        flux.subscribe(
////                val -> log.info("-----------------Subscriber received: {}", val.getFirstName()), val ->log.info("|||||||||||||||||||||deu erro "+val));
////
////        Thread.sleep(50000);
//    }
//
//
//
//
//    @Test
//    public void findByFirstNameIgnoresCaseTest4() throws Exception {
//
//        Flux<UserEntity> flux = reactiveUserRepository.findByCountryAndTenantId(1, "US", 20, 0);
//        flux.subscribe(
//                val -> log.info("-----------------Subscriber received: {}", val.getFirstName()), val ->log.info("|||||||||||||||||||||deu erro "+val));
//
//        Thread.sleep(50000);
//    }
//
//    @Test
//    public void findByUserName() throws Exception {
//
//        Mono<UserEntity> mono = reactiveUserRepository.findByUsername("");
//        mono.subscribe(
//                val -> log.info("-----------------Subscriber received: {}", val.getFirstName()), val ->log.info("|||||||||||||||||||||deu erro "+val));
//
//    }
//
//
//    @Test
//    public void findByFirstNameIgnoresCaseTest() {
//
//        UserEntity userEntity = new UserEntity();
//        userEntity.setFirstName("Denis");
//        userEntity.setTenantId(1);
//        userEntity.setCountryCode("BR");
//        userEntity.setUsername("denis1");
//        userEntity.setPassword("fafafa");
//        userEntity.setId("someID1");
//
//        userEntityRepository.save(userEntity);
//
//
//        UserEntity user2 = new UserEntity();
//        user2.setFirstName("Denis");
//        user2.setTenantId(1);
//        user2.setCountryCode("BR");
//        user2.setUsername("denis2");
//        user2.setPassword("fafafa");
//        user2.setId("someID2");
//
//        userEntityRepository.save(user2);
//
//        UserEntity user3 = new UserEntity();
//        user3.setFirstName("Denis");
//        user3.setTenantId(1);
//        user3.setCountryCode("BR");
//        user3.setUsername("denis2");
//        user3.setPassword("fafafa");
//        user3.setId("someID2");
//
//        userEntityRepository.save(user3);
//
//
//       // Assert.assertTrue(userEntityRepository.findByFirstNameIgnoreCase("denis").size() == 2);
//
//    }
//
//
//    @Test
//    public void findByTenantIdOrderByFirstNameAscTest() {
//       // System.out.println(userEntityRepository.findByTenantIdOrderByFirstNameAsc(1, new PageRequest(0, 3)));;
//
//        System.out.println("=================================================");
//       // System.out.println(userService.listUsers(1, 0, 3));
//        System.out.println(userEntityRepository.listTenantUsers(1, 0, 3));
//
//
//    }
//
//    @Test
//    public void findIsMissing() {
//        //System.out.println(userEntityRepository.findUsersWithMissingSocialSecurityNumber(1));
//    }
}
