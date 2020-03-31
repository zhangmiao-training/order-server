package formssi.orderserver.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    public Boolean delete(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                return redisTemplate.delete(key[0]);
            } else {
                Long result = redisTemplate.delete(Arrays.asList(key));
                return result != null && result > 0;
            }
        } else {
            return false;
        }
    }

    //============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);

    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public Long decrement(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    //================================Map=================================

    /**
     * 获取Map
     * @param hash 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> getMap(String hash) {
        return redisTemplate.opsForHash().entries(hash);
    }

    /**
     * 保存Map
     * @param hash 键
     * @param map  对应多个键值
     * @return true 成功 false 失败
     */
    public boolean setMap(String hash, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(hash, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 保存Map
     * @param hash 键
     * @param map  对应多个键值
     * @param time 缓存失效时间
     * @return true成功 false失败
     */
    public boolean setMap(String hash, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(hash, map);
            if (time > 0) {
                expire(hash, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 在hash表里面插入数据
     *
     * @param hash  键
     * @param key   项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean setMapItem(String hash, String key, Object value) {
        try {
            redisTemplate.opsForHash().put(hash, key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 在hash表里面插入数据
     *
     * @param hash  键
     * @param key   项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean setMapItem(String hash, String key, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(hash, key, value);
            if (time > 0) {
                expire(hash, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取Map值
     * @param hash
     * @param key
     * @return
     */
    public Object getMapItem(String hash, String key) {
        return redisTemplate.opsForHash().get(hash, key);
    }

    /**
     * 删除hash表里面的项
     *
     * @param hash    键
     * @param objects 数据项
     */
    public void deleteMapItem(String hash, Object... objects) {
        redisTemplate.opsForHash().delete(hash, objects);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hasKeyMapItem(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增
     *
     * @param key   键
     * @param item  项
     * @param delta 要增加几(大于0)
     * @return
     */
    public double incrementMap(String key, String item, double delta) {
        return redisTemplate.opsForHash().increment(key, item, delta);
    }

    /**
     * hash递减
     *
     * @param key   键
     * @param item  项
     * @param delta 要减少记(小于0)
     * @return
     */
    public double decrementMap(String key, String item, double delta) {
        return redisTemplate.opsForHash().increment(key, item, -delta);
    }


    //===============================List=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return
     */
    public List<Object> getList(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public Long setList(String key, List<Object> value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public Long setList(String key, List<Object> value, long time) {
        Long result = redisTemplate.opsForList().rightPushAll(key, value);
        if (time > 0) expire(key, time);
        return result;
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public Long getListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object getListIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public Long setListItem(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public Long setListItem(String key, Object value, long time) {
        Long result = redisTemplate.opsForList().rightPush(key, value);
        if (time > 0) expire(key, time);
        return result;
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean replaceListItem(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public Long removeListItem(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }
}