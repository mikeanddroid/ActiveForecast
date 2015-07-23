package com.mike.givemewingzz.activeforecast.servermapping;

import android.util.Log;

import com.activeandroid.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class WeatherModel extends Model
{

    private static final String TAG = WeatherModel.class.getSimpleName();
    private static final Map<String, Map<String, String>> fieldMapping = Collections.synchronizedMap(new HashMap<String, Map<String, String>>());

    public static List createFromJSONList(Class class1, JSONArray jsonarray)
        throws JSONException, InstantiationException, IllegalAccessException
    {
        ArrayList arraylist = new ArrayList();
        for (int i = 0; i < jsonarray.length(); i++)
        {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            Log.d(TAG, (new StringBuilder()).append("JSON SIZE : ").append(jsonarray.length()).append(": JSONArray : ").append(jsonobject).toString());
            arraylist.add(createFromJson(class1, jsonobject));
            Log.d(TAG, (new StringBuilder()).append("JSON createFromJSONList : ").append(arraylist.get(i)).toString());
        }

        return arraylist;
    }

    public static List createFromJSONObject(Class class1, JSONObject jsonobject)
        throws JSONException, InstantiationException, IllegalAccessException
    {
        ArrayList arraylist = new ArrayList();
        JSONObject tempJsonObject = null;
        for (int i = 0; i < jsonobject.length(); i++)
        {
            tempJsonObject = jsonobject;
        }

        arraylist.add(createFromJson(class1, tempJsonObject));
        return arraylist;
    }

    public static WeatherModel createFromJson(Class class1, JSONObject jsonobject)
        throws JSONException, IllegalAccessException, InstantiationException
    {
        WeatherModel weathermodel = (WeatherModel)class1.newInstance();
        Log.d(TAG, (new StringBuilder()).append("JSONObject : ").append(jsonobject).toString());
        setFields(class1, weathermodel, jsonobject);
        return weathermodel;
    }

    public static void setFields(Class clazz, Object object, JSONObject jsonObject) throws JSONException, IllegalAccessException {
        List<Field> fields = Arrays.asList(clazz.getFields());
        for (Field field : fields) {
            String fieldName = field.getName();
            String serverFieldName = null;

            if (!fieldMapping.containsKey(clazz.getCanonicalName())) {
                synchronized (fieldMapping) {
                    fieldMapping.put(clazz.getCanonicalName(), new HashMap<String, String>());
                }
            }

            Map<String, String> classFieldMap = fieldMapping.get(clazz.getCanonicalName());

            if (classFieldMap.containsKey(fieldName)) {
                serverFieldName = classFieldMap.get(fieldName);
            } else {
                PropMap annotation = field.getAnnotation(PropMap.class);
                if (annotation != null) {
                    serverFieldName = annotation.serverFieldName();
                    classFieldMap.put(field.getName(), serverFieldName);
                    synchronized (fieldMapping) {
                        fieldMapping.put(clazz.getCanonicalName(), classFieldMap);
                    }
                }
            }

            if (serverFieldName != null && jsonObject.has(serverFieldName) && !jsonObject.isNull(serverFieldName)) {
                Object fieldValue = jsonObject.get(serverFieldName);
                field.setAccessible(true);
                if (field.getType() == String.class) {
                    field.set(object, String.valueOf(fieldValue));
                } else if (field.getType().isAssignableFrom(Boolean.TYPE)) {
                    field.set(object, Boolean.valueOf(fieldValue.toString()));
                } else if (field.getType().isAssignableFrom(Double.TYPE)) {
                    field.set(object, Double.valueOf(fieldValue.toString()));
                } else if (field.getType().isAssignableFrom(Integer.TYPE)) {
                    field.set(object, Integer.valueOf(fieldValue.toString()));
                } else if (field.getType().isAssignableFrom(Long.TYPE)) {
                    field.set(object, Long.valueOf(fieldValue.toString()));
                } else if (field.getType().isAssignableFrom(Float.TYPE)) {
                    field.set(object, Float.valueOf(fieldValue.toString()));
                }
            }
        }
    }

}
