package com.example.blogplatform.repository.specification;

import com.example.blogplatform.domain.PostStatus;
import com.example.blogplatform.domain.entity.Post;
import com.example.blogplatform.domain.entity.Tag;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class PostSpecification {
    public static Specification<Post> hasStatus(PostStatus status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    public static Specification<Post> hasCategory(UUID categoryId) {
        return (root, query, cb) -> categoryId == null ? null : cb.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Post> hasAuthor(UUID authorId) {
        return (root, query, cb) -> authorId == null ? null : cb.equal(root.get("author").get("id"), authorId);
    }

    public static Specification<Post> hasTag(UUID tagId) {
        return (root, query, cb) -> {
            if (tagId == null) {
                return null;
            }

            Join<Post, Tag> tags = root.join("tags", JoinType.INNER);
            return cb.equal(tags.get("id"), tagId);
        };
    }

    public static Specification<Post> titleContains(String q) {
        return (root, query, cb) -> q == null ? null : cb.like(cb.lower(root.get("title")), "%" + q.toLowerCase() + "%");
    }
}
