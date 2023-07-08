package top.daisy.template;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class test {

    private static final Logger logger = LoggerFactory.getLogger(test.class);

    @Test
    public void listtest(){
//        第一个题目：输入一串字符串，判定字符串中出现过哪些字符
//        例如输入，ab1a21b,结果是，出现的字符包括a,b,1,2
        Set<String> stringSet =new HashSet<>();
        stringSet.add("ab1a21b");


        logger.debug("{}",stringSet);



//        不单单要统计出现的字符，还要计算出现的次数
//        结果是这样a:2,b:2,1:2,2:1

    }


}
