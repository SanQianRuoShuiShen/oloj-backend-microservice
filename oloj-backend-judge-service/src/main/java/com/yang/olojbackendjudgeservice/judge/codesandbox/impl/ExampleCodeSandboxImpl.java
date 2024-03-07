package com.yang.olojbackendjudgeservice.judge.codesandbox.impl;


import com.yang.olojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import lombok.extern.slf4j.Slf4j;
import olojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import olojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import olojbackendmodel.model.codesandbox.JudgeInfo;
import olojbackendmodel.model.enums.JudgeInfoMessageEnum;
import olojbackendmodel.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * 示例代码沙箱
 */
@Slf4j
public class ExampleCodeSandboxImpl implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {


        List<String> inputList = executeCodeRequest.getInputList();

        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);


        log.info("代码沙箱请求信息:"+executeCodeRequest.toString());
        System.out.println("示例代码沙箱");
        return null;
    }
}
