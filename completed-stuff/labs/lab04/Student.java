import java.util.Optional;

class Student extends KeyableMap<Course> {
    Student(String stName) {
        super(stName);
    }

    Student(String stName, ImmutableMap<String, Course> courseMap) {
        super(stName, courseMap);
    }

    Student(KeyableMap<Course> courseMap) {
        super(courseMap);
    }

    @Override
    public Student put(Course cse) {
        return new Student(super.put(cse));
    }

    @Override
    public Optional<Course> get(String courseName) {
        return super.get(courseName);
    }
}
