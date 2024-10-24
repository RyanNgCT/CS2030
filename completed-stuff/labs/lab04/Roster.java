import java.util.Optional;

class Roster extends KeyableMap<Student> {
    Roster(String rName) {
        super(rName);
    }

    Roster(String rName, ImmutableMap<String, Student> stuMap) {
        super(rName, stuMap);
    }

    Roster(KeyableMap<Student> stuMap) {
        super(stuMap);
    }

    @Override
    public Roster put(Student stu) {
        return new Roster(super.put(stu));
    }

    @Override
    public Optional<Student> get(String stuName) {
        return super.get(stuName);
    }

    public String getGrade(String stuName, String cseName, String astName) {
        // return new Student(stuName).put(new Course(courseName).put(new Assessment(assessName)));

        return this.get(stuName)
            .flatMap(cse -> cse.get(cseName))
            .flatMap(ast -> ast.get(astName))
            .map(x -> x.getGrade())
            .orElse("No such record: " + stuName + " " + cseName + " " +
                    astName);
    }

    public Roster add(String stuName, String cseCode, String astName,
                      String grade) {
        //return this.put(new Student(stuName).put(
        //    new Course(cseCode).put(new Assessment(astName, grade))));

        Student stu = this.get(stuName).orElse(new Student(stuName));
        Course cse = stu.get(cseCode).orElse(new Course(cseCode));
        cse = cse.put(new Assessment(astName, grade));
        stu = stu.put(cse);

        return this.put(stu);
    }
}
