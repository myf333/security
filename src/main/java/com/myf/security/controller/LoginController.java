package com.myf.security.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.myf.security.model.ValidCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by myf on 2017/10/25.
 */
@Controller
@RequestMapping("/public")
public class LoginController {
    @Autowired
    private DefaultKaptcha producer;

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("imageCode")
    public void imageCode(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception{
        String yzm = producer.createText();
        BufferedImage image = producer.createImage(yzm);
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        OutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        session.setAttribute(producer.getConfig().getSessionKey(),yzm);
        session.setAttribute(producer.getConfig().getSessionDate(),new Date());
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    @RequestMapping("checkCode/{code}")
    @ResponseBody
    public ValidCode checkCode(@PathVariable("code")String code, HttpSession session){
        String validCode = (String)session.getAttribute("KAPTCHA_SESSION_KEY");
        Date validDate = (Date)session.getAttribute("KAPTCHA_SESSION_DATE");
        if(validCode==null||validDate==null){
            return new ValidCode(false,"验证码已经失效");
        }
        if(code==null||!code.equals(validCode)){
            return new ValidCode(false,"验证码填写错误");
        }
        Date now = new Date();
        if((now.getTime()-validDate.getTime())/1000/60>5){
            return new ValidCode(false,"验证码已经失效");
        }
        return new ValidCode(true,"");
    }
}
