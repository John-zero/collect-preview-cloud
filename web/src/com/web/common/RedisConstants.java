package com.web.common;

public class RedisConstants
{

    public static final String COLLECT_PREVIEW_CLOUD = "C-P-C:";

    public static final String BOOK_MARKS = COLLECT_PREVIEW_CLOUD + "BOOK_MARKS";

    /**
     * 用户书签菜单
     */
    private static final String USER_FOLDER_MENU = COLLECT_PREVIEW_CLOUD + "USER:%s_FOLDER_MENU";
    public static String userFolderMenu (long userId)
    {
        return String.format(USER_FOLDER_MENU, userId);
    }

}
