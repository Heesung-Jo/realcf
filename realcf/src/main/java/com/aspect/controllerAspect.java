package com.aspect;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.repository.lookupcounterRepository;
import com.entity.lookupcounter;
import java.util.Optional;


@Aspect
@Component
public class controllerAspect {

	@Autowired
	public lookupcounterRepository lookupcounterRepository;

	@Pointcut("execution(public * com.controllers..*(..))")
	public void controllerTarget() {
	}
	
	@Around("controllerTarget()")
	public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
		//Long num = (Long) joinPoint.getArgs()[0];
		System.out.println("Aspect 잘 작동합니다.");
		System.out.println(joinPoint.getSignature().toString());
		String sugnature = joinPoint.getSignature().toString();

		Object result = joinPoint.proceed();
		
		// 카운터에 기록하기
		Optional<lookupcounter> counter = lookupcounterRepository.findByname(sugnature);
		
		if(counter.isPresent()) {
			lookupcounter realcounter = counter.get();
			realcounter.setcount(realcounter.getcount() + 1);
	    	String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			realcounter.setwhen(time);
			lookupcounterRepository.save(realcounter);

		}else {
			lookupcounter realcounter = new lookupcounter();
			realcounter.setname(sugnature);
			realcounter.setcount(1);
	    	String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			realcounter.setwhen(time);

			lookupcounterRepository.save(realcounter);
		}

		
		return result;
	}

}
