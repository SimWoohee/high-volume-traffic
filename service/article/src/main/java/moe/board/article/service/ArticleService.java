package moe.board.article.service;

import board.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import moe.board.article.entity.Article;
import moe.board.article.repository.ArticleRepository;
import moe.board.article.service.request.ArticleCreateRequest;
import moe.board.article.service.request.ArticleUpdateRequest;
import moe.board.article.service.response.ArticleResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final Snowflake snowflake = new Snowflake();
    private final ArticleRepository articleRepository;


    @Transactional
    public ArticleResponse create(ArticleCreateRequest request){
        Article article = articleRepository.save(Article.create(snowflake.nextId(), request.getTitle(), request.getContent(), request.getBoardId(), request.getWriterId()));
        return ArticleResponse.from(article);

    }


    @Transactional
    public ArticleResponse update(Long articleId, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        article.update(request.getTitle(), request.getContent());
        return ArticleResponse.from(article);
    }


    @Transactional
    public ArticleResponse read(Long articleId){
        return ArticleResponse.from(articleRepository.findById(articleId).orElseThrow());
    }

    @Transactional
    public void delete(Long articleId){
        articleRepository.deleteById(articleId);
    }
}
