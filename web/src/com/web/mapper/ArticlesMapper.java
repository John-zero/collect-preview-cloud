package com.web.mapper;

import com.web.entity.ArticlesEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticlesMapper
{

    ArticlesEntity getById(@Param("articleId") long articleId);

    ArticlesEntity getByArticlesName(@Param("articleName") String articleName);

    long insert(ArticlesEntity articlesEntity);

    List<ArticlesEntity> getAllByBookMarkId(@Param("bookMarkId") long bookMarkId);

}
