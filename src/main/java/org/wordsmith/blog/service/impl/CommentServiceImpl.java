package org.wordsmith.blog.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wordsmith.blog.domain.Comment;
import org.wordsmith.blog.repository.CommentRepository;
import org.wordsmith.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
    private CommentRepository commentRepository;
    
	@Override
	public Optional<Comment> getCommentById(Long id) {
		return commentRepository.findById(id);
	}

	@Override
	public void removeComment(Long id) {
		commentRepository.deleteById(id);
	}

}
