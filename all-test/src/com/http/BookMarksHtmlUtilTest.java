package com.http;

import com.http.utils.book_mark.BookMarkNode;
import com.http.utils.book_mark.BookMarksHtmlUtil;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;

/**
 * Created by John_zero on 2017/10/18.
 */
public class BookMarksHtmlUtilTest
{
    @Test
    public void bookMarksHtmlUtil ()
    {
        File file = new File("F:/bookmarks_2017_10_9.html");

        InputStream inputStream = null;

        try
        {
            inputStream = new java.io.FileInputStream(file);

            BookMarkNode bookMarkNode = BookMarksHtmlUtil.parseBookMarks(inputStream);

//            Assert.assertNull(bookMarkNode);

            if(bookMarkNode != null)
                bookMarkNode.printTree();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
