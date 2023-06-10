package com.guli.search.enums;

/**
 * @Description：
 * @Author: Ryan_Wuyx
 * @Date: 2023/6/10 14:40
 */
public enum SuggesterEnums {

    // 每个枚举类的构造器参数
    ADDRESS("address", new String[]{"address"}, new String[]{"fulladdr", "street", "district"}, null);

    // 所有枚举类通用的实例变量
    private String client;
    private String[] indices;
    private String[] includes;
    private String[] excludes;

    // 所有枚举类通用的构造函数
    SuggesterEnums(String client, String[] indices, String[] includes, String[] excludes) {
        this.client = client;
        this.indices = indices;
        this.includes = includes;
        this.excludes = excludes;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getClient() {
        return client;
    }

    public String[] getIndices() {
        return indices;
    }

    public void setIndices(String[] indices) {
        this.indices = indices;
    }

    public void setIncludes(String[] includes) {
        this.includes = includes;
    }

    public String[] getIncludes() {
        return includes;
    }

    public void setExcludes(String[] excludes) {
        this.excludes = excludes;
    }

    public String[] getExcludes() {
        return excludes;
    }

}
