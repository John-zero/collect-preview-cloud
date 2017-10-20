package com.enums;

public enum EnvEnum
{
    // 测试
    TEST ("test"),
    // 研发
    DEV ("dev"),
    // 预研发
    PRE_DEV ("pre_dev"),
    // 预生产
    PRE_PRODUCTION ("pre_production"),
    // 生产
    PRODUCTION ("production"),
    ;

    private String env;

    EnvEnum (String env)
    {
        this.env = env;
    }

    public String env ()
    {
        return this.env;
    }

    public static EnvEnum gain (String env)
    {
        for(EnvEnum typeEnum : EnvEnum.values())
        {
            if(typeEnum.env.equals(env))
                return typeEnum;
        }

        return null;
    }

}
