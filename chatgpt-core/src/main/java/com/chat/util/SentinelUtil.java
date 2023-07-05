package com.chat.util;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class SentinelUtil implements InitializingBean {

    public void qpsLimit(){
        List<FlowRule> rules = new ArrayList<>();
        FlowRule chatRule = new FlowRule();
        chatRule.setResource("chat");
        chatRule.setCount(1);
        chatRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        chatRule.setLimitApp("default");

        FlowRule chatCountRule = new FlowRule();
        chatCountRule.setResource("chatCount");
        chatCountRule.setCount(1);
        chatCountRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        chatCountRule.setLimitApp("default");
        rules.add(chatCountRule);
        rules.add(chatRule);
        FlowRuleManager.loadRules(rules);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        qpsLimit();
    }
}
