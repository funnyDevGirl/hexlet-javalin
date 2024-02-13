@import org.example.hexlet.dto.courses.CoursesPage
@param CoursesPage page

<!doctype html>
<html lang="en">
    <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" /> -->
    <title>Хекслет</title>
    </head>
    <body>
        <h1>${page.getHeader()}</h1>
        @if(page.getCourses().isEmpty())
            <p>Пока не добавлено ни одного курса</p>
        @else
            @for(var course : page.getCourses())
                <div>
                    <h2><a href="/courses/${course.getId()}">${course.getName()}</a></h2>
                    <p>${course.getDescription()}</p>
                </div>
            @endfor
        @endif
    </body>
</html>

<!-- Логика вывода здесь такая:
     1. Если список курсов пустой, то выводится соответствующее сообщение
     2. Если курсы в списке есть, то на основе этого списка формируются HTML-блоки с информацией о курсе и ссылке на его страницу

     Этих конструкций хватит для решения большинства стандартных задач, потому что внутри них можно использовать любой Java-код.
     Единственное, что нужно не забывать — это импортировать используемые классы через директиву @import.
-->
