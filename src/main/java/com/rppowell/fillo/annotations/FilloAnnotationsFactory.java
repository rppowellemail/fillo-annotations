package com.rppowell.fillo.annotations;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Recordset;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class FilloAnnotationsFactory {

    private static List<FilloColumnField> getFilloColumnFields(Class annotatedClass) {
        String xlsxColumn;
        List<FilloColumnField> entries = new ArrayList<FilloColumnField>();
        Field[] fields = annotatedClass.getFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation  annotation : annotations) {
                if (annotation instanceof FilloColumn) {
                    FilloColumn filloColumn = (FilloColumn) annotation;
                    if (filloColumn.columnName().length() == 0) {
                        xlsxColumn = field.getName();
                    } else {
                        xlsxColumn = filloColumn.columnName();
                    }
                    entries.add(new FilloColumnField(xlsxColumn, field));
                }
            }
        }
        return entries;
    }

    private static void setFilloColumnFields(Recordset recordset, Object object, List<FilloColumnField> filloColumnFields) throws FilloException, IllegalAccessException {
        for (FilloColumnField filloColumnField : filloColumnFields ) {
            String value = "";
            value = recordset.getField(filloColumnField.columnName);
            filloColumnField.field.set(object, value);
        }
    }

    public static Object extractObjectFromRecordset(Recordset recordset, Class annotatedClass) throws IllegalAccessException, FilloException, InvocationTargetException, InstantiationException, NoSuchMethodException, ClassNotFoundException {
        Class<?> c = Class.forName(annotatedClass.getName());
        Constructor<?> cons = c.getConstructor();
        Object object = cons.newInstance();
        List<FilloColumnField> filloColumnFields = getFilloColumnFields(annotatedClass);
        setFilloColumnFields(recordset, object, filloColumnFields);
        return object;
    }

    public static Object extractFirstObjectFromRecordset(Recordset recordset, Class annotatedClass) throws FilloException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Object object = null;
        if(recordset.next()) {
            object = extractObjectFromRecordset(recordset, annotatedClass);
        }
        return object;
    }

    public static <T> List<T> extractClassesFromRecordset(Recordset recordset, Class<T> annotatedClass) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, FilloException {
        List<T> list = new ArrayList<>();
        Class<?> c = Class.forName(annotatedClass.getName());
        Constructor<?> cons = c.getConstructor();
        Object object = cons.newInstance();
        while(recordset.next()) {
            object = extractObjectFromRecordset(recordset, annotatedClass);
            list.add((T)object);
        }
        return list;
    }

    public static <T> T extractFirstClassFromRecordset(Recordset recordset, Class<T> annotatedClass) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, FilloException {
        Class<?> c = Class.forName(annotatedClass.getName());
        Constructor<?> cons = c.getConstructor();
        Object object = cons.newInstance();
        List<FilloColumnField> filloColumnFields = getFilloColumnFields(annotatedClass);
        setFilloColumnFields(recordset, object, filloColumnFields);
        return (T)object;
    }

    @Deprecated
    public static Object extract(Recordset recordset, Class annotatedClass) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, FilloException {
        Class<?> c = Class.forName(annotatedClass.getName());
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
