package com.tangerine.productmng;

import com.tangerine.productmng.model.product.ProductEntity;
import com.tangerine.productmng.model.product.dao.ProductDao;
import com.tangerine.productmng.model.user.UserEntity;
import com.tangerine.productmng.model.user.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class DataInitializer implements ApplicationRunner {

    private final PasswordEncoder passwordEncoder;

    private final UserDao userDao;

    private final ProductDao productDao;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var user = new UserEntity();
        user.setName(UUID.randomUUID().toString());
        user.setUsername("mahbodsn");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRoles(List.of("ROLE_USER", "ROLE_ADMIN"));
        userDao.save(user);

        var product = new ProductEntity();
        product.setName(UUID.randomUUID().toString());
        productDao.save(product);
    }
}
