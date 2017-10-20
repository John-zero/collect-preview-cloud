package com.web.service.impl;

import com.http.utils.book_mark.BookMarkNodeTypeEnum;
import com.web.VO.FolderVO;
import com.web.common.RedisConstants;
import com.web.entity.BookMarkEntity;
import com.web.entity.FolderEntity;
import com.web.entity.WebSiteEntity;
import com.web.mapper.BookMarksMapper;
import com.web.mapper.FoldersMapper;
import com.web.mapper.WebSitesMapper;
import com.web.service.IFoldersService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service("foldersService")
public class FoldersServiceImpl implements IFoldersService
{
    private static final Logger LOG = LogManager.getLogger(FoldersServiceImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private FoldersMapper foldersMapper;

    @Autowired
    private WebSitesMapper webSitesMapper;

    @Autowired
    private BookMarksMapper bookMarksMapper;

    @Override
    public List<FolderVO> getFoldersByUserId(long userId)
    {
        ListOperations<String, FolderVO> folderVOListOperations = redisTemplate.opsForList();

        List<FolderVO> folderVOs = folderVOListOperations.range(RedisConstants.userFolderMenu(userId), 0, -1);
        if(folderVOs != null && !folderVOs.isEmpty())
            return folderVOs;

        List<FolderEntity> folders = foldersMapper.getAllByUserId(userId);

        if(folders == null || folders.isEmpty())
            return null;

        folderVOs = tree(folders);
        if(folderVOs == null)
            return null;

        for(FolderVO folderVO : folderVOs)
        {
            paddingWebSites(folderVO);
        }

        folderVOListOperations.leftPushAll(RedisConstants.userFolderMenu(userId), folderVOs);

        return folderVOs;
    }

    private List<FolderVO> tree (List<FolderEntity> folders)
    {
        List<FolderVO> folderVOs = new ArrayList<>();

        folders.stream().forEach(folderEntity -> {
            FolderVO folderVO = new FolderVO();
            folderVO.setFolderId(folderEntity.getFolderId());
            folderVO.setFolderName(folderEntity.getFolderName());
            folderVO.setParentFolderId(folderEntity.getParentFolderId());
            folderVO.setBookMarkNodeTypeEnum(BookMarkNodeTypeEnum.FOLDER);
            folderVOs.add(folderVO);
        });

        Iterator<FolderVO> iterator = folderVOs.iterator();
        while(iterator.hasNext())
        {
            FolderVO folderVO = iterator.next();
            if(folderVO.getParentFolderId() == 0) // 直属 ROOT
            {

            }
            else if(folderVO.getParentFolderId() != 0)
            {
                FolderVO parentFolderVO = findParentFolderVO(folderVOs, folderVO.getParentFolderId());
                if(parentFolderVO != null)
                {
                    parentFolderVO.getChildrenFolders().add(folderVO);
                    iterator.remove();
                }
                else
                {
                    LOG.warn(String.format("%s - %s 没有找到所属父 %s", folderVO.getFolderId(), folderVO.getFolderName(), folderVO.getParentFolderId()));
                }
            }
        }

        return folderVOs;
    }

    private FolderVO findParentFolderVO (List<FolderVO> folderVOs, long parentFolderId)
    {
        for(FolderVO folderVO : folderVOs)
        {
            if(folderVO.getFolderId() == parentFolderId)
                return folderVO;

            if(folderVO.getChildrenFolders() != null && !folderVO.getChildrenFolders().isEmpty())
            {
                FolderVO _folderVO = findParentFolderVO (folderVO.getChildrenFolders(), parentFolderId);
                if(_folderVO != null)
                    return _folderVO;
            }
        }

        return null;
    }

    private void paddingWebSites (FolderVO folderVO)
    {
        if(folderVO.getChildrenFolders() != null && !folderVO.getChildrenFolders().isEmpty())
        {
            for(FolderVO childrenFolderVO : folderVO.getChildrenFolders())
            {
                paddingWebSites(childrenFolderVO);
            }
        }

        HashOperations<String, Long, BookMarkEntity> bookMarksHashOperations = redisTemplate.opsForHash();

        List<FolderVO> folderVOs = new ArrayList<>();

        List<WebSiteEntity> webSites = webSitesMapper.getAllByFolderId(folderVO.getFolderId());
        webSites.stream().forEach(webSiteEntity -> {
            BookMarkEntity bookMarkEntity = bookMarksMapper.getById(webSiteEntity.getBookMarkId());
            if(bookMarkEntity != null)
            {
                FolderVO _folderVO = new FolderVO();

                _folderVO.setBookMarkNodeTypeEnum(BookMarkNodeTypeEnum.FILE);

                _folderVO.setWebSiteId(webSiteEntity.getId());

                _folderVO.setBookMarkId(bookMarkEntity.getId());
                _folderVO.setWebSite(bookMarkEntity.getWebSite());
                _folderVO.setHref(bookMarkEntity.getHref());
                _folderVO.setIcon(bookMarkEntity.getIcon());

                folderVOs.add(_folderVO);
            }
            else
            {
                LOG.error(String.format("%s 没有加载到 %s 对应的 BookMarks 数据", webSiteEntity.getId(), webSiteEntity.getBookMarkId()));
            }
        });

        folderVO.getChildrenFolders().addAll(folderVOs);
    }

}
