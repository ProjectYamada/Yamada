# Yamada
It's not about Kayla™

### Dependencies
- A PostgreSQL database with the tables from src/main/resources/createTables.sql
- Gradle and Git (in your PATH)
- optional: [forever](https://www.npmjs.com/package/forever) for process management
- optional: GNU screen or an alternative terminal
### Building and Running
You **must** have git and gradle in your PATH when running.
Yamada uses relative paths to complete some reflection
(see module.SanaeClassLoader) so when running your own instance, run it
in the top path of the folder.
```
# running:
gradle run
```
