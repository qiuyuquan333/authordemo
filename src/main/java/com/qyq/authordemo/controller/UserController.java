package com.qyq.authordemo.controller;

import com.qyq.authordemo.result.ResponseResult;
import com.qyq.authordemo.util.AesUtil;
import com.qyq.authordemo.util.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 软件注册demo客户端
 */
@RestController
public class UserController {

    @Value("${file.path}")
    private String path;
    @Value("${AES.key}")
    private String key;

    @GetMapping("/login")
    public ResponseResult loginController()throws Exception{
        File file = new File("");
        String filePath = file.getCanonicalPath()+path;
        File file1 = new File(filePath);
        if(!file1.exists()){
            return new ResponseResult(1,"授权验证失败");
        }

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String text;
        while ((text = reader.readLine())!= null){
            sb.append(text);
        }

        return verify(sb.toString());

    }

    /**
     * 获取本机CPU生成申请码
     * @return
     * @throws Exception
     */
    @GetMapping("/generate")
    public ResponseResult generate()throws Exception{
        String cpu = Util.getCPU();
        String date = Util.getDate();
        String data = cpu+","+date;
        String encrypt = AesUtil.encrypt(data, key);
        return new ResponseResult(0,"生成申请码成功",encrypt);

    }

    /**
     * 根据服务端生成的授权码注册
     * @param code
     * @return
     * @throws Exception
     */
    @GetMapping("/register")
    public ResponseResult register(String code) throws Exception {
        File file1 = new File("");
        String canonicalPath = file1.getCanonicalPath();

        File file = new File(canonicalPath+path);
        if(!file.exists()){
            file.createNewFile();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(code);
        writer.flush();
        writer.close();

        return verify(code);

    }

    /**
     * 验证注册码
     * @param code
     * @return
     * @throws Exception
     */
    public ResponseResult verify(String code)throws Exception{
        String decrypt = AesUtil.decrypt(code, key);
        String[] split = decrypt.split(",");
        String cpu = split[0];
        String time = split[1];

        String cpu1 = Util.getCPU();
        String date = Util.getDate();
        System.out.println(cpu+"   "+cpu1);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date maturity = dateFormat.parse(time);
        Date application = dateFormat.parse(date);
        System.out.println(time+"  "+date);

        if(cpu.equals(cpu1)){
            if(maturity.before(application)){
                return new ResponseResult(2,"注册码过期");
            }
        }else {
            return new ResponseResult(1,"验证失败");
        }
        return new ResponseResult(0,"验证成功");
    }
}
