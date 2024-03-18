package com.yang.olojbackendserviceclient.service;


import olojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "oloj-backend-judge-service",url = "/api/judge/inner")
public interface JudgeFeignClient {
    /**
     * 判题服务
     * @param questionSubmitId
     * @return
     */
    @PostMapping("/do")
    QuestionSubmit doJudge(@RequestParam("questionSubmitId")  long questionSubmitId);
}
