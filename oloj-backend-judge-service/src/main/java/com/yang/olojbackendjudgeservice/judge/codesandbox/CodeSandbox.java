package com.yang.olojbackendjudgeservice.judge.codesandbox;


import olojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import olojbackendmodel.model.codesandbox.ExecuteCodeResponse;

public interface CodeSandbox {
    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
