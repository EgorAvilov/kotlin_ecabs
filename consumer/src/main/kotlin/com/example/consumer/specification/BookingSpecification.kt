package com.example.consumer.specification

import com.example.consumer.data.Booking
import com.example.consumer.utils.SearchCriteria
import com.example.consumer.utils.SearchOperation
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification

class BookingSpecification(private val searchCriteria: SearchCriteria) : Specification<Booking> {

    override fun toPredicate(root: Root<Booking>, query: CriteriaQuery<*>, cb: CriteriaBuilder): Predicate? {
        val strToSearch = searchCriteria.value.toString().lowercase()
        return when (SearchOperation.getSimpleOperation(searchCriteria.operation)) {
            SearchOperation.CONTAINS -> cb.like(cb.lower(root.get(searchCriteria.filterKey)), "%$strToSearch%")
            SearchOperation.DOES_NOT_CONTAIN -> cb.notLike(cb.lower(root.get(searchCriteria.filterKey)), "%$strToSearch%")
            SearchOperation.BEGINS_WITH -> cb.like(cb.lower(root.get(searchCriteria.filterKey)), "$strToSearch%")
            SearchOperation.DOES_NOT_BEGIN_WITH -> cb.notLike(cb.lower(root.get(searchCriteria.filterKey)), "$strToSearch%")
            SearchOperation.ENDS_WITH -> cb.like(cb.lower(root.get(searchCriteria.filterKey)), "%$strToSearch")
            SearchOperation.DOES_NOT_END_WITH -> cb.notLike(cb.lower(root.get(searchCriteria.filterKey)), "%$strToSearch")
            SearchOperation.EQUAL -> cb.equal(root.get<Any>(searchCriteria.filterKey), searchCriteria.value)
            SearchOperation.NOT_EQUAL -> cb.notEqual(root.get<Any>(searchCriteria.filterKey), searchCriteria.value)
            SearchOperation.NULL -> cb.isNull(root.get<Any>(searchCriteria.filterKey))
            SearchOperation.NOT_NULL -> cb.isNotNull(root.get<Any>(searchCriteria.filterKey))
            SearchOperation.GREATER_THAN -> cb.greaterThan(root.get(searchCriteria.filterKey), searchCriteria.value.toString())
            SearchOperation.GREATER_THAN_EQUAL -> cb.greaterThanOrEqualTo(root.get(searchCriteria.filterKey), searchCriteria.value.toString())
            SearchOperation.LESS_THAN -> cb.lessThan(root.get(searchCriteria.filterKey), searchCriteria.value.toString())
            SearchOperation.LESS_THAN_EQUAL -> cb.lessThanOrEqualTo(root.get(searchCriteria.filterKey), searchCriteria.value.toString())
            else -> null
        }
    }
}
