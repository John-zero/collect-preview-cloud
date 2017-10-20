package com.web.service;

import com.web.VO.FolderVO;

import java.util.List;

public interface IFoldersService
{

    List<FolderVO> getFoldersByUserId(long userId);

}
