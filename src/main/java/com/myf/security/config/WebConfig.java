package com.myf.security.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Created by myf on 2017/10/25.
 * @author myf
 */
@Configuration
public class WebConfig {
    @Bean
    public DefaultKaptcha captchaProducer(){
        Properties properties = new Properties();
        //图片边框，合法值  yea  no
        properties.setProperty("kaptcha.border","yes");
        // 边框颜色，合法值  r,g,b(and optional alpha)或者white black blue
        properties.setProperty("kaptcha.border.color","blue");
        //边框厚度  合法值  大于0 的整数
        properties.setProperty("kaptcha.border.thickness","1");
        //图片宽度
        properties.setProperty("kaptcha.image.width","125");
        //图片高度
        properties.setProperty("kaptcha.image.height","50");
        //验证码长度
        properties.setProperty("kaptcha.textproducer.char.length","5");
        //文本集合  验证码的值从此集合中获取
        properties.setProperty("kaptcha.textproducer.char.string","023456789abcdefghijkmnopqrstuvwxyz");
        //字体大小
        properties.setProperty("kaptcha.textproducer.font.size","40");
        //字体颜色    合法值 r,g,b  black white  blue
        properties.setProperty("kaptcha.textproducer.font.color","black");
        //干扰颜色
        properties.setProperty("kaptcha.noise.color","blue");
        //图片样式： 水纹com.google.code.kaptcha.impl.WaterRipple
        //鱼眼com.google.code.kaptcha.impl.FishEyeGimpy
        //阴影com.google.code.kaptcha.impl.ShadowGimpy
        properties.setProperty("kaptcha.obscurificator.impl","com.google.code.kaptcha.impl.FishEyeGimpy");
        //背景颜色渐变，开始颜色
        properties.setProperty("kaptcha.background.clear.from","green");
        //背景颜色渐变，结束颜色
        properties.setProperty("kaptcha.background.clear.to","white");
        //session中存放验证码的key键
        properties.setProperty("kaptcha.session.key","KAPTCHA_SESSION_KEY");
        // The date the kaptcha is generated is put into the
        // HttpSession. This is the key value for that item in the session.
        properties.setProperty("kaptcha.session.date","KAPTCHA_SESSION_DATE");
        Config config = new Config(properties);
        DefaultKaptcha producer =  new DefaultKaptcha();
        producer.setConfig(config);
        return producer;
    }
}
