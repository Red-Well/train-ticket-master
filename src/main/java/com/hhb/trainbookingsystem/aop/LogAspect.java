package com.hhb.trainbookingsystem.aop;



import com.alibaba.fastjson.JSON;
import com.hhb.trainbookingsystem.entity.LoginfoEntity;
import com.hhb.trainbookingsystem.entity.OrdinaryUserEntity;
import com.hhb.trainbookingsystem.service.LoginfoService;
import com.hhb.trainbookingsystem.annotation.OperationLogDetail;
import com.hhb.trainbookingsystem.util.IPUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.UUID;

@Aspect
@Component
public class LogAspect {
    @Autowired
    private LoginfoService loginfoService;

    @Pointcut("@annotation(com.hhb.trainbookingsystem.annotation.OperationLogDetail)")
    public void loginoperationLog(){}

    /**
     * 环绕增强，相当于MethodInterceptor
     */

//    @Around("operationLog()")
//    public Object doaround(ProceedingJoinPoint joinPoint) throws Throwable{
    @AfterReturning(pointcut = "loginoperationLog()",returning = "ret")
    public void saveLog(JoinPoint joinPoint,Object ret){
        //Object res=null;
        long time=System.currentTimeMillis();
        try{
          //  res=joinPoint.proceed();
            time=System.currentTimeMillis()-time;
           // return res;
        }
        finally {
            try{
                //方法执行后增加日志
                addOperationLog(joinPoint,ret,time);
            }catch (Exception e){
                System.out.println("LogAspect 操作失败a");
                e.printStackTrace();
            }
        }
    }

    private void addOperationLog(JoinPoint joinPoint,Object ret,long time){
//        ServletRequestAttributes arr=(ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//        HttpSession session=arr.getRequest().getSession(true);
//        OrdinaryUserEntity user=(OrdinaryUserEntity)session.getAttribute("user");
//        if(user!=null){
//            System.out.println("1232424"+user.getName());
//        }


        // System.out.println(b +""+(ret instanceof UserOrderEntity) );
        //获取请求ip
        ServletRequestAttributes attributes=(ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        String ip= IPUtil.getClientIp(request);


        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        LoginfoEntity operationLog = new LoginfoEntity();

        if(ret==null){
            operationLog.setUsername((String)joinPoint.getArgs()[0]);
        }
        else {
            //operationLog.setUserid(((OrdinaryUserEntity) ret).getId());
            operationLog.setUsername(((OrdinaryUserEntity) ret).getName());
        }
        operationLog.setRuntime(time);
        operationLog.setId(UUID.randomUUID().toString());
        operationLog.setArgs(JSON.toJSONString(joinPoint.getArgs()));
        operationLog.setCreatetime(new Timestamp(System.currentTimeMillis()));
        operationLog.setMethod(signature.getDeclaringTypeName() + "." + signature.getName());

        operationLog.setClientip(ip);
        OperationLogDetail annotation = signature.getMethod().getAnnotation(OperationLogDetail.class);
        if (annotation != null) {
                operationLog.setLevel(annotation.level());
                operationLog.setDescribe(annotation.detail());
                operationLog.setOperationtype(annotation.operationType().getValue());
                operationLog.setOperationunit(annotation.operationUnit().getValue());
        }
        System.out.println("记录日志：" + operationLog.toString());
        loginfoService.insertLogininfo(operationLog);

    }


//    /**
//     * 对当前登录用户和占位符处理
//     * @param argnames 方法参数名称数组
//     * @param args 方法参数数组
//     * @param annotation 注解信息
//     * @return 返回处理后的描述
//     */
//    private String getDetail(String[] argnames,Object[] args,OperationLogDetail annotation){
//        Map<Object,Object> map=new HashMap<>(4);
//        for(int i=0;i<argnames.length;i++){
//            map.put(argnames[i],args[i]);
//        }
//        String detail = annotation.Detail();
//        try {
//            detail = "'" + "#{currentUserName}" + "'=》" + annotation.Detail();
//            for (Map.Entry<Object, Object> entry : map.entrySet()) {
//                Object k = entry.getKey();
//                Object v = entry.getValue();
//                detail = detail.replace("{{" + k + "}}", JSON.toJSONString(v));
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return detail;
//    }

//    @Before("operationLog()")
//    public void doBeforeAdvice(JoinPoint joinPoint){
//        System.out.println("进入方法前执行.....");
//
//    }

    /**
     * 处理完请求，返回内容
     * @param ret
     */
//    @AfterReturning(returning = "ret", pointcut = "operationLog()")
//    public void doAfterReturning(Object ret) {
//        System.out.println("方法的返回值 : " + ret);
//    }

    /**
     * 后置异常通知
     */
    @AfterThrowing("loginoperationLog()")
    public void throwss(JoinPoint jp){
        System.out.println("方法异常时执行.....");
    }




    /**
     * 后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
     */
//    @After("operationLog()")
//    public void after(JoinPoint jp){
//        System.out.println("方法最后执行.....");
//    }

}

