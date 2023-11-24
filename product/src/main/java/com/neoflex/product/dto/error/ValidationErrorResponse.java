package com.neoflex.product.dto.error;

import java.util.List;

public record ValidationErrorResponse(List<Violation> violations) {
}
