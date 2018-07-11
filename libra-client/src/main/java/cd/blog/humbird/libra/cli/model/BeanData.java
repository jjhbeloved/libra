package cd.blog.humbird.libra.cli.model;

/**
 * @author david
 * @since created by on 18/7/10 23:11
 */
public class BeanData {

    private String beanName;
    private String fieldName;
    private boolean annotation;
    private String filedDefaultValue;

    public BeanData(String beanName, String fieldName) {
        this.beanName = beanName;
        this.fieldName = fieldName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public boolean isAnnotation() {
        return annotation;
    }

    public void setAnnotation(boolean annotation) {
        this.annotation = annotation;
    }

    public String getFiledDefaultValue() {
        return filedDefaultValue;
    }

    public void setFiledDefaultValue(String filedDefaultValue) {
        this.filedDefaultValue = filedDefaultValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BeanData beanData = (BeanData) o;

        if (beanName != null ? !beanName.equals(beanData.beanName) : beanData.beanName != null) {
            return false;
        }
        return fieldName != null ? fieldName.equals(beanData.fieldName) : beanData.fieldName == null;
    }

    @Override
    public int hashCode() {
        int hash = 19;
        hash = hash * 17 + beanName.hashCode();
        hash = hash * 17 + fieldName.hashCode();
        return hash;
    }
}
