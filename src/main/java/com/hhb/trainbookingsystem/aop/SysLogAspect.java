package com.hhb.trainbookingsystem.aop;


import com.hhb.trainbookingsystem.entity.LoginfoEntity;
import com.hhb.trainbookingsystem.entity.OrdinaryUserEntity;
import com.hhb.trainbookingsystem.service.LoginfoService;
import com.hhb.trainbookingsystem.annotation.Operation;
import com.hhb.trainbookingsystem.util.IPUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.UUID;

@Aspect
@Component
public class SysLogAspect {
    @Autowired
    LoginfoService loginfoService;

    @Pointcut("@annotation(com.hhb.trainbookingsystem.annotation.Operation)")
    public void logpoint(){}

    @AfterReturning(pointcut = "logpoint()",returning = "ret")
    public void saveLog(JoinPoint joinPoint,Object ret){
        long time=System.currentTimeMillis();
        try {
            time=System.currentTimeMillis()-time;
        }
        finally {
            try {
                SysLog(joinPoint,ret,time);
            }catch (Exception e){
                System.out.println("LogAspect 操作失败");
                e.printStackTrace();
            }
        }
    }


    public void SysLog(JoinPoint joinPoint,Object ret,long time){
//        //获取请求ip
//        ServletRequestAttributes attributes=(ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
//        HttpServletRequest request=attributes.getRequest();
//        String ip= IPUtil.getClientIp(request);
//
//        //获取用户
//        HttpSession session=attributes.getRequest().getSession(true);
//        OrdinaryUserEntity user=(OrdinaryUserEntity) session.getAttribute("user");

        String ip="";
        OrdinaryUserEntity user=new OrdinaryUserEntity();
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();
            ip= IPUtil.getClientIp(request);

            //获取用户
            HttpSession session=request.getSession(true);
            user=(OrdinaryUserEntity) session.getAttribute("user");
        }



        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        LoginfoEntity operationLog = new LoginfoEntity();

        operationLog.setRuntime(time);
        operationLog.setId(UUID.randomUUID().toString());
        operationLog.setCreatetime(new Timestamp(System.currentTimeMillis()));
        operationLog.setMethod(signature.getDeclaringTypeName() + "." + signature.getName());
//        operationLog.setArgs(JSON.toJSONString(joinPoint.getArgs()));
        //operationLog.setReturnvalue(JSON.toJSONString(ret));
        operationLog.setClientip(ip);
        if(user!=null){
        operationLog.setUsername(user.getName());}
        Operation annotation=signature.getMethod().getAnnotation(Operation.class);
        if(annotation!=null){
            operationLog.setLevel(annotation.level());
            operationLog.setOperationtype(annotation.operationType().getValue());
            operationLog.setOperationunit(annotation.operationUnit().getValue());
            operationLog.setDescribe(annotation.value());
        }
        loginfoService.insertLogininfo(operationLog);


    }


//    //切面 配置通知
//    @AfterReturning(pointcut = "logPoinCut()")
//    public void saveSysLog(JoinPoint joinPoint) {
//
//        //保存日志
//        SysLog sysLog = new SysLog();
//
//        //从切面织入点处通过反射机制获取织入点处的方法
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        //获取切入点所在的方法
//        Method method = signature.getMethod();
//
//
//        //获取操作
//        Operation operation = method.getAnnotation(Operation.class);
//        if (operation != null) {
//            String value = operation.value();
//            sysLog.setOperation(value);//保存获取的操作
//        }
//
//        //获取请求的类名
//        String className = joinPoint.getTarget().getClass().getName();
//
//        //获取请求的方法名
//        String methodName = method.getName();
//        sysLog.setMethod(className + "." + methodName);
//
//        //请求的参数
//        Object[] args = joinPoint.getArgs();
//        //将参数所在的数组转换成json
//        String params = null;
//        try {
//            params = JacksonUtil.obj2json(args);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        sysLog.setParams(params);
//
//        //请求的时间
//        sysLog.setCreateDate(new Date());
//
//        //获取用户名
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (!(authentication instanceof AnonymousAuthenticationToken)) {
//            sysLog.setUsername(authentication.getName());
//        }
//
//        //获取用户ip地址
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
//                .getRequest();
//       // sysLog.setIp(IpAddressUtil.getIpAdrress(request));
//
//        System.out.println("日志记录: "+sysLog.toString());
//
//        //调用service保存SysLog实体类到数据库
//
//    }


}
