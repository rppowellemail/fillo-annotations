package com.rppowell.fillo.annotations;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Recordset;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class FilloAnnotationsFactory {
    public static Object extract(Recordset recordset, Class annotatedClass) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, FilloException {
        Class<?> c = Class.forName(annotatedClass.getName());
        //System.out.println("annotatedClass getName('" + c.getName() + "')");
        //System.out.println("annotatedClass getCanonicalName('" + c.getCanonicalName() + "')");
        //System.out.println("annotatedClass getSimpleName('" + c.getSimpleName() + "')");
        Constructor<?> cons = c.getConstructor();
        Object object = cons.newInstance();

        String xlsxColumn;
        ArrayList<FilloColumnField> entries = new ArrayList<FilloColumnField>();

        // find all annotated fields
        Field[] fields = annotatedClass.getFields();
        for (Field field : fields) {
            //System.out.println("Field:" + field.getName());
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof FilloColumn) {
                    FilloColumn filloColumn = (FilloColumn) annotation;
                    if(filloColumn.columnName().length() == 0) {
                        xlsxColumn = field.getName();
                    } else {
                        xlsxColumn = filloColumn.columnName();
                    }
                    //System.out.println("FilloColumn annotated field:" + field.getName() + " columnName: " + filloColumn.columnName() + " -> " + xlsxColumn );
                    entries.add(new FilloColumnField(xlsxColumn, field));
                }
            }
        }

        for (FilloColumnField entry : entries) {
            String value = "";
            value = recordset.getField(entry.columnName);
            entry.field.set(object, value);
        }
        return object;
    }
}
