package top.mnsx.take_out.component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import top.mnsx.take_out.entity.Employee;
import top.mnsx.take_out.utils.ThreadLocalUtil;

import java.time.LocalDateTime;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/9 13:47
 * @Description:
 */
@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser", ((Employee)ThreadLocalUtil.get()).getId());
        metaObject.setValue("updateUser", ((Employee)ThreadLocalUtil.get()).getId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", ((Employee)ThreadLocalUtil.get()).getId());
    }
}
