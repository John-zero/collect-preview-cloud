package com.web.mapper;

import com.web.entity.BookMarkEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookMarksMapper
{

    List<BookMarkEntity> getAll();

    BookMarkEntity getById(Long id);

    BookMarkEntity getBookMark(@Param("webSite") String webSite);

    long insert(BookMarkEntity bookMarkEntity);

    void update(BookMarkEntity bookMarkEntity);

    void delete(Long id);

}
