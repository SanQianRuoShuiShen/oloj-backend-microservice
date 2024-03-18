package com.yang.olojbackendserviceclient.service;

import olojbackendmodel.model.entity.Question;
import olojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
* @author WEBGUO
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2023-10-10 22:33:13
*/
@FeignClient(name = "oloj-backend-question-service",url = "/api/question/inner")
public interface QuestionFeignClient  {

    /**
     * 通过题目ID获取题目
     * @param questionId
     */
    @GetMapping("/get/id")
    Question getQuestionById(@RequestParam("questionId") long questionId);

    /**\
     * 通过题目提交ID获取题目提交信息
     * @param questionSubmitId
     */
    @GetMapping("/question_submit/get/id")
    QuestionSubmit getQuestionSubmitIdById(@RequestParam("questionSubmitId") long questionSubmitId);

    /**
     * 通过题目提交json删除题目提交信息
     * @param questionSubmit
     */
    @PostMapping("/question_submit/update")
    boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit);


}
