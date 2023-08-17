# Stadium Manager
###### Version:
- Spring Boot: 2.7.14
- Java 11
- jQuery 3.6.0
- Boostrap 5
- Maven: latest version

#### Installation Instructions
After clone project from [Github](https://github.com/minhnhatit21/StadiumManager) 
Use [Intellij Idea](https://www.jetbrains.com/idea/download/?section=windows) IDE to open project

`application.properties`: 
```
spring.datasource.url=jdbc:mysql://localhost:3306/stadium_manager?useSSL=false  
spring.datasource.username= [username]  
spring.datasource.password= [password]  
  
spring.jpa.hibernate.ddl-auto=update  
  
spring.mvc.throw-exception-if-no-handler-found=true  
spring.resources.add-mappings=false  
  
# Enable SQL logging for Hibernate  
spring.jpa.show-sql=true  
spring.jpa.properties.hibernate.format_sql=true  
spring.jpa.properties.hibernate.use_sql_comments=true
```

> Note: Create Schenma before run project, 
> Example: `CREATE SCHEMA stadium_manager

Run project by comand: `mvn spring-boot:run`
Run SQL Script to Import: [Link](https://drive.google.com/file/d/1GzAvBAhIeYDFhRTwCtY25ZwPFqF_WSPI/view?usp=sharing)
###### Account:
- User:
	username: user01, password: 123456
	username: user02, password: 123456
- Admin: 
	username: admin, password: 123456
