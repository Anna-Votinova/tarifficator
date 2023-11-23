package com.neoflex.credentials.repository;

import com.neoflex.credentials.entity.Client;
import com.neoflex.credentials.exeption.QueryOperatorNotSupportedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomClientRepository {

    private final ClientRepository clientRepository;

    public List<Client> getClientsByParameters(List<Criteria> criteria) {
        List<Client> clients = clientRepository.findAll(getCriteriaSpecification(criteria));
        log.info("Received clients = {}", clients);
        return clients;
    }

    private Specification<Client> getCriteriaSpecification(List<Criteria> criteria) {
        log.info("Try to get specifications for criteria list with size {}", criteria.size());

        Specification<Client> specification = where(createSpecification(criteria.remove(0)));
        for (Criteria input : criteria) {
            log.info("Common specifications equals {}", specification);
            specification = specification.and(createSpecification(input));
        }
        log.info("Common specifications equals {}", specification);
        return specification;
    }

    private Specification<Client> createSpecification(Criteria criteria) {
        log.info("Get specifications for criteria {}", criteria);

        if (QueryOperator.EQUALS.equals(criteria.operator())) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.<String> get(criteria.field()), criteria.value().toString());
        } else {
            throw new QueryOperatorNotSupportedException("Оператор пока не поддерживается");
        }
    }
}
