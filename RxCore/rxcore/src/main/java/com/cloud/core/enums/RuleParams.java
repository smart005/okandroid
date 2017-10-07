package com.cloud.core.enums;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-6-15 上午10:08:16
 * @Description:验证规则
 * @Modifier:
 * @ModifyContent:
 */
public enum RuleParams {

    /**
     * 手机号
     */
    Phone("^1\\d{10}$"),
    /**
     * email
     */
    Email("^[\\w]+[@][\\w]+[\\.][\\w]+$"),
    /**
     * 验证金额
     */
    ValidMoney("^(([1-9]\\d{0,9})|0)(\\.\\d{1,2})?$"),
    /**
     * 过滤金额
     */
    FilterMoney("(([1-9]\\d{0,9})|0)(\\.\\d{1,})?"),
    /**
     * url或file:///本地文件路径验证
     */
    Url("^(http|https|file)://[/]?(([\\w-]+\\.)+)?[\\w-]+(/[\\w-./?%&=,@!~`#$%^&*,./]*)?$"),
    /**
     * 匹配指定标记及属性值(开始结束标签必须配对)<br/>
     * {0}、{1}、{2}分别代表标签、属性名、属性值<br/>
     * 内容名称:text
     */
    MatchThisTagAndAttr(
            "(?is)<{0}(\\S*?) [^>]*>*?\1[^>]*?{1}=(['\"\"\\s]?)[\\s\\S].?({2})(['\"\"\\s]?)\1[^>]*?>(?<text>(?><{0}[^>]*>(?<o>)|</{0}>(?<-o>)|(?:(?!</?{0}\b).)*)*(?(o)(?!)))</{0}>"),
    /**
     * 获取标记之间内容 {0}、{1}分别代表开始结束标记
     */
    MatchTagBetweenContent("(?<=%s)([.\\S\\s]*)(?=%s)"),

    /**
     * 是否是英文字母
     */
    MatchEnglishLetters("^[A-Za-z]"),
    /**
     * 身份证正则
     */
    IDCard("(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$)"),
    /**
     * 匹配域名
     */
    MatchDomain("((?<=://)[a-zA-Z\\.0-9]+(?=\\/))|(?<=://)[a-zA-Z\\.0-9]+(?=)");

    private String value = "";

    RuleParams(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
