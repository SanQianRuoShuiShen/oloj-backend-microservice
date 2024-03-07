package com.yang.olojbackendquestionservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import olojbackendmodel.model.dto.questionsubmit.QuestionSubmitAddRequest;
import olojbackendmodel.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import olojbackendmodel.model.entity.QuestionSubmit;
import olojbackendmodel.model.entity.User;
import olojbackendmodel.model.vo.QuestionSubmitVO;


/**
* @author WEBGUO
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2023-10-10 22:37:46
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest（题目提交信息）
     * @param loginUser
     * @return
     */
    Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);
    /**
     * 获取查询条件
     *
     * @param questionQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);


    /**
     * 获取题目封装
     *
     * @param question
     * @param loginuser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);

}
