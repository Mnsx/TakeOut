package top.mnsx.take_out.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: mnsx-utils
 * @User: Mnsx_x
 * @CreateTime: 2022/10/1 17:12
 * @Description: redis工具类
 */
@Component
public final class RedisUtil {
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 为键对应的数据设置过期时间
     * @param key 键
     * @param time 过期时间
     * @return 返回是否设置成功
     */
    public Boolean expire(String key, Long time) {
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
     * 获取过期时间
     * @param key 键
     * @return 返回过期时间，秒作为单位
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 查看键是否存在
     * @param key 键
     * @return 返回是否存在
     */
    public Boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除键对应的数据
     * @param key 键
     */
    public void del(String key) {
        if (key != null) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 通过键获取对应的string数据
     * @param key 键
     * @return 返回string类型数据
     */
    public String get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置string键值对
     * @param key 键
     * @param value 值
     * @return 是否存储成功
     */
    public Boolean set(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置string键值对并设置过期时间
     * @param key 键
     * @param value 值
     * @param time 过期时间
     * @return 返回是否添加成功
     */
    public Boolean set(String key, String value, Long time) {
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
     * 将键对应的值增加offset
     * @param key 键
     * @param offset 偏移量
     * @return 返回
     */
    public Long incr(String key, Long offset) {
        if (offset < 0) {
            throw new RuntimeException("偏移量必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, offset);
    }

    /**
     * 将键对应的值减少offset
     * @param key 键
     * @param offset 偏移量
     * @return 返回结果值
     */
    public Long decr(String key, Long offset) {
        if (offset < 0) {
            throw new RuntimeException("偏移量必须大于0");
        }
        return redisTemplate.opsForValue().decrement(key, offset);
    }

    /**
     * 通过key获取hash中item对应的值
     * @param key 键
     * @param item 属性
     * @return 返回值
     */
    public Object hGet(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 通过key获取hash中所有值
     * @param key 键
     * @return 所有值
     */
    public Map<Object, Object> hGet(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 将map中的键值对存储，并且将key设置为键
     * @param key 键
     * @param map 值
     * @return 是否添加成功
     */
    public Boolean hSet(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将map中的键值对存储，并且将可以设置为键，并设置过期时间
     * @param key 键
     * @param map 值
     * @param time 过期时间
     * @return 返回是否添加成功
     */
    public Boolean hSet(String key, Map<String, Object> map, Long time)  {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将item-value键值对，作为值，key设置为键，存储
     * @param key 键
     * @param item 属性
     * @param value 值
     * @return 返回是否添加成功
     */
    public Boolean hSet(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将item-value键值对作为值，key作为键，存储
     * @param key 键
     * @param item 属性
     * @param value 值
     * @param time 过期时间
     * @return 返回是否添加成功
     */
    public Boolean hSet(String key, String item, Object value, Long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 通过键，属性批量删除值
     * @param key 键
     * @param item 属性
     */
    public void hDel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 验证键中属性是否存在
     * @param key 键
     * @param item 属性
     * @return 是否存再
     */
    public Boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * 将键中对应属性的值增加offset
     * @param key 键
     * @param item 属性
     * @param offset 偏移量
     * @return 返回返回增加后的值
     */
    public Double hIncr(String key, String item, Double offset) {
        return redisTemplate.opsForHash().increment(key, item, offset);
    }

    /**
     * 将键中对应属性的值减去offset
     * @param key 键
     * @param item 属性
     * @param offset 偏移量
     * @return 返回
     */
    public Double hDecr(String key, String item, Double offset) {
        return redisTemplate.opsForHash().increment(key, item, -offset);
    }

    /**
     * 获取set数据结构中的值
     * @param key 键
     * @return 返回set
     */
    public Set<String> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * value是否存在set中
     * @param key 键
     * @param value 值
     * @return 返回是否存在键
     */
    public Boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据批量放入set中
     * @param key 键
     * @param values 值
     * @return 返回存放数据的长度
     */
    public Long sSet(String key, String... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 将set数据放入缓存，并设置过期时间
     * @param key 键
     * @param time 过期时间
     * @param values 值
     * @return 返回是否添加成功
     */
    public Long sSet(String key, Long time, String... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 获取set缓存的长度
     * @param key 键
     * @return 长度
     */
    public Long sSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 删除值为value
     * @param key 键
     * @param values 值
     * @return 返回受影响个数
     */
    public Long sDel(String key, String values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 获取list缓存的内容
     * @param key 键
     * @param start 开始索引
     * @param end 结束索引
     * @return 返回数据集合
     */
    public List<String> lGet(String key, Long start, Long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存长度
     * @param key 键
     * @return 长度
     */
    public Long lSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 通过索引，获取list中的值
     * @param key 键
     * @param index 索引
     * @return 返回值
     */
    public String lGet(String key, Long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @return 返回是否存入
     */
    public Boolean lSet(String key, String value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存并设置过期时间
     * @param key 键
     * @param value 值
     * @param time 过期时间
     * @return 返回是否添加成功
     */
    public Boolean lSet(String key, String value, Long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @return 返回是否添加成功
     */
    public Boolean lSet(String key, List<String> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存，并设置过期时间
     * @param key 键
     * @param value 值
     * @param time 过期时间
     * @return 返回是否添加成功
     */
    public Boolean lSet(String key, List<String> value, Long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     * @param key 键
     * @param index 索引
     * @param value 值
     * @return 返回是否修改成功
     */
    public Boolean lSet(String key, Long index, String value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除队列中对应值，对应个数的值
     * @param key 键
     * @param count 个数
     * @param value 值
     * @return 返回删除个数
     */
    public Long lDel(String key, Long count, String value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 添加zSet数据结构，键值对，和分数
     * @param key 键
     * @param value 值
     * @param score 分数
     * @return 返回是否添加成功
     */
    public Boolean zSet(String key, String value, Double score) {
        try {
            return redisTemplate.opsForZSet().add(key, value, score);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 批量添加ZSet数据结构
     * @param key 键
     * @param map 值与成绩作为键值对组成的HashMap
     * @return 返回添加个数
     */
    public Long zSet(String key, Map<String, Double> map) {
        Set<ZSetOperations.TypedTuple<String>> typedTuples = new HashSet<>();
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            ZSetOperations.TypedTuple<String> typedTuple = new DefaultTypedTuple<>(entry.getKey(), entry.getValue());
            typedTuples.add(typedTuple);
        }
        try {
            return redisTemplate.opsForZSet().add(key, typedTuples);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 获取zset数据结构中的值
     * @param key 键
     * @param start 起始索引
     * @param end 结束索引
     * @return 返回对应的所有值
     */
    public Set<String> zGet(String key, Long start, Long end, Boolean flag) {
        try {
            if (flag) {
                return redisTemplate.opsForZSet().reverseRange(key, start, end);
            } else {
                return redisTemplate.opsForZSet().range(key, start, end);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取zSet中的值
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @return 返回set
     */
    public Set<String> zGet(String key, Double min, Double max, Boolean flag) {
        try {
            if (flag) {
                return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
            } else {
                return redisTemplate.opsForZSet().rangeByScore(key, min, max);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取zset数据带成绩
     * @param key 键
     * @param start 起始索引
     * @param end 结束索引
     * @param flag 是否反转
     * @return 返回数据
     */
    public Set<ZSetOperations.TypedTuple<String>> zGets(String key, Long start, Long end, Boolean flag) {
        try {
            if (flag) {
                return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
            } else {
                return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取zset数据带成绩
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @param flag 是否反转
     * @return 返回set
     */
    public Set<ZSetOperations.TypedTuple<String>> zGets(String key, Double min, Double max, Boolean flag) {
        try {
            if (flag) {
                return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
            } else {
                return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 修改成绩
     * @param key 键
     * @param value 值
     * @param offset 偏移量
     * @return 返回更新后的成绩
     */
    public Double zIncr(String key, String value, Double offset) {
        try {
            return redisTemplate.opsForZSet().incrementScore(key, value, offset);
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    /**
     * 获取数量
     * @param key 键
     * @return 返回长度
     */
    public Long zSize(String key) {
        try {
            return redisTemplate.opsForZSet().zCard(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 删除键中的值
     * @param key 键
     * @param value 值
     * @return 删除个数
     */
    public Long zDel(String key, Object...value) {
        try {
            return redisTemplate.opsForZSet().remove(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 返回当前值再键中的排名
     * @param key 键
     * @param value 值
     * @return 返回排名
     */
    public Long zRank(String key, Object value) {
        try {
            return redisTemplate.opsForZSet().rank(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 设置BitMap中某个偏移量的值
     * @param key 键
     * @param offset 偏移量
     * @param value 值 1/0
     * @return 返回是否成功
     */
    public Boolean bitSet(String key, Long offset, Boolean value) {
        try {
            return redisTemplate.opsForValue().setBit(key, offset, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取bigMap中某个偏移量的值
     * @param key 键
     * @param offset 偏移量
     * @return 返回值 0/1
     */
    public Boolean bitGet(String key, Long offset) {
        try {
            return redisTemplate.opsForValue().getBit(key, offset);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取范围内为1的个数
     * @param key 键
     * @return 返回为1的个数
     */
    public Long bigCount(String key) {
        try {
            return redisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 获取键中limit范围内所有个数，可以通过offset偏移
     * @param key 键
     * @param limit 范围
     * @param offset 偏移量
     * @return 返回。。。
     */
    public List<Long> bigField(String key, int limit, int offset) {
        try {
            return (List<Long>) redisTemplate.execute((RedisCallback<List<Long>>) con -> con.bitField(key.getBytes(),
                    BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.unsigned(limit)).valueAt(offset)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 添加到HyperLogLog
     * @param key 键
     * @param element 值
     * @return 返回添加的个数
     */
    public Long pfAdd(String key, String... element) {
        try {
            return redisTemplate.opsForHyperLogLog().add(key, element);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public Long pfCount(String key) {
        try {
            return redisTemplate.opsForHyperLogLog().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }
}