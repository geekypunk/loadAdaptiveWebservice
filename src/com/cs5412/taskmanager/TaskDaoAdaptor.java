package com.cs5412.taskmanager;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * <p><b>Serialization class for Task object, to be used by Gson<b></p>
 * @author kt466
 *
 */
public class TaskDaoAdaptor implements JsonSerializer<TaskDao> {

 @Override
 public JsonElement serialize(TaskDao src, Type typeOfSrc,
            JsonSerializationContext context)
    {
	 	ArrayList<String> myList = src.getParentTaskId();
	 	if(myList == null) myList = new ArrayList<String>();
	 	final JsonArray obj1 = new JsonArray();
	 	for(final String in : myList){
	 		final JsonPrimitive pId = new JsonPrimitive(in);
	 		obj1.add(pId);
	 	}
	 
        JsonObject obj = new JsonObject();
        obj.addProperty("userId", src.getUserId());
        obj.addProperty("taskName",src.getTaskName());
        obj.addProperty("taskDescription",src.getTaskDescription());
        obj.addProperty("taskType",src.getTaskType());
        obj.addProperty("reportUrl",src.getReportUrl());
        obj.addProperty("status",src.getStatus().name());
        obj.addProperty("isSeen",src.isSeen());
        obj.addProperty("taskId",src.getTaskId());
        obj.addProperty("isSeen",src.isSeen());
        obj.addProperty("isParent",src.isParent());
        obj.addProperty("hostAddress",src.getHostAddress());
        obj.addProperty("hostVersion",src.getHostVersion());
        obj.addProperty("wsURL",src.getWsURL());
        final JsonArray subTasks = new JsonArray();
        for (final Integer task : src.getAllSubTasks()) {
            final JsonPrimitive taskID = new JsonPrimitive(task);
            subTasks.add(taskID);
          }
        obj.add("subTasks", subTasks);
        obj.add("parentTaskIds",obj1);

        return obj;
    }


}