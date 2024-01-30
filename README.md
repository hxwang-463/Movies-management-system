# Movies, Series, and Models management

A Spring Boot Application

## Database ER Diagram
![er](https://github.com/hxwang-463/Movies-management-system/assets/34204376/bbd2ddbe-d5c0-4e9c-a5fe-0861ea134edc)
Movies.series is FK of Series.id, which is a Many-To-One and One-To-Many relationship;  
Models and Movies is a Many-to-Many relationship, which will lead to a intermediate table Movie_model.

## Spring Boot

