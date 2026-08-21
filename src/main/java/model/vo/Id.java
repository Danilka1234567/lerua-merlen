package model.vo;

import java.util.ArrayList;
import java.util.List;

public class Id extends BaseVO<Long>{


    public Id(Long value) {
        super(value);
    }

    @Override
    protected List<String> getViolations(Long value) {
        List<String> violations = new ArrayList<>();
        if (value < 0)
            violations.add("value can't be negative");

        
        return violations;
    }
}
