package cd.blog.humbird.libra.entity;

/**
 * Created by david on 2018/7/13.
 */
public enum OpLogTypeEnum {
    Env_All("环境管理", 701, 720, false),
    Env_Add("环境-新增", 705, false),
    Env_Delete("环境-删除", 706, false),
    Env_Edit("环境-修改", 707, false),

    User_All("User管理", 901, 920, false),
    User_Add("User-新增", 905, false),
    User_Delete("User-删除", 906, false),
    User_Edit("User-修改", 907, false);

    private String label;
    private int begin;
    private int end;
    private int value;
    private boolean projectRelated;

    OpLogTypeEnum(String label, int value, boolean projectRelated) {
        this.label = label;
        this.value = value;
        this.projectRelated = projectRelated;
    }

    OpLogTypeEnum(String label, int begin, int end, boolean projectRelated) {
        this.label = label;
        this.begin = begin;
        this.end = end;
        this.projectRelated = projectRelated;
    }

    public String getLabel() {
        return label;
    }

    public int getBegin() {
        return begin;
    }

    public int getEnd() {
        return end;
    }

    public int getValue() {
        return value;
    }

    public boolean isProjectRelated() {
        return projectRelated;
    }
}
