package ec.edu.uce.web.api.application;

import java.util.List;

import ec.edu.uce.web.api.application.representation.StudentRepresentation;
import ec.edu.uce.web.api.domain.Student;
import ec.edu.uce.web.api.infraestructure.StudentRepository;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class StudentService {
    @Inject
    StudentRepository studentRepository;

    @WithTransaction
    public Uni<List<StudentRepresentation>> findAll() {
        return studentRepository.findAll().list()
                .map(students -> students.stream()
                        .map(this::mapperToStudent)
                        .toList());
    }

    @WithTransaction
    public Uni<StudentRepresentation> findById(Long id) {
        return studentRepository.findById(id)
                .map(this::mapperToStudent);
    }

    @WithTransaction
    public Uni<StudentRepresentation> save(StudentRepresentation studentRep) {
        Student student = mapperToStudentRepresentation(studentRep);
        return studentRepository.persist(student)
                .map(this::mapperToStudent);
    }

    @WithTransaction
    public Uni<StudentRepresentation> update(Long id, StudentRepresentation studentRep) {
        return studentRepository.findById(id)
                .flatMap(existing -> {
                    if (existing == null) {
                        return Uni.createFrom().failure(
                            new jakarta.ws.rs.NotFoundException("Student not found")
                        );
                    }
                    
                    Student studentData = mapperToStudentRepresentation(studentRep);
                    existing.name = studentData.name;
                    existing.lastName = studentData.lastName;
                    existing.email = studentData.email;
                    existing.birthDay = studentData.birthDay;
                    existing.province = studentData.province;
                    existing.gender = studentData.gender;
                    
                    return studentRepository.persistAndFlush(existing)
                            .map(this::mapperToStudent);
                });
    }

    @WithTransaction
    public Uni<Void> partialUpdate(Long id, StudentRepresentation studentRep) {
        return studentRepository.findById(id)
                .flatMap(existing -> {
                    if (existing == null) {
                        return Uni.createFrom().failure(
                            new jakarta.ws.rs.NotFoundException("Student not found")
                        );
                    }
                    
                    if (studentRep.name != null) {
                        existing.name = studentRep.name;
                    }
                    if (studentRep.lastName != null) {
                        existing.lastName = studentRep.lastName;
                    }
                    if (studentRep.email != null) {
                        existing.email = studentRep.email;
                    }
                    if (studentRep.birthDay != null) {
                        existing.birthDay = studentRep.birthDay;
                    }
                    if (studentRep.province != null) {
                        existing.province = studentRep.province;
                    }
                    if (studentRep.gender != null) {
                        existing.gender = studentRep.gender;
                    }
                    
                    return studentRepository.persistAndFlush(existing)
                            .replaceWithVoid();
                });
    }

    @WithTransaction
    public Uni<Boolean> delete(Long id) {
        return studentRepository.deleteById(id);
    }

    @WithTransaction
    public Uni<List<StudentRepresentation>> findByProvince(String province) {
        return studentRepository.find("province", province).list()
                .map(students -> students.stream()
                        .map(this::mapperToStudent)
                        .toList());
    }

    @WithTransaction
    public Uni<List<StudentRepresentation>> findByProvinceAndGender(String province, String gender) {
        return studentRepository.find("province = ?1 and gender = ?2", province, gender).list()
                .map(students -> students.stream()
                        .map(this::mapperToStudent)
                        .toList());
    }

    private StudentRepresentation mapperToStudent(Student student) {
        if (student == null) return null;
        
        StudentRepresentation studentRep = new StudentRepresentation();
        studentRep.id = student.id;
        studentRep.name = student.name;
        studentRep.lastName = student.lastName;
        studentRep.email = student.email;
        studentRep.birthDay = student.birthDay;
        studentRep.province = student.province;
        studentRep.gender = student.gender;
        return studentRep;
    }

    private Student mapperToStudentRepresentation(StudentRepresentation studentRep) {
        if (studentRep == null) return null;
        
        Student student = new Student();
        student.id = studentRep.id;
        student.name = studentRep.name;
        student.lastName = studentRep.lastName;
        student.email = studentRep.email;
        student.birthDay = studentRep.birthDay;
        student.province = studentRep.province;
        student.gender = studentRep.gender;
        return student;
    }
}