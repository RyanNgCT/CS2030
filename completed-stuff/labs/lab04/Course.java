import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

class Course extends KeyableMap<Assessment> {
    Course(String cName) {
        super(cName);
    }

    Course(String cName, ImmutableMap<String, Assessment> imMap) {
        super(cName, imMap);
    }

    Course(KeyableMap<Assessment> imMap) {
        super(imMap);
    }

    @Override
    public Course put(Assessment asm) {
        return new Course(super.put(asm));
    }

    @Override
    public Optional<Assessment> get(String assessName) {
        return super.get(assessName);
    }
}
