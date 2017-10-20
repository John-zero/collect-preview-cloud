package com.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;

@Configuration
@PropertySource(value = "classpath:/redis.properties")
// 启用缓存特性
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport
{
    private static final Logger LOG = LogManager.getLogger(RedisConfig.class);

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    // 设置连接超时时间
    @Value("${spring.redis.timeout}")
    private int timeOut;
    // 默认DB下标
    @Value("${spring.redis.dbIndex}")
    private int dbIndex;
    // Jedis 池最大连接数总数, 默认8, 设置 -1 表示不限制
    @Value("${spring.redis.maxTotal}")
    private int maxTotal;
    // Jedis 池最大空闲连接数, 默认8
    @Value("${spring.redis.maxIdle}")
    private int maxIdle;
    // Jedis 池最少空闲连接数
    @Value("${spring.redis.minIdle}")
    private int minIdle;
    // Jedis 池没有对象返回时, 最大等待时间, 单位: 毫秒
    @Value("${spring.redis.maxWaitMillis}")
    private long maxWaitMillis;
    // 空闲检查时间
    @Value("${spring.redis.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    // 在 borrow(引入) 一个 Jedis 实例时, 是否提前进行 validate 操作
    @Value("${spring.redis.testOnBorrow}")
    private boolean testOnBorrow;
    // 在归还给 Jedis 池时, 是否提前进行 validate 操作
    @Value("${spring.redis.testOnReturn}")
    private boolean testOnReturn;

    @Bean
    public JedisConnectionFactory redisConnectionFactory()
    {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setTestOnReturn(testOnReturn);

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setTimeout(timeOut);
        jedisConnectionFactory.setDatabase(dbIndex);
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);

        return jedisConnectionFactory;
    }

    /**
     * 缓存数据时 KEY 的生成器, 可以依据业务和技术场景自行定制
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator()
    {
        return new KeyGenerator()
        {
            @Override
            public Object generate(Object target, Method method, Object... params)
            {
                StringBuilder sb = new StringBuilder();

                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params)
                    sb.append(obj.toString());

                LOG.info(String.format("keyGenerator: %s", sb));

                return sb.toString();
            }
        };
    }

    @SuppressWarnings("rawtypes")
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate)
    {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        // Number of seconds before expiration. Defaults to unlimited (0)
        cacheManager.setDefaultExpiration(300); // 设置 KEY-VALUE 超时时间
        return cacheManager;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory)
    {
        /**
         * 1. 字节流储存: JdkSerializationRedisSerializer
         * 2. String 储存: StringRedisSerializer
         * 3. JSON 格式储存: Jackson2JsonRedisSerializer (基于 String 储存)
         * 4. XML 格式储存: OxmSerializer (基于 String 储存)
         */
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(factory);
        // 对普通 K-V 操作, KEY 采取的序列化策略
//        stringRedisTemplate.setKeySerializer(jackson2JsonRedisSerializer); // 默认: StringRedisSerializer
        // VALUE 采取的序列化操作
        stringRedisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // 在 HASH 数据结构中, HASH-KEY 的序列化操作
        stringRedisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer); // 默认: StringRedisSerializer
        // 在 HASH 数据结构中, HASH-VALUE 的序列化操作
        stringRedisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        stringRedisTemplate.afterPropertiesSet();
        return stringRedisTemplate;
    }

}
