package com.teamtreehouse.techdegrees.model;


public class Todo {
    private Long id;
    private String name;
    private boolean edited;
    private boolean completed;

    public Todo(String name, boolean edited, boolean completed) {
        this.name = name;
        this.edited  = edited;
        this.completed = completed;
    }

    public Todo(Long id, String name, boolean edited, boolean completed) {
        this.id = id;
        this.name = name;
        this.edited  = edited;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Todo todo = (Todo) o;

        if (edited != todo.edited) return false;
        if (completed != todo.completed) return false;
        if (!id.equals(todo.id)) return false;
        return name.equals(todo.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (edited ? 1 : 0);
        result = 31 * result + (completed ? 1 : 0);
        return result;
    }
}
