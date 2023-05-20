package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore // 스웨거에서 페이지 이동 컨트롤러를 보여줄 필요 없어서 제외
public class ViewController {

    @GetMapping("/list")
    public void list(){}

    @GetMapping("/register")
    public void register(){}

    @GetMapping("/update")
    public void update(){}

    @GetMapping("/view")
    public void view(){}

    @GetMapping("/temp")
    public void temp(){}

}
