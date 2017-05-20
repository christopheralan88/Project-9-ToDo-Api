package com.teamtreehouse.techdegrees;


import com.google.gson.Gson;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.teamtreehouse.techdegrees.dao.Sql2oTodoDao;
import com.teamtreehouse.techdegrees.dao.TodoDao;
import com.teamtreehouse.techdegrees.model.Todo;
import org.h2.store.LimitInputStream;
import org.sql2o.Sql2o;

import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/todos.db;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        //Sql2o sql2o = new Sql2o("jdbc:h2:mem:sample;INIT=RUNSCRIPT from 'classpath:db/init.sql'" );
        TodoDao todoDao = new Sql2oTodoDao(sql2o);
        Gson gson = new Gson();


        get("/api/v1/todos", "application/json",
                (request, response) -> todoDao.findAll(), gson::toJson);

        post("/api/v1/todos", "application/json", ((request, response) -> {
            Todo todo = gson.fromJson(request.body(), Todo.class);
            todoDao.add(todo);
            response.status(201);
            return todoDao.findAll();
        }), gson::toJson);

        delete("/api/v1/todos/:id", "application/json", ((request, response) -> {
            Todo todo = todoDao.findById(Long.parseLong(request.params("id")));
            todoDao.delete(todo);
            response.status(200);
            return todoDao.findAll();
        }), gson::toJson);

        put("/api/v1/todos/:id", "application/json", ((request, response) -> {
            Long id = Long.parseLong(request.params("id"));
            String name = gson.fromJson(request.body(), Todo.class).getName();
            boolean edited = gson.fromJson(request.body(), Todo.class).isEdited();
            boolean completed = gson.fromJson(request.body(), Todo.class).isCompleted();
            Todo todo = new Todo(id, name, edited, completed);
            todoDao.update(todo);
            response.status(200);
            return todoDao.findAll();
        }), gson::toJson);

        after(((request, response) -> {
            response.type("application/json");
        }));
    }

}
