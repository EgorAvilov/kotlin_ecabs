import com.example.consumer.data.Booking
import com.example.consumer.specification.BookingSpecification
import com.example.consumer.utils.SearchCriteria
import com.example.consumer.utils.SearchOperation
import org.springframework.data.jpa.domain.Specification

class BookingSpecificationBuilder {

    private val params: MutableList<SearchCriteria> = mutableListOf()

    fun with(key: String, operation: String, value: Any): BookingSpecificationBuilder {
        params.add(SearchCriteria(key, operation, value))
        return this
    }

    fun with(searchCriteria: SearchCriteria): BookingSpecificationBuilder {
        params.add(searchCriteria)
        return this
    }

    fun build(): Specification<Booking> {
        var result: Specification<Booking> = BookingSpecification(params[0])

        for (idx in 1 until params.size) {
            val criteria = params[idx]
            result = if (SearchOperation.getDataOption(criteria.dataOption) == SearchOperation.ALL) {
                result.and(BookingSpecification(criteria))
            } else {
                result.or(BookingSpecification(criteria))
            }
        }
        return result
    }

}
