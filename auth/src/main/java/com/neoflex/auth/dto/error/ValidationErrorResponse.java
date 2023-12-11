package com.neoflex.auth.dto.error;

import java.util.List;

public record ValidationErrorResponse(List<Violation> violations) {
}
