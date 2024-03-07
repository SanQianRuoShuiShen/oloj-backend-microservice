package com.yang.olojbackendjudgeservice.judge;


import olojbackendmodel.model.entity.QuestionSubmit;

public interface JudgeService {
    /**
     * 判题服务
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
