package io.ylab.exception;

import java.util.List;

public record ValidationErrorResponse(List<Violation> violations) {
}
