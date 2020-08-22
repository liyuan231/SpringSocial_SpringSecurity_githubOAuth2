package com.liyuan.controller;

import com.liyuan.github.api.UserTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.social.connect.*;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@RestController
public class TestController {
    @PostMapping("/test-post")
    public String testPost() {
        return "Liyuan-POST";
    }

    @GetMapping("/test-get")
    public String testGet() {
        return "Liyuan-GET";
    }

    @GetMapping("/test")
    @PreAuthorize("hasAuthority('test1')")
    public String testAuthority() {
        return "you have the authority!";
    }
//    @Autowired
//    ProviderSignInUtils providerSignInUtils;

    //    @RequestMapping(value="/signup", method=RequestMethod.POST)
//    public String signup(@Valid SignupForm form, BindingResult formBinding, WebRequest request) {
//        if (formBinding.hasErrors()) {
//            return null;
//        }
//        Account account = createAccount(form, formBinding);
//        if (account != null) {
//            SignInUtils.signin(account.getUsername());
//            ProviderSignInUtils.doPostSignUp(account.getUsername(), request);
//            return "redirect:/";
//        }
//        return null;
//    }
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signupForm(WebRequest request) {
//        Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);

//        connection.get
//        connection.get
        return "-1";
    }

}
