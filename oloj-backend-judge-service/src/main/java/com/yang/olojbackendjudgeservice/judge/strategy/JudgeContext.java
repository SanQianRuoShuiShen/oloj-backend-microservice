package com.yang.olojbackendjudgeservice.judge.strategy;


import lombok.Data;
import olojbackendmodel.model.codesandbox.JudgeInfo;
import olojbackendmodel.model.dto.question.JudgeCase;
import olojbackendmodel.model.entity.Question;
import olojbackendmodel.model.entity.QuestionSubmit;

import java.util.List;

/**
 * 用于定义在策略中传递的参数
 */

@Data
public class JudgeContext {
    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;


}
