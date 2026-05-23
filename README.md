# Student Result Management System

A Jakarta Servlet + JSP web application for managing students, subjects, marks, and results with role-based admin/student access.

## Current Project Contents

### Root Files
- `.gitignore` — ignores `target/`, IDE files, `.vscode/`, and `src/main/resources/db.properties`
- `pom.xml` — Maven build configuration
- `GenerateHash.java` — utility to generate BCrypt password hashes
- `student_db.sql` — database schema and seed data for MySQL
- `README.md` — project documentation

### Config Files
- `src/main/resources/db.properties.example` — database connection example
- `src/main/resources/db.properties` — actual database connection file (should be created locally and kept out of source control)

### Java Source
- `src/main/java/com/srms/dao/`
  - `DBConnection.java`
  - `UserDAO.java`
  - `StudentDAO.java`
  - `SubjectDAO.java`
  - `MarksDAO.java`
- `src/main/java/com/srms/servlet/`
  - `LoginServlet.java`
  - `LogoutServlet.java`
  - `ChangePasswordServlet.java`
  - `DashboardServlet.java`
  - `StudentDashboardServlet.java`
  - `ResultServlet.java`
  - `StudentResultServlet.java`
  - `StudentServlet.java`
  - `SubjectServlet.java`
  - `MarksServlet.java`
  - `SearchServlet.java`
  - `ExportServlet.java`
- `src/main/java/com/srms/filter/AuthFilter.java`
- `src/main/java/com/srms/listener/AppStartupListener.java`
- `src/main/java/com/srms/util/`
  - `GradeCalculator.java`
  - `PasswordUtil.java`
  - `ExportUtil.java`
- `src/main/java/com/srms/model/`
  - `Admin.java`
  - `Student.java`
  - `Subject.java`
  - `Mark.java`
  - `Result.java`
  - `User.java`
  - `Person.java`
  - `Reportable.java`

### Web Files
- `src/main/webapp/WEB-INF/web.xml`
- JSP views in `src/main/webapp/WEB-INF/views/`
  - `login.jsp`
  - `error.jsp`
  - `admin/` pages
  - `student/` pages
- Static resources in `src/main/webapp/resources/`
  - `css/style.css`
  - `js/validation.js`

## Key Application Features

- Role-based login for admin and student users
- Servlet-based routing and protected pages using `AuthFilter`
- Admin CRUD for students, subjects, and marks
- Student result dashboard and view
- Password change support for both admin and students
- BCrypt password hashing with `jBCrypt`
- Database-backed login and authorization
- JSP UI with form validation and sidebar navigation
- Export support via `ExportServlet`

## Setup Instructions

1. Copy the example DB file:

```bash
cp src/main/resources/db.properties.example src/main/resources/db.properties
```

2. Edit `src/main/resources/db.properties` with your MySQL settings:

```properties
db.url=jdbc:mysql://localhost:3306/student_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
db.user=root
db.password=YOUR_PASSWORD_HERE
db.driver=com.mysql.cj.jdbc.Driver
```

3. Create the database from `student_db.sql`:

```bash
mysql -u root -p < student_db.sql
```

4. Build the application:

```bash
mvn clean package
```

5. Deploy the generated WAR from `target/` to Tomcat 10.x or later.

## Notes

- `src/main/resources/db.properties` is intentionally excluded by `.gitignore`.
- `db.properties.example` is safe to keep in source control.
- Use `GenerateHash.java` when you need to create new BCrypt password hashes.

## Recommended URLs

- Login: `http://localhost:8080/student-result-management/login`
- Admin Dashboard: `http://localhost:8080/student-result-management/admin/dashboard`
- Student Dashboard: `http://localhost:8080/student-result-management/student/dashboard`

## Troubleshooting

- If the app cannot connect to the database, verify `db.properties` values and MySQL availability.
- If login fails, ensure user records exist in `student_db.sql` seed data.
- If the build fails, check your Java and Maven installations:
  - `java -version`
  - `mvn -version`


