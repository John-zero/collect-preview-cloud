package com.web.service.impl;

import com.HttpConstants;
import com.http.utils.book_mark.BookMarkNode;
import com.http.utils.book_mark.BookMarkNodeTypeEnum;
import com.web.VO.FolderVO;
import com.web.common.RedisConstants;
import com.web.entity.BookMarkEntity;
import com.web.entity.FolderEntity;
import com.web.entity.UserEntity;
import com.web.entity.WebSiteEntity;
import com.web.mapper.BookMarksMapper;
import com.web.mapper.FoldersMapper;
import com.web.mapper.WebSitesMapper;
import com.web.service.IBookMarksService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("bookMarksService")
@Transactional
public class BookMarksServiceImpl implements IBookMarksService
{
    private static final Logger LOG = LogManager.getLogger(BookMarksServiceImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private FoldersMapper foldersMapper;

    @Autowired
    private WebSitesMapper webSitesMapper;

    @Autowired
    private BookMarksMapper bookMarksMapper;

    @Override
    public void insert(UserEntity userEntity, BookMarkNode bookMarkNode)
    {
        FolderEntity parentFolderEntity = new FolderEntity();
        if(!bookMarkNode.getParent().equals(HttpConstants.BOOK_MARKS_ROOT_PATH))
        {
            parentFolderEntity = foldersMapper.getByFolderName(bookMarkNode.getParent());
            if(parentFolderEntity == null)
            {
                LOG.error(String.format("%s 所属父 %s 的数据是不存在滴", bookMarkNode.getFolder(), bookMarkNode.getParent()));
                return;
            }
        }

        // 默认是覆盖, 也就是会清空之前的
        // DB
        foldersMapper.clear(userEntity.getId());
        webSitesMapper.clear(userEntity.getId());
        // Cache
        ListOperations<String, FolderVO> folderVOListOperations = redisTemplate.opsForList();
        folderVOListOperations.trim(RedisConstants.userFolderMenu(userEntity.getId()), 0, -1);

        insert(userEntity, parentFolderEntity, bookMarkNode);
    }

    private void insert (UserEntity userEntity, FolderEntity parentFolderEntity, BookMarkNode bookMarkNode)
    {
        if(bookMarkNode.getFolder().equals(HttpConstants.BOOK_MARKS_ROOT_NAME))
        {
            if(!bookMarkNode.getChildrens().isEmpty())
            {
                bookMarkNode.getChildrens().stream().forEach(childrenBookMarkNode -> insert(userEntity, parentFolderEntity, childrenBookMarkNode));
            }
        }
        else
        {
            if(bookMarkNode.getBookMarkNodeTypeEnum() == BookMarkNodeTypeEnum.FOLDER)
            {
                FolderEntity folderEntity = new FolderEntity(parentFolderEntity.getFolderId(), userEntity.getId(), bookMarkNode.getFolder());
                foldersMapper.insert(folderEntity);

                if(!bookMarkNode.getChildrens().isEmpty())
                {
                    bookMarkNode.getChildrens().stream().forEach(childrenBookMarkNode -> insert(userEntity, folderEntity, childrenBookMarkNode));
                }
            }
            else if(bookMarkNode.getBookMarkNodeTypeEnum() == BookMarkNodeTypeEnum.FILE)
            {
                HashOperations<String, Long, BookMarkEntity> bookMarksHashOperations = redisTemplate.opsForHash();

                BookMarkEntity bookMarkEntity = bookMarksMapper.getBookMark(bookMarkNode.getFolder());
                if(bookMarkEntity == null)
                {
                    bookMarkEntity = new BookMarkEntity(bookMarkNode.getFolder(), bookMarkNode.getHref(), bookMarkNode.getIcon());

                    bookMarksMapper.insert(bookMarkEntity);
                }

                WebSiteEntity webSiteEntity = new WebSiteEntity(parentFolderEntity.getFolderId(), userEntity.getId(), bookMarkEntity.getId());
                webSitesMapper.insert(webSiteEntity);
            }
        }
    }

    @Override
    public List<BookMarkEntity> getAll()
    {
        HashOperations<String, Long, BookMarkEntity> bookMarksHashOperations = redisTemplate.opsForHash();

        List<BookMarkEntity> bookMarks = bookMarksHashOperations.values(RedisConstants.BOOK_MARKS);
        if(bookMarks == null || bookMarks.isEmpty())
        {
            bookMarks = bookMarksMapper.getAll();
            if(bookMarks != null && !bookMarks.isEmpty())
            {
                Map<Long, BookMarkEntity> _bookMarks = new HashMap<>();
                bookMarks.stream().forEach(bookMarkEntity -> _bookMarks.put(bookMarkEntity.getId(), bookMarkEntity));

                bookMarksHashOperations.putAll(RedisConstants.BOOK_MARKS, _bookMarks);
            }
        }

        return bookMarks;
    }

}
