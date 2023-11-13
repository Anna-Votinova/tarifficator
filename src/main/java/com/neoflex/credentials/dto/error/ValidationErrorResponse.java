package com.neoflex.credentials.dto.error;

import java.util.List;

public record ValidationErrorResponse(List<Violation> violations) {
}
