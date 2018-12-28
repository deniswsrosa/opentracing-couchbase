package com.cb.demo.userProfile;

import com.cb.demo.userProfile.model.UserEntity;
import com.cb.demo.userProfile.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class UserCommandLineRunner implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... strings) throws Exception {

        String[] names = {"Olivia", "Alex", "Allex", "Alec", "Charlotte", "Benjamin",
                "James", "Elijah", "Michael", "Liam", "Emma", "Isabella", "Mia", "Robert", "Maria", "David", "Mary",
                "George", "Henry", "Thomas", "Joseph", "Samuel", "Elizabeth", "Margaret", "Martha", "Ann", "Catherine"};

        String[] surnames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller",
                "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez",  "Gonzalez" };


        for(int i=0; i < 300;i++) {

            if(i%100 == 0) {
                System.out.println("----------- i = "+i);
            }
            UserEntity userEntity = new UserEntity();
            userEntity.setId("user-"+i);
            userEntity.setFirstName(names[ThreadLocalRandom.current().nextInt(0, 26 + 1)]);
            userEntity.setLastName(surnames[ThreadLocalRandom.current().nextInt(0, 12 + 1)]);
            userEntity.setTenantId(1);
            //userEntity.setCountryCode(i> 40000?"DE": "US");
            userEntity.setCountryCode( "US");
            userEntity.setUsername("user"+i);
            userEntity.setPassword("secret");
           // userEntity.setEnabled(i>70000? false:true);
            userEntity.setEnabled(true);
            userService.save(userEntity);

            System.out.println(userService.getUser(userEntity.getId()));
        }

//        for(int i=40000; i < 1000001;i++) {
//
//            if(i%100 == 0) {
//                System.out.println("----------- i = "+i);
//                Thread.sleep(100);
//            }
//            UserEntity userEntity = new UserEntity();
//            userEntity.setId("user-"+i);
//            userEntity.setFirstName("Some Name "+i);
//            userEntity.setTenantId(1);
//            userEntity.setCountryCode("US");
//            userEntity.setUsername("user"+i);
//            userEntity.setPassword("secret");
//            userEntity.setEnabled(true);
//            userEntityRepository.save(userEntity);
//        }

    }
}
