CREATE TABLE IF NOT EXISTS todos (
   id           Long PRIMARY KEY auto_increment,
   name         VARCHAR,
   edited       Boolean,
   completed    Boolean
);