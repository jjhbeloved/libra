package cd.blog.humbird.libra.entity;

/**
 * Created by david on 2018/7/13.
 */
public enum OpLogTypeEnum {
    Config_All("配置管理", 1, 20, true),
    Config_Add("配置-添加", 5, true),
    Config_Delete("配置-删除", 8, true),
    Config_Edit("配置-设置", 11, true),
    Config_EditAttr("配置-设置属性", 14, true),
    Config_Clear("配置-清除", 17, true),

    Env_All("环境管理", 701, 720, false),
    Env_Add("环境-新增", 705, false),
    Env_Delete("环境-删除", 706, false),
    Env_Edit("环境-修改", 707, false),

    TPP_All("Team/Product/Project管理", 801, 820, false),
    Team_Add("Team-新增", 805, false),
    Team_Delete("Team-删除", 806, false),
    Team_Edit("Team-修改", 807, false),
    Product_Add("Product-新增", 808, false),
    Product_Delete("Product-删除", 809, false),
    Product_Edit("Product-修改", 810, false),
    Project_Add("Project-新增", 811, false),
    Project_Delete("Project-删除", 812, false),
    Project_Edit("Project-修改", 813, false),

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
