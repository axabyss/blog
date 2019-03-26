package org.wordsmith.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.wordsmith.blog.domain.Comment;

/**
 * Comment资源库
 * @author Wordsmith
 *
 */
public interface CommentRepository extends JpaRepository<Comment, Long>{
	
}
