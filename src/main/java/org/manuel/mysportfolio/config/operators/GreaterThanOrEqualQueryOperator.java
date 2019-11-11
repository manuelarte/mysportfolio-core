package org.manuel.mysportfolio.config.operators;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@lombok.EqualsAndHashCode
public class GreaterThanOrEqualQueryOperator implements QueryOperator {

    @Override
    public String getOperator() {
        return ">=:";
    }

    @Override
    public Function<Criteria, Criteria> addOperation(final Object value) {
        return c -> c.gte(value);
    }

    @Override
    public Object getValue(Object value) {
        return value;
    }

}
