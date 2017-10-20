package com.web.control;


import com.aop.log.Log;
import com.aop.method.Method;
import com.http.utils.book_mark.BookMarkNode;
import com.http.utils.book_mark.BookMarksHtmlUtil;
import com.web.VO.FolderVO;
import com.web.common.Constants;
import com.web.entity.UserEntity;
import com.web.service.IBookMarksService;
import com.web.service.IFoldersService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/book_marks")
public class BookMarksController extends BaseController
{
    private static final Logger LOG = LogManager.getLogger(BookMarksController.class);

    @Autowired
    private IFoldersService foldersService;

    @Autowired
    private IBookMarksService bookMarksService;

    @PostMapping("/component/book_marks_menu")
    @Log(description = "书签菜单")
    @Method(description = "书签菜单")
    @ResponseBody
    public List<FolderVO> bookMarksMenu ()
    {
        UserEntity userEntity = (UserEntity) getSession().getAttribute(Constants.LOGIN_USER_SESSION_KEY);

        List<FolderVO> folderVOs = foldersService.getFoldersByUserId(userEntity.getId());

        return folderVOs;
    }

    @RequestMapping("/_import")
    @Log(description = "导入书签页")
    @Method(description = "导入书签页")
    public String _import ()
    {
        return "import";
    }

    @PostMapping("/importBookMarks")
    @Log(description = "导入书签")
    @Method(description = "导入书签")
    public String importBookMarks(@RequestParam("htmlFile") MultipartFile htmlFile)
    {
        try
        {
            if (getSession().getAttribute(Constants.LOGIN_USER_SESSION_KEY) != null)
            {
                UserEntity userEntity = (UserEntity) getSession().getAttribute(Constants.LOGIN_USER_SESSION_KEY);

                BookMarkNode bookMarkNode = BookMarksHtmlUtil.parseBookMarks(htmlFile.getInputStream());

                bookMarksService.insert(userEntity, bookMarkNode);
            }
        }
        catch (Exception e)
        {
            LOG.error(e);
        }

        return "import_result";
    }

    @RequestMapping("/_import_result")
    @Log(description = "书签导入结果列表页")
    @Method(description = "书签导入结果列表页")
    public String _import_result ()
    {
        return "import_result";
    }

    @PostMapping("/import_result")
    @Log(description = "书签导入结果列表")
    @Method(description = "书签导入结果列表")
    @ResponseBody
    public List<FolderVO> importResult ()
    {
        UserEntity userEntity = (UserEntity) getSession().getAttribute(Constants.LOGIN_USER_SESSION_KEY);

        List<FolderVO> folderVOs = foldersService.getFoldersByUserId(userEntity.getId());

        return folderVOs;
    }

    @RequestMapping("/_recommend_book_marks")
    @Log(description = "推荐书签列表")
    @Method(description = "推荐书签列表")
    public String _recommend_book_marks ()
    {
        return "recommend_book_marks";
    }

}
