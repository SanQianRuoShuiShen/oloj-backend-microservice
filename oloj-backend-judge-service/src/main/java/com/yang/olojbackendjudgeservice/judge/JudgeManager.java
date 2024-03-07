package com.yang.olojbackendjudgeservice.judge;


import com.yang.olojbackendjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.yang.olojbackendjudgeservice.judge.strategy.JavaLanguageJudgeStrategy;
import com.yang.olojbackendjudgeservice.judge.strategy.JudgeContext;
import com.yang.olojbackendjudgeservice.judge.strategy.JudgeStrategy;
import olojbackendmodel.model.codesandbox.JudgeInfo;
import olojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

@Service
public class JudgeManager {
    
    JudgeInfo doJudge(JudgeContext judgeContext){
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)){
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
