package com.qyq.authordemo;

import com.qyq.authordemo.util.AesUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthordemoApplicationTests {

    @Value("${file.path}")
    private String path;
    @Value("${AES.key}")
    private String key;

    @Test
    public void contextLoads()throws Exception{
        String code = "JwxcJpQVyO6HVuzs4pGjvBHr6DWifgMK40aCO880iOezKe2t1BIfAXpNoAhLn7Cm";
        String decrypt = AesUtil.decrypt(code, key);
        String[] split = decrypt.split(",");
        String cpu = split[0];
        String application_time = split[1];
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(application_time);
        System.out.println(date);

        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.DAY_OF_MONTH,5);
        Date date1 = instance.getTime();
        String s = dateFormat.format(date1);
        System.out.println(s);


    }

}
