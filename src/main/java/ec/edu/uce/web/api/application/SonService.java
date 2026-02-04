package ec.edu.uce.web.api.application;

import java.util.List;

import ec.edu.uce.web.api.application.representation.SonRepresentation;
import ec.edu.uce.web.api.domain.Son;
import ec.edu.uce.web.api.infraestructure.SonRepository;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SonService {
    @Inject
    SonRepository sonRepository;

    @WithTransaction
    public Uni<List<SonRepresentation>> findByStudentId(Long studentId) {
        return sonRepository.findByStudentId(studentId)
                .map(sons -> sons.stream()
                        .map(this::mapperToSonRepresentation)
                        .toList());
    }

    private SonRepresentation mapperToSonRepresentation(Son son) {
        SonRepresentation sonRepresentation = new SonRepresentation();
        sonRepresentation.id = son.id;
        sonRepresentation.name = son.name;
        sonRepresentation.lastName = son.lastName;
        return sonRepresentation;
    }

}
