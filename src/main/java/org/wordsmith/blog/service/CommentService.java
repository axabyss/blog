package org.wordsmith.blog.service;

import java.util.Optional;

import org.wordsmith.blog.domain.Comment;

/**
 * Comment 服务接口.
 * @author Wordsmith
 */
public interface CommentService {
	
	/**
     * 根据id获取 Comment
     * @param id
     * @return
     */
	Optional<Comment> getCommentById(Long id);
    /**
     * 删除评论
     * @param id
     * @return
     */
    void removeComment(Long id); 
}
