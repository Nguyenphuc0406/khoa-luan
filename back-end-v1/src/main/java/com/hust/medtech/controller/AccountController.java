package com.hust.medtech.controller;

import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.base.response.NotFoundResponse;
import com.hust.medtech.base.response.OkResponse;
import com.hust.medtech.config.ConfigUrl;
import com.hust.medtech.config.MyCrawler;
import com.hust.medtech.data.dto.AccountDTO;
import com.hust.medtech.data.dto.NotificationDTO;
import com.hust.medtech.data.entity.Account;
import com.hust.medtech.repository.AccountRepository;
import com.hust.medtech.security.CustomUserDetails;
import com.hust.medtech.security.JwtTokenProvider;
import com.hust.medtech.service.impl.AccountServiceImpl;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class AccountController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private AccountRepository accountRepository;

    @PostMapping(ConfigUrl.URL_LOGIN)
    public BaseResponse login(@Validated @RequestBody Account request) {

//			// Xác thực từ username và password.

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
        String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        return new OkResponse(jwt);

        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context

    }
    @GetMapping("/getUserInfo")
    public BaseResponse getUserInfoByToken(){
        String patientName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AccountDTO> accountParent = accountRepository._getAccountByAccountId(patientName);
        if(!accountParent.isPresent()){
            return new NotFoundResponse("Không tìm thấy account");
        }
        return new OkResponse(accountParent.get());
    }
    @SneakyThrows
    @GetMapping("/getNews")
    public BaseResponse getNews(){
        Document doc = Jsoup.connect("https://vnexpress.net/").get();
        Elements elements = doc.getElementsByClass("item-news");
        List<NotificationDTO> noti = new ArrayList<>();
        for (Element e : elements){
            try {
                Element edTitle = e.getElementsByClass("title-news").get(0).child(0);
                String data = edTitle.text();
                String linkUrl = edTitle.attr("href");
                String content = e.getElementsByClass("description").get(0).child(0).text();
                if(data.contains("Covid-19") || data.contains("nCoV") || content.contains("Covid-19")){
                    //strings.add(data);
//                    strings.add(content);
                    Elements ed = e.getElementsByClass("thumb-art").get(0).child(0).child(0)
                            .getElementsByTag("img");
                    String link = ed.attr("data-src");
                    if("".equals(link)){
                         link = ed.attr("src");
                    }
                    NotificationDTO notificationDTO = NotificationDTO.builder()
                            .title(data)
                            .content(content)
                            .link(link)
                            .srcUrl(linkUrl).build();
                    noti.add(notificationDTO);


                }

            }catch (Exception ex){

            }
        }
        return new OkResponse(noti);
    }

}
