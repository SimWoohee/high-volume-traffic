package moe.board.article.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import moe.board.article.entity.Article;
import moe.board.article.service.ArticleService;
import moe.board.article.service.request.ArticleCreateRequest;
import moe.board.article.service.request.ArticleUpdateRequest;
import moe.board.article.service.response.ArticlePageResponse;
import moe.board.article.service.response.ArticleResponse;
import org.aspectj.lang.annotation.Before;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleController {
    private ObjectMapper mapper;
    @Before("create")
    public void setUp(){
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    private  final ArticleService articleService;

    @GetMapping("/v1/articles/{articleId}")
    public ArticleResponse read(@PathVariable Long articleId){
        return articleService.read(articleId);
    }

    @GetMapping("/v1/articles")
    public ArticlePageResponse readAll(
            @RequestParam("boardId") Long boardId,
            @RequestParam("page") Long page,
            @RequestParam("pageSize") Long pageSize
            ){
        return articleService.readAll(boardId, page, pageSize);
    }


    @PostMapping("/v1/articles")
    public ArticleResponse create(@RequestBody ArticleCreateRequest request){
        return articleService.create(request);
    }

    @PutMapping("/v1/articles/{articleId}")
    public ArticleResponse update(@PathVariable Long articleId, @RequestBody ArticleUpdateRequest request){
        return articleService.update(articleId, request);
    }


    @DeleteMapping("/v1/articles/{articleId}")
    public void delete(@PathVariable Long articleId){
        articleService.delete(articleId);
    }

    @GetMapping("/v1/articles/infinite-scroll")
    public List<ArticleResponse> readAllInfiniteScroll(
            @RequestParam("boardId") Long boardId
            ,@RequestParam("pageSize") Long pageSize
            ,@RequestParam(value = "lastArticleId", required = false) Long lastArticleId
            ){
            return articleService.readAllInfiniteScroll(boardId, pageSize, lastArticleId);
    }

}
