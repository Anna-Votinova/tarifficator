package com.neoflex.tariffs.dto.error;

import java.util.List;

public record ValidationErrorResponse(List<Violation> violations) {
}
