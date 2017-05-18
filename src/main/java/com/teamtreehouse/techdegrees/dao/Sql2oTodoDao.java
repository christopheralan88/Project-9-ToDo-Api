package com.teamtreehouse.techdegrees.dao;


import com.teamtreehouse.techdegrees.exceptions.DaoException;
import com.teamtreehouse.techdegrees.model.Todo;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oTodoDao implements TodoDao {
    private final Sql2o sql2o;


    public Sql2oTodoDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Todo todo) throws DaoException {
        String sql = "INSERT INTO todos (id, name, edited, completed) " +
                "VALUES (:id, :name, :edited, :completed)";
        try (Connection con = sql2o.open()) {
            Long id = (Long) con.createQuery(sql)
                    .bind(todo)
                    .executeUpdate()
                    .getKey();
            todo.setId(id); //TODO: CJ why are we doing this since the todo is not being returned???
        } catch (Sql2oException ex) {
            throw new DaoException(ex, "There was a problem adding the Todo");
        }
    }

    @Override
    public void delete(Todo todo) throws DaoException {

    }

    @Override
    public void update(Todo todo) throws DaoException {

    }

    @Override
    public List<Todo> findAll() throws DaoException {
        String sql = "SELECT * FROM todos";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(Todo.class);
        } catch (Sql2oException ex) {
            throw new DaoException(ex, "There was a problem adding the Todo");
        }
    }

    @Override
    public Todo findById(Long id) throws DaoException {
        return null;
    }
}
