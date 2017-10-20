package com.http.utils.book_mark;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class BookMarkNode
{
    private static final Logger LOG = LogManager.getLogger(BookMarkNode.class);

    private String folder;

    private BookMarkNodeTypeEnum bookMarkNodeTypeEnum;

    private String parent;

    private String icon;

    private String href;

    private List<BookMarkNode> childrens = new ArrayList<>();

    public BookMarkNode (String folder, BookMarkNodeTypeEnum bookMarkNodeTypeEnum, String parent)
    {
        this.folder = folder;
        this.bookMarkNodeTypeEnum = bookMarkNodeTypeEnum;
        this.parent = parent;
    }

    public BookMarkNode (String folder, BookMarkNodeTypeEnum bookMarkNodeTypeEnum, String parent, String href, String icon)
    {
        this.folder = folder;
        this.bookMarkNodeTypeEnum = bookMarkNodeTypeEnum;
        this.parent = parent;
        this.href = href;
        this.icon = icon;
    }

    public String getFolder ()
    {
        return this.folder;
    }

    public BookMarkNodeTypeEnum getBookMarkNodeTypeEnum ()
    {
        return this.bookMarkNodeTypeEnum;
    }

    public String getParent ()
    {
        return this.parent;
    }

    public String getHref()
    {
        return href;
    }

    public String getIcon()
    {
        return icon;
    }

    public List<BookMarkNode> getChildrens ()
    {
        return this.childrens;
    }

    /**
     * 罗列
     */
    public void printTree ()
    {
        LOG.info("Tree 型结构: ");
        tree(this);
    }

    private void tree (BookMarkNode bookMarkNode)
    {
        System.out.println(bookMarkNode.toString());

        if(!bookMarkNode.getChildrens().isEmpty())
        {
            for (int i = 0; i < bookMarkNode.getChildrens().size(); i++)
            {
                BookMarkNode childrenBookMarkNode = bookMarkNode.getChildrens().get(i);
                if(childrenBookMarkNode.getBookMarkNodeTypeEnum() == BookMarkNodeTypeEnum.FILE)
                {
                    System.out.println("    " + childrenBookMarkNode.toString());
                }
                else if(childrenBookMarkNode.getBookMarkNodeTypeEnum() == BookMarkNodeTypeEnum.FOLDER)
                {
                    tree(childrenBookMarkNode);
                }
            }
        }
    }

    @Override
    public String toString()
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("folder", this.folder);
        jsonObject.put("bookMarkNodeTypeEnum", this.bookMarkNodeTypeEnum);
        jsonObject.put("parent", this.parent);
        jsonObject.put("href", this.href);
        jsonObject.put("icon", this.icon);
        jsonObject.put("children.size", this.childrens.size());
        return jsonObject.toString();
    }

}
