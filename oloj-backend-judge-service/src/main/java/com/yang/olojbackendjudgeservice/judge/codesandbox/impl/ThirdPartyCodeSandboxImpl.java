package com.yang.olojbackendjudgeservice.judge.codesandbox.impl;


import com.yang.olojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import olojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import olojbackendmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * 第三方代码沙箱（网上现成的）
 */
public class ThirdPartyCodeSandboxImpl implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {

        System.out.println("第三方代码沙箱");
        return null;
    }
}
