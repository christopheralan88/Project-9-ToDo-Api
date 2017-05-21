package com.teamtreehouse.techdegrees.dao;

import com.teamtreehouse.techdegrees.exceptions.DaoException;
import com.teamtreehouse.techdegrees.model.Todo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;
import java.util.function.ToDoubleBiFunction;

import static org.junit.Assert.*;


public class Sql2oTodoDaoTest {
    private Sql2oTodoDao dao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        dao = new   Sql2oTodoDao(sql2o);

        //Keep connection open during entire test so that it isn't wiped out.
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void adding_todo_sets_id() throws Exception {
        //arrange
        Todo todo = new Todo("todo1", false, false);

        //act
        dao.add(todo);

        //assert
        Assert.assertTrue(todo.getId() == 1);
    }

    @Test
    public void added_todos_are_returned_from_findAll() throws Exception {
        Todo todo = new Todo("todo2", false, false);
        dao.add(todo);

        List<Todo> todos = dao.findAll();

        assertTrue(todos.size() != 0);
    }

    @Test
    public void no_todos_returns_an_empty_list() throws  Exception {
        assertEquals(0, dao.findAll().size());
    }

    @Test
    public void deleting_todos_works() throws Exception {
        Todo todo = new Todo(3L,"todo1", false, false);
        dao.add(todo);

        dao.delete(todo);

        assertTrue(dao.findById(3L) == null);
    }

    @Test
    public void updating_todos_works() throws Exception {
        Todo originalTodo = new Todo(4L, "todo4", false, false);
        Todo newTodo = new Todo(4L, "todo4Updated", true, true);

        dao.add(originalTodo);
        dao.update(newTodo);

        assertEquals(dao.findById(4L), newTodo);
    }

    @Test
    public void finding_todos_by_id_works_correctly() throws Exception {
        Todo todo = new Todo(1L,"todo", false, false);

        dao.add(todo);
        Todo retrievedTodo = dao.findById(1L);

        assertEquals(todo, retrievedTodo);
    }

    @Test
    public void findById_returns_null_if_cant_find_todo() throws Exception {
        assertTrue(dao.findById(1L) == null);
    }

}