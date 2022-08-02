#include <stdio.h>

typedef struct
{
    char *name;
    int age;
} Student;

void print_student_ptr(Student *student)
{
    printf("%s is %d years old.\n", student->name, student->age);
}

void print_student(Student student)
{
    printf("%s is %d years old.\n", student.name, student.age);
}

int main(int argc, char const *argv[])
{
    Student s1 = {
        "John",
        20};
    print_student_ptr(&s1);
    print_student(s1);
    return 0;
}
