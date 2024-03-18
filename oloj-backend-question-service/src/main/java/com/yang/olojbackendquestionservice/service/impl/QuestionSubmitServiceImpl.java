package com.yang.olojbackendquestionservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yang.olojbackendcommon.common.ErrorCode;
import com.yang.olojbackendcommon.constant.CommonConstant;
import com.yang.olojbackendcommon.exception.BusinessException;
import com.yang.olojbackendcommon.utils.SqlUtils;
import com.yang.olojbackendquestionservice.mapper.QuestionSubmitMapper;
import com.yang.olojbackendquestionservice.service.QuestionService;
import com.yang.olojbackendquestionservice.service.QuestionSubmitService;
import com.yang.olojbackendserviceclient.service.JudgeFeignClient;
import com.yang.olojbackendserviceclient.service.UserFeignClient;
import olojbackendmodel.model.dto.questionsubmit.QuestionSubmitAddRequest;
import olojbackendmodel.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import olojbackendmodel.model.entity.Question;
import olojbackendmodel.model.entity.QuestionSubmit;
import olojbackendmodel.model.entity.User;
import olojbackendmodel.model.enums.QuestionSubmitLanguageEnum;
import olojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import olojbackendmodel.model.vo.QuestionSubmitVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author WEBGUO
 * @description 针对表【question_submit(题目提交)】的数据库操作Service实现
 * @createDate 2023-10-10 22:37:46
 */
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService {
    @Resource
    private QuestionService questionService;
    @Resource
    private UserFeignClient userFeignClient;
    @Resource
    @Lazy
    private JudgeFeignClient judgeFeignClient;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest（题目提交信息）
     * @param loginUser
     * @return
     */
    @Override
    public Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        //校验编程语言是否合法
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言错误");
        }

        long questionId = questionSubmitAddRequest.getQuestionId();
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已提交题目
        long userId = loginUser.getId();
        // 每个用户串行提交题目

        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(questionSubmitAddRequest.getLanguage());
        //设置初始状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean save = this.save(questionSubmit);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据插入失败");
        }
//        // 锁必须要包裹住事务方法
//        QuestionSubmitService questionSubmitService = (QuestionSubmitService) AopContext.currentProxy();
//        synchronized (String.valueOf(userId).intern()) {
//            return questionSubmitService.doQuestionSubmitInner(userId, questionId);
//        }
        Long questionSubmitId = questionSubmit.getId();
        //todo 执行判题服务
        CompletableFuture.runAsync(() -> {
            judgeFeignClient.doJudge(questionSubmitId);
        });

        return questionSubmitId;
    }


    /**
     * 获取查询包装类（用户根据那些字段查询，根据前端的请求对象，传到mybatis框架支持的查询类 QueryWrapper  类）
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if (questionSubmitQueryRequest == null) {
            return queryWrapper;
        }
        String language = questionSubmitQueryRequest.getLanguage();
        Integer status = questionSubmitQueryRequest.getStatus();
        Long questionId = questionSubmitQueryRequest.getQuestionId();
        Long userId = questionSubmitQueryRequest.getUserId();
        String sortField = questionSubmitQueryRequest.getSortField();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();
        // 拼接查询条件
        queryWrapper.eq(ObjectUtils.isNotEmpty(language), "language", language);
        queryWrapper.eq(ObjectUtils.isNotEmpty(status), "status", status);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * 获取单个信息的包装类
     *
     * @param questionSubmit
     * @param request
     * @return
     */

    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser) {
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);

        // 1. 关联查询用户信息
//        Long userId = questionSubmit.getUserId();
//        User user = null;
//        if (userId != null && userId > 0) {
//            user = userService.getById(userId);
//        }
//        UserVO userVO = userService.getUserVO(user);
//        questionSubmitVO.setUserVO(userVO);
        //脱敏,除了管理员，普通用户只能看到非答案、提交代码等公开信息
        long userId = loginUser.getId();
        //处理脱敏
        if (userId != questionSubmit.getUserId() && !userFeignClient.isAdmin(loginUser)) {
            questionSubmitVO.setCode(null);
        }

        return questionSubmitVO;
    }

    /**
     * 分页获取多个信息的包装类
     *
     * @param questionSubmitPage
     * @param request
     * @return
     */
    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser) {
        List<QuestionSubmit> questionSubmitList = questionSubmitPage.getRecords();
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollectionUtils.isEmpty(questionSubmitList)) {
            return questionSubmitVOPage;
        }
//        // 1. 关联查询用户信息
//        Set<Long> userIdSet = questionSubmitList.stream().map(QuestionSubmit::getUserId).collect(Collectors.toSet());
//        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
//                .collect(Collectors.groupingBy(User::getId));
//
//        // 填充信息
//        List<QuestionSubmitVO> questionSubmitVOList = questionSubmitList.stream().map(questionSubmit -> {
//            QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
//            Long userId = questionSubmit.getUserId();
//            User user = null;
//            if (userIdUserListMap.containsKey(userId)) {
//                user = userIdUserListMap.get(userId).get(0);
//            }
//            questionSubmitVO.setUserVO(userService.getUserVO(user));
//            return questionSubmitVO;
//        }).collect(Collectors.toList());
        List<QuestionSubmitVO> questionSubmitVOList = questionSubmitList
                .stream()
                .map(questionSubmit -> getQuestionSubmitVO(questionSubmit, loginUser))
                .collect(Collectors.toList());
        questionSubmitVOPage.setRecords(questionSubmitVOList);
        return questionSubmitVOPage;
    }


}




