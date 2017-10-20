package com.http.utils.book_mark;

import com.HttpConstants;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;

public final class BookMarksHtmlUtil
{
    private static final Logger LOG = LogManager.getLogger(BookMarksHtmlUtil.class);

    public static BookMarkNode parseBookMarks (InputStream inputStream)
    {
        BookMarkNode rootBookMarkNode = new BookMarkNode(HttpConstants.BOOK_MARKS_ROOT_NAME, BookMarkNodeTypeEnum.FOLDER, HttpConstants.BOOK_MARKS_ROOT_PATH);
        if(inputStream == null)
            return rootBookMarkNode;

        try
        {
            Document document = Jsoup.parse(inputStream, "UTF-8", "");

            Elements elements = document.select("dt");
            if(elements.size() <= 0)
                return rootBookMarkNode;

            BookMarkNode bookMarkNode = folder (rootBookMarkNode, elements.get(0));

            if(bookMarkNode != null)
            {
                bookMarkNode.printTree();

                return bookMarkNode;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            IOUtils.closeQuietly(inputStream);
        }

        return rootBookMarkNode;
    }

    /**
     * 格式:
     *  <DT><H3>...</H3>
     *  <DL>
     *      <p>
     *          <DT><A>...</A>
     *          <DT><A>...</A>
     *  </DL>
     *      <p>
     * @param parentBookMarkNode
     * @param element
     */
    private static BookMarkNode folder (BookMarkNode parentBookMarkNode, Element element)
    {
        if("DT".equalsIgnoreCase(element.nodeName()))
        {
            Element DT = element;

            Element H3 = DT.child(0);
            if(H3 == null)
                return null;

            BookMarkNode bookMarkNode = null;
            if("书签栏".equals(H3.text()) || H3.hasAttr("PERSONAL_TOOLBAR_FOLDER"))
                bookMarkNode = new BookMarkNode(HttpConstants.BOOK_MARKS_ROOT_NAME, BookMarkNodeTypeEnum.FOLDER, HttpConstants.BOOK_MARKS_ROOT_PATH);
            else
                bookMarkNode = new BookMarkNode(H3.text(), BookMarkNodeTypeEnum.FOLDER, parentBookMarkNode.getFolder());

            Element DL = DT.child(1);
            if(DL == null)
                return null;

            Elements elements = DL.children();
            for (int i = 0; i < elements.size(); i++)
            {
                Element _element = elements.get(i);
                if("DT".equalsIgnoreCase(_element.nodeName()))
                {
                    Element _DT = _element;
                    if("A".equalsIgnoreCase(_DT.child(0).nodeName()))
                    {
                        Element A = _DT.child(0);

                        BookMarkNode a_BookMarkNode = new BookMarkNode(A.text(), BookMarkNodeTypeEnum.FILE, bookMarkNode.getFolder(), A.attr("HREF"), A.attr("ICON"));

                        bookMarkNode.getChildrens().add(a_BookMarkNode);
                    }
                    else if("H3".equalsIgnoreCase(_DT.child(0).nodeName()))
                    {
                        BookMarkNode childrenFolder = folder (bookMarkNode, _element);

                        if(childrenFolder != null)
                            bookMarkNode.getChildrens().add(childrenFolder);
                    }
                }
            }

            return bookMarkNode;
        }

        return null;
    }

}
