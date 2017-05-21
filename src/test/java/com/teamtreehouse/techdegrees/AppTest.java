package com.teamtreehouse.techdegrees;

import com.google.gson.Gson;
import com.teamtreehouse.techdegrees.dao.Sql2oTodoDao;
import com.teamtreehouse.techdegrees.model.Todo;
import com.teamtreehouse.techdegrees.testing.ApiClient;
import com.teamtreehouse.techdegrees.testing.ApiResponse;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


public class AppTest {

    public static final String PORT = "4568";
    public static final String TEST_DATA_SOURCE = "jdbc:h2:mem:testing";
    //private Sql2oTodoDao dao;
    private Connection conn;
    private ApiClient client;
    private Gson gson;


    @BeforeClass
    public static void startServer() {
        String[] args = {PORT, TEST_DATA_SOURCE};
        App.main(args);
    }

    @AfterClass
    public static void stopServer() {
        Spark.stop();
    }

    @Before
    public void setUp() throws Exception {
        String dataSource = String.format("%s;INIT=RUNSCRIPT from 'classpath:db/init.sql'", TEST_DATA_SOURCE);
        Sql2o sql2o = new Sql2o(dataSource, "", "");
        client = new ApiClient("http://localhost:" + PORT);
        gson = new Gson();
        //dao = new Sql2oTodoDao(sql2o);

        //Keep connection open during entire test so that it isn't wiped out.
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void adding_todos_returns_created_status() throws Exception {
        Map<String, String> values = new HashMap<>();
        values.put("name", "todo1");
        values.put("edited", "false");
        values.put("completed", "false");

        ApiResponse response = client.request("POST", "/api/v1/todos", gson.toJson(values));

        assertEquals(response.getStatus(), 201);
    }
}