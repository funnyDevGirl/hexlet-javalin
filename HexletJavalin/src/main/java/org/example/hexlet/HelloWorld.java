package org.example.hexlet;

import io.javalin.Javalin;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.model.Course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HelloWorld {
    //private static final List<Map<String, String>> COURSES = Data.getCourses();
    private static final List<Course> COURSES = new ArrayList<>();



    public static void main(String[] args) {
        // Создаем приложение
        var app = Javalin.create(config -> {
            config.plugins.enableDevLogging();
        });
        // Описываем, что загрузится по адресу /:
        app.get("/", ctx -> ctx.result("Hello World"));
        //app.get("/", ctx -> ctx.render("index.jte"));

        app.get("/users", ctx -> ctx.result("GET /users"));
        app.post("/users", ctx -> ctx.result("POST /users"));

        app.get("/hello", ctx -> {
            //var name = ctx.queryParam("John");
            var name = ctx.queryParamAsClass("John", String.class).getOrDefault("World");
            ctx.result("Hello, " + name + "!");
        });

        app.get("courses/{courseId}/lessons/{id}", ctx -> {
            ctx.result("Course ID: " + ctx.pathParam("courseId"));
            ctx.result("Lesson ID: " + ctx.pathParam("id"));
        });


        app.get("users/{id}/post/{postId}", ctx -> {
            ctx.result("User ID: " + ctx.pathParam("id"));
            ctx.result("Post ID: " + ctx.pathParam("postId"));
        });


        //************************************************************************************************

        Course course1 = new Course("web", "This is a course with theory about the web");
        course1.setId(1L);
        Course course2 = new Course("sql", "This is a course with theory about the sql");
        course2.setId(2L);
        Course course3 = new Course("collections", "This is a course with theory about the collections");
        course3.setId(3L);
        COURSES.add(course1);
        COURSES.add(course2);
        COURSES.add(course3);


        app.get("/courses/{id}", ctx -> {
            /* Курс извлекается из базы данных.*/
            var id = ctx.pathParam("id");
            var course = courseFind(id);

            if (course != null) {
                var page = new CoursePage(course);
                ctx.render("courses/show.jte", Collections.singletonMap("page", page));
            } else {
                ctx.status(404).result("Course not found");
            }
        });

        app.get("/courses", ctx -> {
            /* Список курсов извлекается из базы данных */
            //var courses = COURSES;
            var header = "Курсы по программированию";
            var page = new CoursesPage(COURSES, header);
            //объект передается в шаблон в виде Map, созданного через Collection.singletonMap():
            ctx.render("index.jte", Collections.singletonMap("page", page));
        });


        app.start(7070); // Стартуем веб-сервер
    }

    public static Course courseFind(String id) {
        for (var course : COURSES) {
            if (String.valueOf(course.getId()).equals(id)) {
                return course;
            }
        }
        return null;
    }
}
