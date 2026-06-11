package spring_boot.it211projectfinal.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import spring_boot.it211projectfinal.model.dto.request.GradeRequestDTO;

@Aspect
@Component
@Slf4j
public class GradeLoggingAspect {

    @AfterReturning(
            value =
                    "execution(* spring_boot.it211projectfinal.service.impl.SubmissionServiceImpl.gradeSubmission(..))")
    public void logGrade(
            JoinPoint joinPoint){

        Object[] args =
                joinPoint.getArgs();

        GradeRequestDTO request =
                (GradeRequestDTO) args[0];

        Authentication auth =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String lecturerEmail =
                auth.getName();

        log.info(
                "[GRADE] Lecturer={} Submission={} Score={}",
                lecturerEmail,
                request.getSubmissionId(),
                request.getScore()
        );
    }
}
