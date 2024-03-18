package com.yang.olojbackendjudgeservice.controller;

import com.yang.olojbackendjudgeservice.judge.JudgeService;
import com.yang.olojbackendserviceclient.service.JudgeFeignClient;
import olojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author GuoYang
 * @version 1.0
 */
@RestController
@RequestMapping("/inner")
public class JudgeInnerController implements JudgeFeignClient {
    @Resource
    JudgeService judgeService;
    /**
     * 判题服务
     * @param questionSubmitId
     * @return
     */
    @PostMapping("/do")
    @Override
    public QuestionSubmit doJudge(@RequestParam("questionSubmitId")  long questionSubmitId){
        return judgeService.doJudge(questionSubmitId);
    }
}
