**Setup the database**

```
CREATE DATABASE stojkovski;
CREATE USER 'stojkovski'@'localhost' IDENTIFIED BY '123465';
GRANT ALL PRIVILEGES ON stojkovski.* TO 'stojkovski'@'localhost';
```