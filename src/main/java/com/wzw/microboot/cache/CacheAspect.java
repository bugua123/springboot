package com.wzw.microboot.cache;
import java.util.HashMap;

import com.wzw.microboot.entity.Dept;
import com.wzw.microboot.vo.DeptVo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.Map;

@Aspect
@Component
@EnableAspectJAutoProxy
public class CacheAspect {

    //声明缓存容器
    private Map<String,Object> CACHE_CONTAINER=new HashMap<>();
    //声明切面表达式
    private static final String POINTCUT_DEPT_ADD = "execution(* com.wzw.microboot.service.impl.DeptServiceImpl.save(..))";
    private static final String POINTCUT_DEPT_UPDATE = "execution(* com.wzw.microboot.service.impl.DeptServiceImpl.updateById(..))";
    private static final String POINTCUT_DEPT_GET = "execution(* com.wzw.microboot.service.impl.DeptServiceImpl.getById(..))";
    private static final String POINTCUT_DEPT_DELETE = "execution(* com.wzw.microboot.service.impl.DeptServiceImpl.removeById(..))";

    private static final String   CACHE_DEPT_PROFIX="DEPT:";
/**
 * 查询切入
 */
    @Around(value = POINTCUT_DEPT_GET)
    public Object cacheDeptGet(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer object = (Integer) joinPoint.getArgs()[0];
        //从缓存取出
        Object res1 = CACHE_CONTAINER.get(CACHE_DEPT_PROFIX + object);
        if (res1 != null) {
            return res1;
        } else {
            Dept res2 = (Dept) joinPoint.proceed();
            CACHE_CONTAINER.put(CACHE_DEPT_PROFIX+res2.getId(),res2);
            return res2;
        }
    }

    /**
     * 更新切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_DEPT_UPDATE)
    public Object cacheDeptUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        DeptVo deptVo = (DeptVo) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if(isSuccess){
            Dept dept = (Dept)  CACHE_CONTAINER.get(CACHE_DEPT_PROFIX + deptVo.getId());
            if(null==dept){
                dept=new Dept();
                BeanUtils.copyProperties(deptVo,dept);
                CACHE_CONTAINER.put(CACHE_DEPT_PROFIX+dept.getId(),dept);
            }
        }
        return isSuccess;
    }
    /**
     * 删除切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_DEPT_DELETE)
    public Object cacheDeptDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if(isSuccess){
            //删除缓存
            CACHE_CONTAINER.remove(CACHE_DEPT_PROFIX+id);
        }
        return isSuccess;
    }
}