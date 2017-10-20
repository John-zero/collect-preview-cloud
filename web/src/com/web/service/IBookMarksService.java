package com.web.service;

import com.http.utils.book_mark.BookMarkNode;
import com.web.entity.BookMarkEntity;
import com.web.entity.UserEntity;

import java.util.List;

public interface IBookMarksService
{

    void insert(UserEntity userEntity, BookMarkNode bookMarkNode);

    List<BookMarkEntity> getAll();

}
