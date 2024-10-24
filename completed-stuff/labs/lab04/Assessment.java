class Assessment implements Keyable {
    private final String title;
    private final String grade;

    Assessment(String title, String grade) {
        this.title = title;
        this.grade = grade;
    }

    // should this be private instead??? -> no as it is doing something like Pair
    public String getGrade() {
        return this.grade;
    }

    public String getKey() {
        return this.title;
    }

    @Override
    public String toString() {
        return "{" + this.title + ": " + this.grade + "}";
    }
}
