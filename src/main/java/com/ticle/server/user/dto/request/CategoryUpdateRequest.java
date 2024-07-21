package com.ticle.server.user.dto.request;

import com.ticle.server.user.domain.type.Category;

import java.util.List;

public record CategoryUpdateRequest(List<Category> category) {
}
