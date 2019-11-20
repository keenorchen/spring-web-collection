package com.keenor.resttempalate.mock;

import com.keenor.resttempalate.config.ErrorCode;
import com.keenor.resttempalate.pojo.BookReq;
import com.keenor.resttempalate.pojo.BookVo;
import com.keenor.resttempalate.pojo.Result;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/book")
public class MockController {

    Set<BookVo> bookVos;

    @GetMapping("/list")
    public Result<Set<BookVo>> list() {
        return Result.ofSuccess(bookVos);
    }

    @GetMapping("/detail")
    public Result<BookVo> detail(Long id) {
        BookVo bookVo = bookVos.stream().filter(i -> i.getId().equals(id)).findAny().orElse(null);
        return Result.ofSuccess(bookVo);
    }

    @PostMapping("/addByForm")
    public Result<Long> addByForm(String title, String author, String content) {
        BookVo bookVo = new BookVo();
        bookVo.setId((long) bookVos.size()).setTitle(title).setAuthor(author).setContent(content);
        bookVos.add(bookVo);
        return Result.ofSuccess(bookVo.getId());
    }

    @PostMapping("/addByJson")
    public Result<Long> addByJson(@RequestBody BookReq req) {
        BookVo bookVo = new BookVo();
        bookVo.setId((long) bookVos.size());
        BeanUtils.copyProperties(req, bookVo);
        bookVos.add(bookVo);
        return Result.ofSuccess(bookVo.getId());
    }

    @PostMapping(path = "/upload")
    public String upload(MultipartHttpServletRequest request) throws IOException {
        MultipartFile file = request.getFile("file");
        if (file == null) {
            return "no file!";
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String line = reader.readLine();
        while (line  != null) {
            builder.append(line);
            line = reader.readLine();
        }
        reader.close();
        return builder.toString();
    }

    @GetMapping("/httpError")
    public ResponseEntity<Set<BookVo>> httpError() {
        ResponseEntity<Set<BookVo>> entity = ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(bookVos);
        return entity;
    }

    @GetMapping("/apiError")
    public Result<List<BookVo>> apiError() {
        return Result.ofError(ErrorCode.DB_QUERY_NOT_EXIST);
    }

    @ResponseBody
    @RequestMapping(value = "/agent", method = {RequestMethod.GET, RequestMethod.POST})
    public String agent(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam(value = "name") String name) throws IOException {
        String agent = request.getHeader(HttpHeaders.USER_AGENT);
        if (StringUtils.isEmpty(agent) || !agent.contains("WebKit")) {
            response.sendError(HttpStatus.FORBIDDEN.value(), " illegal agent");
            return null;
        }
        return "welcome " + name;
    }


    {
        bookVos = new HashSet<>();
        BookVo bookVo = new BookVo()
                .setId(0L)
                .setTitle("论语")
                .setAuthor("孔夫子")
                .setContent("学而时习之，不亦说乎");
        BookVo bookVo1 = new BookVo()
                .setId(1L)
                .setTitle("金瓶梅")
                .setAuthor("兰陵笑笑生")
                .setContent("%￥&）#@￥%￥%……@#……&………￥……%￥&&（）*￥￥#%￥￥……%……%￥#");
        BookVo bookVo2 = new BookVo()
                .setId(2L)
                .setTitle("Principle")
                .setAuthor("达里奥")
                .setContent("在生活中主动保持理性的态度—很少有人会把每天上下班的时间记录下来，然后进行统计分析");
        bookVos.add(bookVo);
        bookVos.add(bookVo1);
        bookVos.add(bookVo2);new RestTemplate();
    }


}
