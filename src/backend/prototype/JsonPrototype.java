package backend.prototype;

import java.lang.reflect.Field;

public class JsonPrototype {
    
    @Override
    /**
     * Use reflect to iterate every attribute and concat
     */
    public String toString() {
        //通过反射获取所有类的当前字段
        Field[] declaredFields = getClass().getDeclaredFields();
        StringBuffer sb=new StringBuffer();
        sb.append(getClass().getSimpleName()+"[");
        int fieldCount = 0;
        for (Field field:declaredFields) {
            fieldCount+=1;
            field.setAccessible(true);
            String fn = field.getName();
            try {
                sb.append(fn + ":" + field.get(this));
                if(fieldCount!=declaredFields.length) {
                    sb.append(", ");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
