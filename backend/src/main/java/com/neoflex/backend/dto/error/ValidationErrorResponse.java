package com.neoflex.backend.dto.error;

import java.util.List;

public record ValidationErrorResponse(List<Violation> violations) {
}
